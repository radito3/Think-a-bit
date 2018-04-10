package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Game functionality implementation class.
 *
 * @author Rangel Ivanov
 */
public class GameHub implements Game {

    private static GameHub instance;

    private final DatabaseUtil db;

    private Stages currentStage;

    private static final int FIRST_STAGE_ATTEMPTS = 10;

    private GameHub() {
        db = DatabaseUtil.getInstance();
    }

    /**
     * Get the Singleton instance.
     *
     * @return The instance
     */
    public static GameHub getInstance() {
        if (instance == null) {
            instance = new GameHub();
        }
        return instance;
    }

    @Override
    public List<Categories> getCategories() {
        return db.getObject(s -> s.createQuery("FROM Categories").list());
    }

    @Override
    public String playCategory(int categoryId, int userId) {
        Categories category = db.getObject(s -> s.get(Categories.class, categoryId));

        db.processObject(s -> s.saveOrUpdate(new UserProgress(userId, category.getId())));

        List<Stages> stages = db.getObject(s -> s.createQuery("FROM Stages WHERE CategoryId = " +
                category.getId()).list());
        stages.forEach(s -> db.processObject(i -> i.saveOrUpdate(new StageAttempts(s.getId(), userId, categoryId))));

        return buildCategoryJson(categoryId, userId, category, stages).toString();
    }

