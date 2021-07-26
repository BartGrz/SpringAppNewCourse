package com.udemyspringappdemo.demo.model.projection;


import com.udemyspringappdemo.demo.model.Project;
import com.udemyspringappdemo.demo.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Set<GroupTaskWriteModel> tasks;

    public TaskGroup toGroup(Project project) {
        var result = new TaskGroup();
        result.setDescription(description);
        result.setTasks(tasks.stream()
                .map(source -> source.toTask(result))
                .collect(Collectors.toSet()));
        result.setProject(project);
        return result;
    }
}