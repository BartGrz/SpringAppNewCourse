package com.udemyspringappdemo.demo.model;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "task's description must be not null")
    private String description;
    private boolean done;
    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Embedded
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;

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

    public int getId() {
        return id;
    }

     TaskGroup getGroup() {
        return group;
    }

     void setGroup(TaskGroup group) {
        this.group = group;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void updatefrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group=source.group;
    }

    public void deleteFrom(final Task source) {

        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group=source.group;
    }



}