    private StringBuilder buildCategoryJson(int categoryId, int userId, Categories category, List<Stages> stages) {
        StringBuilder json = new StringBuilder("{\"name\":\"" + category.getName() + "\",\"stages\":[");

        stages.forEach(stage -> {
            UserProgress up = db.getObject(s -> s.get(UserProgress.class,
                    new UserProgress(userId, categoryId)));
            boolean isReached = up.getReachedStage() == stage.getNumber();
            StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                    new StageAttempts(stage.getId(), userId, categoryId)));
            long seconds = stageAvailability(sa);

            json.append("{\"id\":").append(stage.getId())
                    .append(",\"isReached\":").append(isReached)
                    .append(",\"attempts\":").append(sa.getAttempts())
                    .append(",\"availableAfter\":").append(seconds)
                    .append("},");
        });

        json.deleteCharAt(json.lastIndexOf(","));
        json.append("]}");
        return json;
    }

    private long stageAvailability(final StageAttempts sa) {
        long seconds = 0;
        if (sa.getLastAttempt() != null) {
            seconds = (sa.getLastAttempt().getTime() + 1800L) -
                    Timestamp.from(Instant.now()).getTime();
            seconds = seconds < 0 ? 0 : seconds;
        }
        return seconds;
    }

    @Override
    public boolean checkIfStageIsAvailable(int stageId, int userId, int categoryId) {
        StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                new StageAttempts(stageId, userId, categoryId)));
        return stageAvailability(sa) == 0;
    }

    @Override
    public String playStage(int stageId, int userId, int categoryId) {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));
        StageAttempts sa;
        try {
            sa = db.getObject(s -> s.get(StageAttempts.class, new StageAttempts(stage.getId(), userId, categoryId)));
        } catch (Exception e) {
            sa = new StageAttempts(stage.getId(), userId, categoryId);
        }
        UserProgress up = db.getObject(s -> s.get(UserProgress.class, new UserProgress(userId, categoryId)));

        if (stage.getNumber() <= up.getReachedStage() && stageAvailability(sa) == 0) {
            sa.setAttempts(FIRST_STAGE_ATTEMPTS - stage.getNumber());
            final StageAttempts temp = sa;
            db.processObject(s -> s.saveOrUpdate(temp));
            setStageQuestions(stage);

            return buildStageJson(categoryId, stageId, stage).toString();
        } else {
            throw new IllegalStateException("Stage is locked");
        }
    }

    private StringBuilder buildStageJson(int categoryId, int stageId, Stages stage) {
        StringBuilder json = new StringBuilder("{\"category\":{\"id\":");
        Categories category = db.getObject(s -> s.get(Categories.class, categoryId));

        json.append(category.getId())
                .append(",\"name\":\"").append(category.getName())
                .append("\"},\"stageId\":").append(stageId)
                .append(",\"questions\":[");

        stage.getQuestions().forEach(question -> {
            json.append("{\"id\":").append(question.getId())
                .append(",\"title\":\"").append(question.getTitle())
                .append("\",\"type\":\"").append(String.valueOf(question.getType()))
                .append("\",\"answers\":[");
            List<Answers> answers = db.getObject(s ->
                    s.createQuery("FROM Answers WHERE QuestionId = " + question.getId()).list());
            answers.forEach(answer ->
                    json.append("{\"content\":\"").append(answer.getPayload())
                    .append("\",\"isCorrect\":").append(answer.getIsCorrect()).append("},"));
            json.deleteCharAt(json.lastIndexOf(","));
            json.append("]},");
        });

        json.deleteCharAt(json.lastIndexOf(","));
        json.append("]}");
        return json;
    }

    private void setStageQuestions(Stages stage) {
        stage.setQuestions(db.getObject(s ->
                s.createQuery("FROM Question RIGHT JOIN QuestionStages " +
                        "ON Question.Id = QuestionStages.QuestionId WHERE QuestionStages.StageId = " +
                        stage.getId()).list()));
    }

    @Override
    public void checkIfCurrentStageIsComplete(int userId, int categoryId, int stageId) {
        StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                new StageAttempts(stageId, userId, categoryId)));

        if (currentStage.getQuestions().stream().allMatch(Question::isSolved)) {
            UserProgress up = db.getObject(s -> s.get(UserProgress.class, new UserProgress(userId, categoryId)));
            up.setReachedStage(up.getReachedStage() + 1);
            db.processObject(s -> s.update(up));

            sa.setAttempts(FIRST_STAGE_ATTEMPTS - currentStage.getNumber());
        } else {
            sa.setAttempts(sa.getAttempts() - 1);
        }
        currentStage = null;

        sa.setLastAttempt(Timestamp.from(Instant.now()));
        db.processObject(s -> s.update(sa));
    }

    private int getQuestionIndex(Question question) {
        return currentStage.getQuestions().indexOf(question);
    }

    @Override
    public void answerQuestion(Question question, int stageId, String... answers) {
        if (currentStage == null) {
            currentStage = db.getObject(s -> s.get(Stages.class, stageId));
            setStageQuestions(currentStage);
        }

        if (question.getType() == Question.Type.CLOSED_MANY) {
            Stream<Answers> correct = db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).getResultStream());

            Stream<String> allAnswers = Stream.concat(correct.map(Answers::getPayload), Arrays.stream(answers));

            if (allAnswers.reduce((a, b) -> a.equals(b) ? "" : a).get().length() == 0) {
                currentStage.getQuestions().get(getQuestionIndex(question)).setSolved(true);
            }
        } else {
            Answers correct = (Answers) db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).uniqueResult());

            if (correct.getPayload().equals(answers[0])) {
                currentStage.getQuestions().get(getQuestionIndex(question)).setSolved(true);
            }
        }
    }

    @Override
    public List<Question> getCurrentStageQuestions() {
        return currentStage.getQuestions();
    }

    private String getCorrectAnswerQuery(Question question) {
        return "FROM Answers WHERE QuestionId = " + question.getId() + " AND IsCorrect = true";
    }

    @Override
    public int buyAttempts(int stageId, int userId, int categoryId) throws IllegalAccessException {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));
        StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                new StageAttempts(stageId, userId, categoryId)));
        UserProgress up = db.getObject(s -> s.get(UserProgress.class, new UserProgress(userId, categoryId)));

        if (up.getReachedStage() < stage.getNumber()) { //check if stage is unlocked
            throw new IllegalAccessException("Stage is locked");
        }

        sa.setAttempts(FIRST_STAGE_ATTEMPTS - stage.getNumber());
        db.processObject(s -> s.update(sa));

        return FIRST_STAGE_ATTEMPTS - stage.getNumber();
    }
}
