package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "UserProgress")
public class UserProgress implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "UserId", nullable = false)
    private int UserId;

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "CategoryId", nullable = false)
    private int CategoryId;

    @Column(columnDefinition = "mysql->int(11)", name = "ReachedStage", nullable = false)
    private int ReachedStage = 1;

    public UserProgress() {}

    public UserProgress(int userId, int categoryId) {
        this.UserId = userId;
        this.CategoryId = categoryId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserProgress that = (UserProgress) object;
        return UserId == that.UserId &&
                CategoryId == that.CategoryId &&
                ReachedStage == that.ReachedStage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserId, CategoryId, ReachedStage);
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getReachedStage() {
        return ReachedStage;
    }

    public void setReachedStage(int reachedStage) {
        ReachedStage = reachedStage;
    }

    public void incrementReachedStage() {
        ReachedStage++;
    }
}
