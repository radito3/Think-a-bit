package org.elsys.netprog.game;

import org.elsys.netprog.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserManagement extends AbstractGame implements UserOperations {

    public UserManagement() {
        super();
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

        currrentUser = user;

        return user;
    }

    @Override
    public void register(String userName, String password) {
        User user = new User(userName, cryptWithMD5(password));
        db.processObject(s -> s.save(user));
        currrentUser = user;
    }

    @Override
    public void logout() {
        currrentUser = null;
        //render index page without user
    }

    @Override
    public User getUser(int id) {
        return db.getObject(s -> s.get(User.class, id));
    }

//    public void update() {
//
//    }
//
//    public void delete() {
//
//    }

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
