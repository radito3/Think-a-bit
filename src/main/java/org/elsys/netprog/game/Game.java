package org.elsys.netprog.game;

import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;

public interface Game {

    Categories getCurrentCategory();

    Question getCurrentQuestion();

    GameHub playCategory(int categoryId);

    void playStage(int stageId);

    void checkIfCurrentStageIsComplete();

    GameHub playQuesion(int questionId);

    boolean answerQuestion(String... answers);
}
