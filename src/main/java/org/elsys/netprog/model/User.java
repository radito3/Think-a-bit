package org.elsys.netprog.model;

public class User {

    private int Id;
    private String UserName;
    private String Password;

    public User() {}

    public User(int Id, String UserName, String Password) {
        this.Id = Id;
        this.UserName = UserName;
        this.Password = Password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        //validation check
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        //check for string validation
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        //check for string validation
        Password = password;
    }
}
