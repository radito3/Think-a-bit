package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.Sessions;
import org.elsys.netprog.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserManagement implements UserOperations {

    private final DatabaseUtil db;

    public UserManagement() {
        db = DatabaseUtil.getInstance();
    }

    @Override
    public User login(String userName, String password) throws IllegalAccessException {
        User user;
        String encryptedPass = cryptWithMD5(password);

        try {
            user = (User) db.getObject(s ->
                    s.createQuery("FROM User WHERE UserName = '" + userName +
                            "' AND Password = '" + encryptedPass + "'").uniqueResult());
        } catch (Exception e) {
            throw new IllegalAccessException("No such user"); //or more than 1 user with same credentials
        }

        return user;
    }

    @Override
    public User register(String userName, String password) {
        User user = new User(userName, cryptWithMD5(password));
        db.processObject(s -> s.save(user));

        return user;
    }

    @Override
    public void deleteSessionData(int sessionId) {
        Sessions session = db.getObject(s -> s.get(Sessions.class, sessionId));
        db.processObject(s -> s.delete(session));
    }

    @Override
    public void update(int id, String userName, String password) {
        User user = new User(id, userName, cryptWithMD5(password));
        try {
            db.getObject(s -> s.get(User.class, id));
        } catch (Exception e) {
            throw new IllegalArgumentException("User does not exist");
        }
        db.processObject(s -> s.update(user));
    }

    @Override
    public void delete(int userId, String userName, String password) {
        User user = new User(userId, userName, cryptWithMD5(password));
        db.processObject(s -> s.delete(user));
    }

    private static String cryptWithMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);

            StringBuilder sb = new StringBuilder();
            for (byte aDigested : digested) {
                sb.append(Integer.toHexString(0xff & aDigested));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
