package com.udemyspringappdemo.demo.model.projection;


import com.udemyspringappdemo.demo.model.Project;
import com.udemyspringappdemo.demo.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    @Getter
    @Setter
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    @Getter
    @Setter
    @Valid
    private Set<GroupTaskWriteModel> tasks = new HashSet<>();
    @Getter
    @Setter
    private LocalDateTime deadline;

    public GroupWriteModel() {
        tasks.add(new GroupTaskWriteModel());
    }

    public TaskGroup toGroup(Project project ) {
        var result = new TaskGroup();
        result.setDescription(description);
        result.setTasks(tasks.stream()
                .map(source -> source.toTask(result))
                .collect(Collectors.toSet()));
        result.setProject(project);
        return result;
    }
}