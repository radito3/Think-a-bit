package org.elsys.netprog.game;

import org.elsys.netprog.model.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GameHub extends AbstractGame implements Game {

    private static GameHub instance;

    private GameHub() {
        super();
        setupEnvironment();
    }

    public static GameHub getInstance() {
        if (instance == null) {
            instance = new GameHub();
        }
        return instance;
    }

    private void setupEnvironment() {
        categories = IntStream.range(1, 10).mapToObj(i ->
                db.getObject(s -> s.get(Categories.class, i))).collect(Collectors.toList());

//        categories.forEach(category -> {
//
//            category.setStages();
//        });
    }

    @Override
    public GameHub playCategory(int categoryId) {
        //here there is a second select query to the db for the same info as in setupEnv()
        currentCategory = db.getObject(s -> s.get(Categories.class, categoryId));

//        currentCategory.setStages(IntStream.range(1, 10/*броя нива за тази категория*/).mapToObj(i ->
//            db.getObject(s -> s.get(Stages.class, i))).collect(Collectors.toList()));
        return this;
    }

    @Override
    public void playStage(int stageId) {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId)); //this should not be from the db
        //but from the List<Stages> in the category
        StageAttempts sa = new StageAttempts(currentStage.getId(),
                currrentUser.getId(), currentCategory.getId());

        if (currentCategory.getStages().get(0).equals(stage)) {
            currentStage = stage;
            currentStage.setUnlocked(true);

            sa.setAttempts(STAGE_ATTEMPTS.get(1));
            db.processObject(s -> s.save(sa));
            //render stage along with questions
        } else if (!stage.isUnlocked()) {
            throw new IllegalStateException("Stage is locked");
        } else {
            currentStage = stage;

            sa.setAttempts(STAGE_ATTEMPTS.get(getStageIndex(currentStage) + 1));
            //render stage
        }
        //TODO each consecutive stage in a category should have less attempts (process a StageAttempts object)
    }

    private int getStageIndex(Stages stage) {
        return currentCategory.getStages().indexOf(stage);
    }

    @Override
    public void checkIfCurrentStageIsComplete() {
        if (currentStage.getQuestions().stream().allMatch(Question::isSolved)) {
            currentStage.setUnlocked(false);
            currentStage.setTimeout(180);
        }
        //need to revise
        for (Iterator<Stages> it = currentCategory.getStages().iterator(); it.hasNext();) {
            if (it.equals(currentStage)) {
                int currentStageIndex = getStageIndex(currentStage);

                currentUserProgress = new UserProgress(currrentUser.getId(), currentCategory.getId());
                currentUserProgress.setReachedStage(currentStageIndex + 1);

                db.processObject(s -> s.save(currentUserProgress));

                StageAttempts sa = new StageAttempts(currentStage.getId(),
                        currrentUser.getId(), currentCategory.getId());
                sa.setAttempts(STAGE_ATTEMPTS.get(currentStageIndex + 1));

                db.processObject(s -> s.update(sa));

                currentStage = it.next();
                currentStage.setUnlocked(true);
                break;
            }
        }
    }

    @Override
    public GameHub playQuesion(int questionId) {
        currentQuestion = db.getObject(s -> s.get(Question.class, questionId));
        //render question with answers or field for open answer (by getType())
        return this;
    }

    @Override
    public boolean answerQuestion(String... answers) {
        if (currentQuestion.getType() == Question.Type.CLOSED_MANY) {
            Stream<Answers> correct = db.getObject(s -> s.createQuery(getCorrectAnswerQuery()).getResultStream());

            Stream<String> allAnswers = Stream.concat(correct.map(Answers::getPayload), Arrays.stream(answers));

            if (allAnswers.reduce((a, b) -> a.equals(b) ? "" : a).get().length() == 0) {
                return correctAnswer();
            } else {
                return wrongAnswer();
            }
        } else {
            Answers correct = (Answers) db.getObject(s -> s.createQuery(getCorrectAnswerQuery()).uniqueResult());

            if (correct.getPayload().equals(answers[0])) {
                return correctAnswer();
            } else {
                return wrongAnswer();
            }
        }
    }

    private String getCorrectAnswerQuery() {
        return "FROM Answers WHERE QuestionId = " + currentQuestion.getId() + " AND IsCorrect = true";
    }

    private boolean correctAnswer() {
        currentQuestion.setSolved(true);
        currentStage.getQuestions().get(currentStage.getQuestions().indexOf(currentQuestion)).setSolved(true);
        return true;
    }

    private boolean wrongAnswer() {
        currentStageAttempts.setAttempts(currentStageAttempts.getAttempts() - 1);
        db.processObject(s -> s.update(currentStageAttempts));
        return false;
    }
}
