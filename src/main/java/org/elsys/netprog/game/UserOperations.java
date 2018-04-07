package org.elsys.netprog.game;

import org.elsys.netprog.model.User;

public interface UserOperations {

    void register(String userName, String password);

    void login(String userName, String password) throws IllegalAccessException;

    void logout();

    User getUser(int id);

    User getCurrentUser();
}
