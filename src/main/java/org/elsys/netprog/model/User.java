package org.elsys.netprog.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Users", schema = "Think_a_bitDB")
public class User implements Serializable {

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
