package com.udemyspringappdemo.demo.model.projection;

import com.udemyspringappdemo.demo.model.Task;

public class GroupTaskReadModel {

    private boolean done;
    private String description;

    public GroupTaskReadModel(Task source) {
        description = source.getDescription();
        done = source.isDone();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
