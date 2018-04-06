package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.*;

import java.util.List;

public abstract class AbstractGame implements Game {

    /**all game variables*/

    protected List<Categories> categories;

    protected final DatabaseUtil db;

    Categories currentCategory;

    User currentUser;

    Stages currentStage;

    UserProgress currentUserProgress;

    StageAttempts currentStageAttempts;

    AbstractGame() {
        db = DatabaseUtil.getInstance();
    }

    /**these are to be used when building the json models for React rendering*/

    @Override
    public List<Categories> getCategories() { return categories; }

    @Override
    public Categories getCurrentCategory() {
        return currentCategory;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public Stages getCurrentStage() {
        return currentStage;
    }

    public UserProgress getCurrentUserProgress() {
        return currentUserProgress;
    }

    public StageAttempts getCurrentStageAttempts() {
        return currentStageAttempts;
    }
}
