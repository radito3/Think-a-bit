package org.elsys.netprog.game;

public interface Game {

    void playCategory(int categoryId);

    void playStage(int stageId);

    void checkIfCurrentStageIsComplete();

    void playQuesion(int questionId);

    <T> void answerQuestion(T[] answer);
}
