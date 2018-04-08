package org.elsys.netprog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Stages")
public class Stages implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "Id", nullable = false, unique = true)
    private int Id;

    @Column(columnDefinition = "mysql->int(11)", name = "CategoryId", nullable = false)
    private int CategoryId;

    @Column(columnDefinition = "mysql->int(11)", name = "Number", nullable = false)
    private int Number;

    @Transient
    private List<Question> questions;

    public Stages() {}

    public Stages(int id, int categoryId) {
        this.Id = id;
        this.CategoryId = categoryId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Stages stages = (Stages) object;
        return Id == stages.Id &&
                CategoryId == stages.CategoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, CategoryId);
    }
}
