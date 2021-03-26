package com.udemyspringappdemo.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    @OneToMany(mappedBy = "project")
    private Set<ProjectStep> groups ;
    @OneToMany( cascade = CascadeType.ALL,mappedBy = "project")
    private Set<ProjectStep> steps;



    public Project() {
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

     void setDescription(String description) {
        this.description = description;
    }

     Set<ProjectStep> getGroups() {
        return groups;
    }

     void setGroups(Set<ProjectStep> groups) {
        this.groups = groups;
    }

     Set<ProjectStep> getSteps() {
        return steps;
    }

     void setSteps(Set<ProjectStep> steps) {
        this.steps = steps;
    }
}
