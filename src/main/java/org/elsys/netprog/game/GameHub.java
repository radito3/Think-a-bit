package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Game functionality implementation class.
 *
 * @author Rangel Ivanov
 */
public class GameHub extends AbstractGame implements Game {

    private static GameHub instance;

    private final DatabaseUtil db;

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
        int categories = db.getObject(s ->
                s.createQuery("SELECT COUNT(Categories.Id) FROM Categories").getFetchSize());
        return IntStream.rangeClosed(1, categories).mapToObj(i ->
                db.getObject(s -> s.get(Categories.class, i))).collect(Collectors.toList());
    }

    @Override
    public String playCategory(int categoryId, int userId) {
        Categories category = db.getObject(s -> s.get(Categories.class, categoryId));

        db.processObject(s -> s.save(new UserProgress(userId, category.getId())));

        StringBuilder json = new StringBuilder("{\"name\":\"" + category.getName() + "\",\"stages\":[");

        Integer stages = (Integer) db.getObject(s ->
                s.createQuery("SELECT COUNT(Stages.Id) FROM Stages WHERE Stages.CategoryId = " + categoryId)
                        .uniqueResult());
        IntStream.rangeClosed(1, stages).mapToObj(i -> db.getObject(s -> s.get(Stages.class, i)))
                .forEach(stage -> {
                    UserProgress up = db.getObject(s -> s.get(UserProgress.class,
                            new UserProgress(userId, categoryId)));
                    boolean isReached = up.getReachedStage() == stage.getNumber();
                    StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                            new StageAttempts(stage.getId(), userId, categoryId)));
                    long seconds = 0;
                    if (sa.getLastAttempt() != null) {
                        seconds = (sa.getLastAttempt().getTime() + (180L * 1000L)) -
                                Timestamp.from(Instant.now()).getTime();
                        seconds = seconds < 0 ? 0 : seconds;
                    }

                    json.append("{\"id\":").append(stage.getId())
                            .append(",\"isReached\":").append(isReached)
                            .append(",\"attempts\":").append(sa.getAttempts())
                            .append(",\"availableAfter\":").append(seconds)
                            .append("}");
                });
        json.append("]}");

        return json.toString();
    }

    @Override
    public GameHub playStage(int stageId, int userId, int categoryId) {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));/*currentCategory.getStages().stream()
                .filter(s -> s.equals(new Stages(stageId, currentCategory.getId())))
                .findAny().get();*/
        StageAttempts sa = new StageAttempts(stage.getId(), userId, categoryId);

        if (true) { //should check the user progress -> reached stage
            currentStage = stage; //should be removed

            sa.setAttempts(10 - stage.getNumber()); //default stage attempts for 1st stage are 10
            db.processObject(s -> s.saveOrUpdate(sa));
            setStageQuestions();

            return this;
        } else {
            throw new IllegalStateException("Stage is locked");
        }
    }

    private void setStageQuestions() {
        List<Question> questions = db.getObject(s ->
                s.createQuery("FROM Question RIGHT JOIN QuestionStages " +
                        "ON Question.Id = QuestionStages.QuestionId WHERE QuestionStages.StageId = " +
                        currentStage.getId()).list()); //not tested Query::list method
        //tested way is with Query::getResultStream, then Stream.collect(Collectors.toList())
        currentStage.setQuestions(questions);
    }

    @Override
    public void checkIfCurrentStageIsComplete(int userId, int categoryId, int stageId) {
        if (currentStage.getQuestions().stream().allMatch(Question::isSolved)) {
//            currentStage.setTimeout(180); //set a variable to tell when this stage is available for playing again

            UserProgress up = db.getObject(s -> s.get(UserProgress.class, new UserProgress(userId, categoryId))); //this should be from request body
            up.setReachedStage(up.getReachedStage() + 1);

            db.processObject(s -> s.update(currentUserProgress));

            StageAttempts sa = new StageAttempts(stageId, userId, categoryId);
            sa.setAttempts(10 - currentStage.getNumber());

            db.processObject(s -> s.update(sa));

            currentStage = null;
        } else {
            StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                    new StageAttempts(currentStage.getId(), userId, categoryId)));
            sa.setAttempts(sa.getAttempts() - 1);

            db.processObject(s -> s.update(sa));
        }
        StageAttempts sa = new StageAttempts(stageId, userId, categoryId);
        sa.setLastAttempt(Timestamp.from(Instant.now()));
        db.processObject(s -> s.update(sa));
    }

    private int getQuestionIndex(Question question) {
        return currentStage.getQuestions().indexOf(question);
    }

    @Override
    public void answerQuestion(Question question, String... answers) {
        if (question.getType() == Question.Type.CLOSED_MANY) {
            Stream<Answers> correct = db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).getResultStream());

            Stream<String> allAnswers = Stream.concat(correct.map(Answers::getPayload), Arrays.stream(answers));

            if (allAnswers.reduce((a, b) -> a.equals(b) ? "" : a).get().length() == 0) {
                currentStage.getQuestions().get(getQuestionIndex(question)).setSolved(true);
            } else {
                currentStageAttempts.setAttempts(currentStageAttempts.getAttempts() - 1);
                db.processObject(s -> s.update(currentStageAttempts));
            }
        } else {
            Answers correct = (Answers) db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).uniqueResult());

            if (correct.getPayload().equals(answers[0])) {
                currentStage.getQuestions().get(getQuestionIndex(question)).setSolved(true);
            } else {
                currentStageAttempts.setAttempts(currentStageAttempts.getAttempts() - 1);
                db.processObject(s -> s.update(currentStageAttempts));
            }
        }
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

        sa.setAttempts(10 - stage.getNumber());
        db.processObject(s -> s.update(sa));

        return 10 - stage.getNumber();
    }
}
