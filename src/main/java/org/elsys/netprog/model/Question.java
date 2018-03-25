package org.elsys.netprog.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import java.io.Serializable;

@Entity
@Table(name = "Question")
public class Question implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "Id", nullable = false, unique = true)
    private int Id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "mysql->enum('placeholder', 'placeholder1')", name = "Title", nullable = false)
    private Type Type;

    @Column(columnDefinition = "mysql->text", name = "Title", nullable = false)
    private String Title;

    public Question() {}

    public Question(int Id, Question.Type Type, String Title) {
        this.Id = Id;
        this.Type = Type;
        this.Title = Title;
    }

    private enum Type {
        PLACEHOLDER, PLACEHOLDER1
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
}
