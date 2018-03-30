package org.elsys.netprog.game;

public interface Game {

    void playCategory(int categoryId);

    void playStage(int stageId);

    void checkIfCurrentStageIsComplete();

    void playQuesion(int questionId);

    boolean answerQuestion(String... answers);
}
