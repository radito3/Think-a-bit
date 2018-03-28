package org.elsys.netprog.game;

import org.elsys.netprog.model.User;

public class UserManagement extends AbstractGame implements UserOperations {

    public UserManagement() {
        super();
    }

    @Override
    public User login(String userName, String passWord) {
        User u = (User) db.getObject(s ->
                s.createQuery("FROM User WHERE UserName = '" + userName +
                        "' AND Password = '" + passWord + "'").uniqueResult());

        currrentUser = u;

        return u;
    }

    @Override
    public void register(User user) {
        db.processObject(s -> s.save(user));
    }

    @Override
    public void logout() {
        currrentUser = null;
    }

}
