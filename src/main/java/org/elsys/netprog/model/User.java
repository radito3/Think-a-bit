package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;

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

    public User(int Id, String UserName, String Password) {
        this.Id = Id;
        this.UserName = UserName;
        this.Password = Password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        Pattern invalidInput = Pattern.compile("[^-_.a-zA-Z0-9]");

        if (invalidInput.matcher(userName).find()) {
            throw new IllegalArgumentException("Illegal characters in Username");
        }

        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Pattern invalidInput = Pattern.compile("[^-_.a-zA-Z0-9]");

        if (invalidInput.matcher(password).find()) {
            throw new IllegalArgumentException("Illegal characters in Password");
        }

        Password = cryptWithMD5(password);
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

    public static String cryptWithMD5(String pass) {
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
