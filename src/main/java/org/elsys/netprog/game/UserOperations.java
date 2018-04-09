package org.elsys.netprog.game;

import org.elsys.netprog.model.Sessions;
import org.elsys.netprog.model.User;

import java.util.UUID;

public interface UserOperations {

    User register(String userName, String password) throws IllegalArgumentException, IllegalAccessException;

    User login(String userName, String password) throws IllegalAccessException;

    void deleteSessionData(UUID sessionId);

    void saveSessionData(Sessions session);

//    void update(int id, String userName, String password);

//    void delete(int userId, String userName, String password);
}
