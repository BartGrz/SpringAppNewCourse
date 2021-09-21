package com.udemyspringappdemo.demo.model.projection;


import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
public class GroupReadModel {

    @Getter
    private int id ;
    @Getter
    @Setter
    private String description;
    /**
     * deadline from the latest task in group
     */
    @Getter
    @Setter
    private LocalDateTime deadline ;
    @Getter
    @Setter
    private Set<GroupTaskReadModel> tasks;


    public GroupReadModel(TaskGroup source) {
        id = source.getId();
        description = source.getDescription();
        source.getTasks()
                .stream()
                .map(Task::getDeadline)
                .filter(Objects::nonNull )
                .max(LocalDateTime::compareTo)
                .ifPresent(date->deadline=date);
        tasks = source.getTasks().stream().map(GroupTaskReadModel::new)
                .collect(Collectors.toSet());

    }

}