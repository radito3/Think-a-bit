package org.elsys.netprog.game;

import org.elsys.netprog.db.DatabaseUtil;
import org.elsys.netprog.model.User;

public class UserManagement {

    public UserManagement() {
        System.setProperty("key", "val");
    }

    public void login() {
        DatabaseUtil db = DatabaseUtil.getInstance();

        User u1 = (User) db.getObject(s -> s.createQuery("FROM User WHERE UserName = \"test\"").uniqueResult());
    }

}
