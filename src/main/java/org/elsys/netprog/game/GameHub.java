package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
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

        UserProgress up = new UserProgress(userId, category.getId());
        if (db.getObject(s -> s.get(UserProgress.class, up)) == null) {
            db.processObject(s -> s.save(up));
        }

        List<Stages> stages = db.getObject(s -> s.createQuery("FROM Stages WHERE CategoryId = " +
                category.getId()).getResultList());
        List<StageAttempts> stageAttempts = new LinkedList<>();

        stages.forEach(s -> stageAttempts.add(db.getObject(i -> i.get(StageAttempts.class,
                new StageAttempts(s.getId(), userId, categoryId)))));
        if (stageAttempts.get(0) == null) {
            stages.forEach(st -> db.processObject(s -> s.save(new StageAttempts(st.getId(), userId, categoryId))));
        }

        return buildCategoryJson(categoryId, userId, up, category, stages).toString();
    }

    private StringBuilder buildCategoryJson(int categoryId, int userId, UserProgress up, Categories category,
                                            List<Stages> stages) {
        StringBuilder json = new StringBuilder("{\"name\":\"" + category.getName() + "\",\"stages\":[");

        stages.forEach(stage -> {
            boolean isReached = up.getReachedStage() >= stage.getNumber();
            StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                    new StageAttempts(stage.getId(), userId, categoryId)));

            json.append("{\"id\":").append(stage.getId())
                    .append(",\"isReached\":").append(isReached)
                    .append(",\"attempts\":").append(sa.getAttempts())
                    .append(",\"availableAfter\":").append(stageAvailability(sa))
                    .append("},");
        });

        json.deleteCharAt(json.lastIndexOf(","));
        json.append("]}");
        return json;
    }

    @Override
    public boolean checkIfStageIsAvailable(int stageId, int userId, int categoryId) {
        StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                new StageAttempts(stageId, userId, categoryId)));
        UserProgress up = db.getObject(s -> s.get(UserProgress.class, new UserProgress(userId, categoryId)));
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));
        return sa != null && up != null && stage != null &&
                stage.getNumber() <= up.getReachedStage() && stageAvailability(sa) == 0;
    }

    @Override
    public String playStage(int stageId, int userId, int categoryId) {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));
        StageAttempts sa;
        if ((sa = db.getObject(s -> s.get(StageAttempts.class,
                new StageAttempts(stage.getId(), userId, categoryId)))) == null) {
            sa = new StageAttempts(stage.getId(), userId, categoryId);
        }

        if (stage.getCategoryId() != categoryId) {
            throw new IllegalArgumentException("Wrong stage Id for this category");
        }

        UserProgress up;
        if ((up = db.getObject(s -> s.get(UserProgress.class, new UserProgress(userId, categoryId)))) == null) {
            up = new UserProgress(userId, categoryId);
        }

        if (stage.getNumber() <= up.getReachedStage() && stageAvailability(sa) == 0) {
            sa.setAttempts(FIRST_STAGE_ATTEMPTS - (stage.getNumber() - 1));
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
                    json.append("{\"content\":\"").append(answer.getPayload()).append("\"},")
            );
            json.deleteCharAt(json.lastIndexOf(","));
            json.append("]},");
        });

        if (json.lastIndexOf("[") != json.length() - 1)
            json.deleteCharAt(json.lastIndexOf(","));
        json.append("]}");
        return json;
    }

    private static long stageAvailability(final StageAttempts sa) {
        if (sa.getLastAttempt() == null) {
            return 0;
        }
        long time;
        return (time = -Duration.between(sa.getLastAttempt().toInstant().plusSeconds(180), Instant.now()).getSeconds())
                <= 0 ? 0 : time;
    }

    private void setStageQuestions(Stages stage) {
        List<QuestionStages> questionStages = db.getObject(s -> s.createQuery("FROM QuestionStages " +
                "WHERE StageId = " + stage.getId()).getResultList());
        List<Question> questions = new LinkedList<>();

        questionStages.forEach(qs -> questions.add(db.getObject(s -> s.get(Question.class, qs.getQuestionId()))));
        stage.setQuestions(questions);
    }

    @Override
    public void checkIfCurrentStageIsComplete(int userId, int categoryId, int stageId) {
        StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                new StageAttempts(stageId, userId, categoryId)));
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));

        if (currentStage.getQuestions().stream().allMatch(Question::isSolved)) {
            UserProgress up = db.getObject(s -> s.get(UserProgress.class, new UserProgress(userId, categoryId)));
            up.setReachedStage(stage.getNumber() + 1);
            db.processObject(s -> s.update(up));
            //TODO set next stage attempts

            sa.setAttempts(FIRST_STAGE_ATTEMPTS - (currentStage.getNumber() - 1));
        } else {
            sa.setAttempts(sa.getAttempts() - 1);
        }

        sa.setLastAttempt(Timestamp.from(Instant.now()));
        db.processObject(s -> s.update(sa));
    }

    private int getQuestionIndex(Question question) {
        int index;
        if ((index = currentStage.getQuestions().indexOf(question)) == -1) {
            throw new IndexOutOfBoundsException("Wrong questions for this stage");
        }
        return index;
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
            } else {
                currentStage.getQuestions().get(getQuestionIndex(question)).setSolved(false);
            }
        } else {
            Answers correct = (Answers) db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).uniqueResult());

            if (correct.getPayload().equals(answers[0])) {
                currentStage.getQuestions().get(getQuestionIndex(question)).setSolved(true);
            } else {
                currentStage.getQuestions().get(getQuestionIndex(question)).setSolved(false);
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
        if (sa == null) {
            throw new IllegalArgumentException("Wrong stage Id for this category");
        }
        UserProgress up = db.getObject(s -> s.get(UserProgress.class, new UserProgress(userId, categoryId)));

        if (up.getReachedStage() < stage.getNumber() || stageAvailability(sa) > 0) {
            throw new IllegalAccessException("Stage is locked");
        }

        sa.setAttempts(FIRST_STAGE_ATTEMPTS - (stage.getNumber() - 1));
        db.processObject(s -> s.update(sa));

        return FIRST_STAGE_ATTEMPTS - (stage.getNumber() - 1);
    }
}
