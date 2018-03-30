package org.elsys.netprog.game;

import org.elsys.netprog.model.User;

public interface UserOperations {

    void register(String userName, String password);

    User login(String userName, String password) throws IllegalAccessException;

    void logout();
}
