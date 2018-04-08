package org.elsys.netprog.game;

import org.elsys.netprog.model.User;

public interface UserOperations {

    User register(String userName, String password);

    User login(String userName, String password) throws IllegalAccessException;

    void logout();

    void deleteSessionData(int sessionId);

    void update(int id, String userName, String password);

    void delete(int userId, String userName, String password);
}
