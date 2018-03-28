package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Stages;
import org.elsys.netprog.model.User;

import java.util.List;

public abstract class AbstractGame {

    /**всички нужни променливи за играта*/

    protected List<Categories> categories;

    protected final DatabaseUtil db;

    Categories currentCategory;

    Question currentQuestion;

    User currrentUser;

    Stages currentStage;

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
}
