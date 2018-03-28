package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Stages;
import org.elsys.netprog.model.User;

import java.util.List;

public abstract class AbstractGame implements Game {

    /**всички нужни променливи за играта*/

    protected List<Categories> categories;

    protected final DatabaseUtil db;

    protected Categories currentCategory;

    protected Question currentQuestion;

    protected User currrentUser;

    protected Stages currentStage;

    protected AbstractGame() {
        db = DatabaseUtil.getInstance();
    }

    @Override
    public Categories getCurrentCategory() {
        return currentCategory;
    }

    @Override
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    @Override
    public User getCurrrentUser() {
        return currrentUser;
    }

    @Override
    public Stages getCurrentStage() {
        return currentStage;
    }
}
