package org.elsys.netprog.game;

import org.elsys.netprog.model.User;

public class UserManagement extends AbstractGame implements UserOperations {

    public UserManagement() {
        super();
    }

    @Override
    public User login(String userName, String password) throws IllegalAccessException {
        User user;

        try {
            user = (User) db.getObject(s ->
                    s.createQuery("FROM User WHERE UserName = '" + userName +
                            "' AND Password = '" + password + "'").uniqueResult());
        } catch (Exception e) {
            throw new IllegalAccessException("No such user"); //or more than 1 user with same credentials
        }

        currrentUser = user;

        return user;
    }

    @Override
    public void register(String userName, String password) {
        User user = new User(userName, password);
        db.processObject(s -> s.save(user));
        currrentUser = user;
    }

    @Override
    public void logout() {
        currrentUser = null;
    }

}
