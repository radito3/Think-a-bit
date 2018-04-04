package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "QuestionStages")
public class QuestionStages implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "QuestionId", nullable = false)
    private int QuestionId;

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "StageId", nullable = false)
    private int StageId;

    public QuestionStages() {}

    public QuestionStages(int questionId, int stageId) {
        this.QuestionId = questionId;
        this.StageId = stageId;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public int getStageId() {
        return StageId;
    }

    public void setStageId(int stageId) {
        StageId = stageId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        QuestionStages that = (QuestionStages) object;
        return QuestionId == that.QuestionId &&
                StageId == that.StageId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(QuestionId, StageId);
    }
}
