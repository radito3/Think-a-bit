package org.elsys.netprog.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Question")
public class Question implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "Id", nullable = false, unique = true)
    private int Id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "mysql->enum('CLOSED_ONE', 'CLOSED_MANY', 'OPEN')", name = "Title", nullable = false)
    private Type Type;

    @Column(columnDefinition = "mysql->text", name = "Title", nullable = false, insertable = false, updatable = false)
    private String Title;

    @Transient
    private boolean isSolved = false;

    public Question() {}

    public Question(int Id, Question.Type Type, String Title) {
        this.Id = Id;
        this.Type = Type;
        this.Title = Title;
    }

    public enum Type {
        CLOSED_ONE, CLOSED_MANY, OPEN
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setType(Question.Type type) {
        Type = type;
    }

    public Type getType() {
        return Type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }
}
