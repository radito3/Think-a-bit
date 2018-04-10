package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.Sessions;
import org.elsys.netprog.model.User;

import java.util.UUID;

public class UserManagement implements UserOperations {

    private final DatabaseUtil db;

    public UserManagement() {
        db = DatabaseUtil.getInstance();
    }

    @Override
    public User login(String userName, String password) throws IllegalAccessException {
        User user;
        String encryptedPass = User.cryptWithMD5(password);

        try {
            user = (User) db.getObject(s ->
                    s.createQuery("FROM User WHERE UserName = '" + userName +
                            "' AND Password = '" + encryptedPass + "'").uniqueResult());
            if (user == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new IllegalAccessException();
        }

        return user;
    }

    @Override
    public User register(String userName, String password) throws IllegalArgumentException, IllegalAccessException {
        User user;
        String encryptedPass = User.cryptWithMD5(password);

        try {
            user = (User) db.getObject(s -> s.createQuery("FROM User WHERE UserName = '" + userName +
                            "' AND Password = '" + encryptedPass + "'").uniqueResult());
            if (user != null) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new IllegalAccessException();
        }

        user = new User();
        user.setUserName(userName);
        user.setPassword(password);

        final User temp = user;
        db.processObject(s -> s.save(temp));

        return user;
    }

    @Override
    public void saveSessionData(final Sessions session) {
        // does not delete session that have already expired
        db.processObject(s -> s.save(session));
    }

    @Override
    public void deleteSessionData(UUID sessionId) {
        Sessions session = db.getObject(s -> s.get(Sessions.class, sessionId));
        db.processObject(s -> s.delete(session));
    }

//    @Override
//    public void update(int id, String userName, String password) {
//        User user = new User(id, userName, cryptWithMD5(password));
//        try {
//            db.getObject(s -> s.get(User.class, id));
//        } catch (Exception e) {
//            throw new IllegalArgumentException("User does not exist");
//        }
//        db.processObject(s -> s.update(user));
//    }
//
//    @Override
//    public void delete(int userId, String userName, String password) {
//        User user = new User(userId, userName, cryptWithMD5(password));
//        db.processObject(s -> s.delete(user));
//    }
}
