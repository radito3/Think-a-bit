package org.elsys.netprog.game;

import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.Stages;
import org.elsys.netprog.model.User;

public interface Game {

    Categories getCurrentCategory();

    Question getCurrentQuestion();

    User getCurrrentUser();

    Stages getCurrentStage();

}
