package org.elsys.netprog.game;

import org.elsys.netprog.model.User;

public interface UserOperations extends Game {

    void register(User user);

    User login(String userName, String passWord);

    void logout();
}
