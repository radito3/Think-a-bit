package org.elsys.netprog.game;

import org.elsys.netprog.model.*;

import java.util.List;

public abstract class AbstractGame implements Game {

//    protected List<Categories> categories;

    Categories currentCategory;

    User currentUser;

    Stages currentStage;

    UserProgress currentUserProgress;

    StageAttempts currentStageAttempts;

//    @Override
//    public List<Categories> getCategories() { return categories; }

    @Override
    public Categories getCurrentCategory() {
        return currentCategory;
    }

    @Override
    public Stages getCurrentStage() {
        return currentStage;
    }
}
