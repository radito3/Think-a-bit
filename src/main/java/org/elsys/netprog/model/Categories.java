package org.elsys.netprog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Categories")
public class Categories implements Serializable {

    @Id
    @Column(columnDefinition = "mysql->int(11)", name = "Id", nullable = false, unique = true)
    private int Id;

    @Column(columnDefinition = "mysql->varchar(32)", name = "Name", nullable = false)
    private String Name;

    @Transient
    private List<Stages> stages;

    public Categories() {}

    public Categories(int Id, String Name) {
        this.Id = Id;
        this.Name = Name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Stages> getStages() {
        return stages;
    }

    public void setStages(List<Stages> stages) {
        this.stages = stages;
    }
}
