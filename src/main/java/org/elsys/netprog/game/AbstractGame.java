package org.elsys.netprog.game;

import org.elsys.netprog.model.*;

public abstract class AbstractGame implements Game {

    Categories currentCategory;
    //to check the stage which is reached -> userProgress.getReachedStage

    Stages currentStage;

    UserProgress currentUserProgress;

    StageAttempts currentStageAttempts;

    @Override
    public Categories getCurrentCategory() {
        return currentCategory;
    }

    @Override
    public Stages getCurrentStage() {
        return currentStage;
    }
}
