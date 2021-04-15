package com.udemyspringappdemo.demo.logic;

import com.udemyspringappdemo.demo.model.*;
import com.udemyspringappdemo.demo.model.projection.GroupReadModel;
import com.udemyspringappdemo.demo.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;


    public ProjectService(ProjectRepository repository,
                          TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll() {

        return repository.findAll();
    }

    public Project save(Project toSave) {
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadLine) {

        if (!config.getTemplate().isAllowMultipleTasks() &&
                taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("only one undone group from project is allowed");
        }

      TaskGroup result =   repository.findById(projectId)
                .map(project -> {
            var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(project.getSteps()
                    .stream()
                    .map(projectStep -> new Task(
                            project.getDescription(), deadLine.plusDays(projectStep.getDaysToDeadLine())))
                    .collect(Collectors.toSet()));

            targetGroup.setProject(project);
            return taskGroupRepository.save(targetGroup);

        }).orElseThrow(()->new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }
}

