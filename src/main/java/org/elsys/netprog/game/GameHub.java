package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.*;

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
        return IntStream.range(1, 10).mapToObj(i ->
                db.getObject(s -> s.get(Categories.class, i))).collect(Collectors.toList());
    }

    @Override
    public Categories playCategory(int categoryId) {
        Categories category = db.getObject(s -> s.get(Categories.class, categoryId));

        category.setStages(IntStream.range(1, 4).mapToObj(i -> //3 stages for 1st category (temporary)
            db.getObject(s -> s.get(Stages.class, i))).collect(Collectors.toList()));

        return category;
    }

    @Override
    public GameHub playStage(int stageId, int userId) {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));/*currentCategory.getStages().stream()
                .filter(s -> s.equals(new Stages(stageId, currentCategory.getId())))
                .findAny().get();*/
        StageAttempts sa = new StageAttempts(currentStage == null ? stage.getId() : currentStage.getId(),
                userId, currentCategory.getId());

        if (true) { //should check the user progress -> reached stage
            currentStage = stage;

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

    private int getStageIndex(Stages stage) {
        return currentCategory.getStages().indexOf(stage);
    }

    @Override
    public boolean checkIfCurrentStageIsComplete(int userId, int categoryId, int stageId) {
        if (currentStage.getQuestions().stream().allMatch(Question::isSolved)) {
//            currentStage.setTimeout(180); //set a variable to tell when this stage is available for playing again

            int currentStageIndex = getStageIndex(currentStage);

            currentUserProgress = new UserProgress(userId, categoryId); //this should be from request body
            currentUserProgress.incrementReachedStage();

            db.processObject(s -> s.save(currentUserProgress));

            StageAttempts sa = new StageAttempts(currentStage.getId(), userId, categoryId);
            sa.setAttempts(10 - currentStageIndex);

            db.processObject(s -> s.update(sa));

            currentStage = null;

            return true;
        } else {
            StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                    new StageAttempts(currentStage.getId(), userId, categoryId)));
            sa.setAttempts(sa.getAttempts() - 1);

            db.processObject(s -> s.update(sa));

            return false;
        }
    }

    @Override
    public void answerQuestion(Question question, String... answers) {
        if (question.getType() == Question.Type.CLOSED_MANY) {
            Stream<Answers> correct = db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).getResultStream());

            Stream<String> allAnswers = Stream.concat(correct.map(Answers::getPayload), Arrays.stream(answers));

            if (allAnswers.reduce((a, b) -> a.equals(b) ? "" : a).get().length() == 0) {
                currentStage.getQuestions().get(currentStage.getQuestions().indexOf(question)).setSolved(true);
            } else {
                currentStageAttempts.setAttempts(currentStageAttempts.getAttempts() - 1);
                db.processObject(s -> s.update(currentStageAttempts));
            }
        } else {
            Answers correct = (Answers) db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).uniqueResult());

            if (correct.getPayload().equals(answers[0])) {
                currentStage.getQuestions().get(currentStage.getQuestions().indexOf(question)).setSolved(true);
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
    public void buyAttempts(int stageId, int userId, int categoryId) throws IllegalAccessException {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));
        StageAttempts sa = db.getObject(s -> s.get(StageAttempts.class,
                new StageAttempts(stageId, userId, categoryId)));
        UserProgress up = new UserProgress(userId, categoryId);

        if (up.getReachedStage() < (getStageIndex(stage) + 1)) { //check if stage is unlocked
            throw new IllegalAccessException("Stage is locked");
        }

        sa.setAttempts(10 - getStageIndex(stage));
        db.processObject(s -> s.update(sa));
    }
}
