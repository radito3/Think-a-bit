package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "StageAttempts")
public class StageAttempts implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "StageId", nullable = false)
    private int StageId;

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "UserId", nullable = false)
    private int UserId;

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "CategoryId", nullable = false)
    private int CategoryId;

    @Column(columnDefinition = "mysql->int(11)", name = "Attempts", nullable = false)
    private int Attempts = 0;

    public StageAttempts() {}

    public StageAttempts(int stageId, int userId, int categoryId) {
        this.StageId = stageId;
        this.UserId = userId;
        this.CategoryId = categoryId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StageAttempts that = (StageAttempts) object;
        return StageId == that.StageId &&
                UserId == that.UserId &&
                CategoryId == that.CategoryId &&
                Attempts == that.Attempts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(StageId, UserId, CategoryId, Attempts);
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getStageId() {
        return StageId;
    }

    public void setStageId(int stageId) {
        StageId = stageId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getAttempts() {
        return Attempts;
    }

    public void setAttempts(int attempts) {
        Attempts = attempts;
    }
}
