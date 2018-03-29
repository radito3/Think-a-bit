package org.elsys.netprog.game;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.*;

import java.util.List;
import java.util.Map;

public abstract class AbstractGame {

    /**всички нужни променливи за играта*/

    protected List<Categories> categories;

    protected final DatabaseUtil db;

    Categories currentCategory;

    Question currentQuestion;

    User currrentUser;

    Stages currentStage;

    UserProgress currentUserProgress;

    //this variable may be temporary - could use StageAttempts::getAttempts() with the needed category stage
    static final Map<Integer, Integer> STAGE_ATTEMPTS = ImmutableMap.of(1, 10,
            2, 9,
            3, 8,
            4, 7,
            5, 6);

    AbstractGame() {
        db = DatabaseUtil.getInstance();
    }

    public Categories getCurrentCategory() {
        return currentCategory;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public User getCurrrentUser() {
        return currrentUser;
    }

    public Stages getCurrentStage() {
        return currentStage;
    }

    public UserProgress getCurrentUserProgress() {
        return currentUserProgress;
    }
}
