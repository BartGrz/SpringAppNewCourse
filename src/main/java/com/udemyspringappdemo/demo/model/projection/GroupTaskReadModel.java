package com.udemyspringappdemo.demo.model.projection;

import com.udemyspringappdemo.demo.model.Task;
import lombok.Getter;
import lombok.Setter;

public class GroupTaskReadModel {

    @Getter
    @Setter
    private boolean done;
    @Getter
    @Setter
    private String description;

    public GroupTaskReadModel(Task source) {
        description = source.getDescription();
        done = source.isDone();
    }

}
