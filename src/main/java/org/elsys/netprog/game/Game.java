package org.elsys.netprog.game;

import org.elsys.netprog.model.Categories;

public interface Game {

    Categories getCurrentCategory();

    GameHub playCategory(int categoryId);

    void playStage(int stageId);

    void checkIfCurrentStageIsComplete();

    void playQuesion(int questionId);

    boolean answerQuestion(String... answers);
}
