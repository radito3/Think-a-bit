package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "QuestionCategories")
public class QuestionCategories implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "QuestionId", nullable = false)
    private int QuestionId;

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "CategoryId", nullable = false)
    private int CategoryId;

    public QuestionCategories() {}

    public QuestionCategories(int Id1, int Id2) {
        this.QuestionId = Id1;
        this.CategoryId = Id2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        QuestionCategories that = (QuestionCategories) object;
        return QuestionId == that.QuestionId &&
                CategoryId == that.CategoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(QuestionId, CategoryId);
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }
}
