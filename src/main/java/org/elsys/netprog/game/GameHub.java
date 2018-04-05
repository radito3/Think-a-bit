package org.elsys.netprog.game;

import org.elsys.netprog.model.*;

import java.util.Arrays;
import java.util.Iterator;
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

    private GameHub() {
        super();
        categories = IntStream.range(1, 10).mapToObj(i ->
                db.getObject(s -> s.get(Categories.class, i))).collect(Collectors.toList());
    }

    public static GameHub getInstance() {
        if (instance == null) {
            instance = new GameHub();
        }
        return instance;
    }

    @Override
    public GameHub playCategory(int categoryId) {
        currentCategory = db.getObject(s -> s.get(Categories.class, categoryId));

        currentCategory.setStages(IntStream.range(1, 4).mapToObj(i -> //3 stages for 1st category (temporary)
            db.getObject(s -> s.get(Stages.class, i))).collect(Collectors.toList()));

        return this;
    }

    @Override
    public List<Question> playStage(int stageId) {
        Stages stage = db.getObject(s -> s.get(Stages.class, stageId));
        StageAttempts sa = new StageAttempts(currentStage == null ? stage.getId() : currentStage.getId(),
                currrentUser.getId(), currentCategory.getId());

        if (currentCategory.getStages().get(0).equals(stage)) {
            currentStage = stage;
            currentStage.setUnlocked(true);
            currentCategory.getStages().get(getStageIndex(stage)).setUnlocked(true);

            sa.setAttempts(10);
            db.processObject(s -> s.save(sa));
            setStageQuestions();

            return currentStage.getQuestions();
        } else if (!stage.isUnlocked()) {
            throw new IllegalStateException("Stage is locked");
        } else {
            currentStage = stage;

            sa.setAttempts(10 - getStageIndex(stage));
            db.processObject(s -> s.update(sa));
            setStageQuestions();

            return currentStage.getQuestions();
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
    public void checkIfCurrentStageIsComplete() { //need to revise
        if (currentStage.getQuestions().stream().allMatch(Question::isSolved)) {
            currentStage.setUnlocked(false);
            currentStage.setTimeout(180);
        }

        for (Iterator<Stages> it = currentCategory.getStages().iterator(); it.hasNext();) {
            if (it.equals(currentStage)) {
                int currentStageIndex = getStageIndex(currentStage);

                currentUserProgress = new UserProgress(currrentUser.getId(), currentCategory.getId());
                currentUserProgress.setReachedStage(currentStageIndex + 1);

                db.processObject(s -> s.save(currentUserProgress));

                StageAttempts sa = new StageAttempts(currentStage.getId(),
                        currrentUser.getId(), currentCategory.getId());
                sa.setAttempts(10 - currentStageIndex);

                db.processObject(s -> s.update(sa));

                currentStage = it.next();
                currentStage.setUnlocked(true);
                break;
            }
        }
    }

    @Override
    public boolean answerQuestion(Question question, String... answers) {
        if (question.getType() == Question.Type.CLOSED_MANY) {
            Stream<Answers> correct = db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).getResultStream());

            Stream<String> allAnswers = Stream.concat(correct.map(Answers::getPayload), Arrays.stream(answers));

            if (allAnswers.reduce((a, b) -> a.equals(b) ? "" : a).get().length() == 0) {
                return correctAnswer(question);
            } else {
                return wrongAnswer();
            }
        } else {
            Answers correct = (Answers) db.getObject(s ->
                    s.createQuery(getCorrectAnswerQuery(question)).uniqueResult());

            if (correct.getPayload().equals(answers[0])) {
                return correctAnswer(question);
            } else {
                return wrongAnswer();
            }
        }
    }

    private String getCorrectAnswerQuery(Question question) {
        return "FROM Answers WHERE QuestionId = " + question.getId() + " AND IsCorrect = true";
    }

    private boolean correctAnswer(Question question) {
        currentStage.getQuestions().get(currentStage.getQuestions().indexOf(question)).setSolved(true);
        return true;
    }

    private boolean wrongAnswer() {
        currentStageAttempts.setAttempts(currentStageAttempts.getAttempts() - 1);
        db.processObject(s -> s.update(currentStageAttempts));
        return false;
    }
}
