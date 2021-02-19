package com.udemyspringappdemo.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "task's description must be not null")
    private String description;
    private boolean done;

    public Task() {
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

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}
