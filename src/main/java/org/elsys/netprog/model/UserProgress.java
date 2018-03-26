package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
    private int ReachedStage = 0;

    public UserProgress() {}

    public UserProgress(int userId, int categoryId, int reachedStage) {
        this.UserId = userId;
        this.CategoryId = categoryId;
        this.ReachedStage = reachedStage;
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
}
