package org.elsys.netprog.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "mysql->int(11)", name = "Id", nullable = false, unique = true)
    @NotNull
    private int Id;

    @Column(columnDefinition = "mysql->varchar(64)", name = "UserName", nullable = false)
    private String UserName;

    @Column(columnDefinition = "mysql->text", name = "Pass", nullable = false)
    private String Password;

    public User() {}

    public User(String UserName, String Password) {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Id == user.Id &&
                Objects.equals(UserName, user.UserName) &&
                Objects.equals(Password, user.Password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, UserName, Password);
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
