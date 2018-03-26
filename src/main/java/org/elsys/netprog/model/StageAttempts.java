package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "StageAttempts")
public class StageAttempts implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "StageId", nullable = false)
    private int StageId;

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "CategoryId", nullable = false)
    private int CategoryId;

    @Column(columnDefinition = "mysql->int(11)", name = "Attempts", nullable = false)
    private int Attempts;

    public StageAttempts() {}

    public StageAttempts(int stageId, int categoryId, int attempts) {
        this.StageId = stageId;
        this.CategoryId = categoryId;
        this.Attempts = attempts;
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
