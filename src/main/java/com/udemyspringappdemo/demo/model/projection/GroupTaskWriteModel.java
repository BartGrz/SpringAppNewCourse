package com.udemyspringappdemo.demo.model.projection;

import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class GroupTaskWriteModel {

    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private LocalDateTime deadline;

    public Task toTask(TaskGroup group) {
        return new Task(description,deadline,group);

    }

}