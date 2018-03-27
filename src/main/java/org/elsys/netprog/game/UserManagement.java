package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.User;

public class UserManagement {

    private DatabaseUtil db;

    public UserManagement() {
//        System.setProperty("key", "val");
        db = DatabaseUtil.getInstance();
    }

    public User login(String userName, String passWord) {
        User u = (User) db.getObject(s ->
                s.createQuery("FROM User WHERE UserName = '" + userName +
                        "' AND Password = '" + passWord + "'").uniqueResult());

        System.setProperty("currentUserId", String.valueOf(u.getId()));

        return u;
    }

    public void register(User user) {
        db.processObject(s -> s.save(user));
    }

    public void logout() {
        System.clearProperty("currentUserId");
    }

}
