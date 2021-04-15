package com.udemyspringappdemo.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "task_groups")
public class TaskGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task group's description must be not null")
    private String description;
    private boolean done;
    @OneToMany( cascade = CascadeType.ALL,mappedBy = "group") //domyslnie dla kolekcji ustawiony jest fetch = FetchType.LAZY,
    private Set<Task> tasks ;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    public TaskGroup() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripton) {
        this.description = descripton;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

     public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}


