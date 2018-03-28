package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Answers")
public class Answers implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "Id", nullable = false, unique = true)
    private int Id;

    @Column(columnDefinition = "mysql->int(11)", name = "QuestionId", nullable = false)
    private int QuestionId;

    @Column(columnDefinition = "mysql->text", name = "Payload", nullable = false)
    private String Payload;

    @Column(columnDefinition = "mysql->boolean", name = "IsCorrect", nullable = false)
    private boolean IsCorrect = false;

    public Answers() {}

    public Answers(int id, int questionId, String payload, boolean isCorrect) {
        this.Id = id;
        this.QuestionId = questionId;
        this.Payload = payload;
        this.IsCorrect = isCorrect;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public String getPayload() {
        return Payload;
    }

    public void setPayload(String payload) {
        Payload = payload;
    }

    public boolean isCorrect() {
        return IsCorrect;
    }

    public void setCorrect(boolean correct) {
        IsCorrect = correct;
    }
}
