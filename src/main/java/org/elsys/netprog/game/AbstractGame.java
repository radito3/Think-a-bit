package org.elsys.netprog.game;

import org.elsys.netprog.model.StageAttempts;
import org.elsys.netprog.model.Stages;
import org.elsys.netprog.model.UserProgress;

public abstract class AbstractGame implements Game {

    //to check the stage which is reached -> userProgress.getReachedStage

    Stages currentStage;

    UserProgress currentUserProgress;

    StageAttempts currentStageAttempts;

    @Override
    public Stages getCurrentStage() {
        return currentStage;
    }
}
