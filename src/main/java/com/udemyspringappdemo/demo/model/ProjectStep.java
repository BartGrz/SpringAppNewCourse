package com.udemyspringappdemo.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_steps")
public class ProjectStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Project step's description must not be empty")
    private String description;
   // private int project_id;

    private int days_to_deadline;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    public ProjectStep() {
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

    public void setDescription(String description) {
        this.description = description;
    }



    public int getDaysToDeadLine() {
        return days_to_deadline;
    }

    public void setDaysToDeadLine(int days_to_deadLine) {
        this.days_to_deadline = days_to_deadLine;
    }

     Project getProject() {
        return project;
    }

     void setProject(Project project) {
        this.project = project;
    }
}
