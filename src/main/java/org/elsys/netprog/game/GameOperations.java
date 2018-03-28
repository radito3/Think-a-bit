package org.elsys.netprog.game;

public interface GameOperations extends Game {

    void setupEnvironment();

    void playCategory(int categoryId);

    void playStage(int stageId);
}
