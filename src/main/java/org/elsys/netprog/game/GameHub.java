package org.elsys.netprog.game;

import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Stages;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameHub extends AbstractGame implements Game {

    public GameHub() {
        super();
        setupEnvironment();
    }

    private void setupEnvironment() {
        categories = IntStream.range(1, 10/*брой категории*/).mapToObj(i ->
                db.getObject(s -> s.get(Categories.class, i))).collect(Collectors.toList());
        //TODO set QuestionCategories appropriately to the Category
    }

    @Override
    public void playCategory(int categoryId) {
        //here there is a second select query to the db for the same info as in setupEnv()
        currentCategory = db.getObject(s -> s.get(Categories.class, categoryId));

        currentCategory.setStages(IntStream.range(1, 10/*броя нива за тази категория*/).mapToObj(i ->
            db.getObject(s -> s.get(Stages.class, i))).collect(Collectors.toList()));
    }

    @Override
    public void playStage(int stageId) {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));

        if (currentCategory.getStages().get(0).equals(stage)) {
            currentStage = stage;
            currentStage.setUnlocked(true);
            //render stage along with questions
        } else if (!stage.isUnlocked()) {
            throw new IllegalStateException("Stage is locked");
        } else {
            currentStage = stage;
            //render stage
        }
        //TODO each consecutive stage in a category should have less attempts (process a StageAttempts object)
    }

    @Override
    public void checkIfCurrentStageIsComplete() {
        if (currentStage.getQuestions().stream().allMatch(Question::isSolved)) {
            currentStage.setUnlocked(false);
            currentStage.setTimeout(180);
        }

        for (Iterator<Stages> it = currentCategory.getStages().iterator(); it.hasNext(); it.next()) {
            if (it.equals(currentStage)) {
                it.next().setUnlocked(true);
                //TODO insert into UserProgress, reset current stage attempts in StageAttempts
                break;
            }
        }
    }

    @Override
    public void playQuesion(int questionId) {
        currentQuestion = db.getObject(s -> s.get(Question.class, questionId));
        //render question with answers or field for open answer (by getType())
    }

    @Override
    public <T> void answerQuestion(T[] answer) {
//        if (currentQuestion.getType() == Question.Type.CLOSED_MANY &&
//                answer.getClass() != Integer[].class) {
//            if (answer.length == 1) {
//                throw new IllegalArgumentException("Invalid type of argument");
//            }
//        } else if (currentQuestion.getType() == Question.Type.CLOSED_ONE &&
//                answer.getClass() != Integer[].class) {
//            if (answer.length != 1) {
//                throw new IllegalArgumentException("Invalid type of argument");
//            }
//        } else if (currentQuestion.getType() == Question.Type.OPEN &&
//                answer.getClass() != String[].class) {
//            if (answer.length != 1) {
//                throw new IllegalArgumentException("Invalid type of argument");
//            }
//        }
        //TODO check if correct type of answer for question (string - open, int[] - closed_many, int - closed_one)
        //TODO if correct -> question is rendered answered (Question::setSolved(true)), attempts are not reduced
        //TODO if incorrect -> StageAttempts.setAttempts (getAttempts - 1)
    }

}
