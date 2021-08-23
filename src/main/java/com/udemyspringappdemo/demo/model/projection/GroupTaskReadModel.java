package com.udemyspringappdemo.demo.model.projection;

import com.udemyspringappdemo.demo.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class GroupTaskReadModel {


    private boolean done;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private LocalDateTime deadline;

    public GroupTaskReadModel(Task source) {
        description = source.getDescription();
        done = source.isDone();
        deadline=source.getDeadline();
    }

    public boolean isDone() {
        return done;
    }
}
