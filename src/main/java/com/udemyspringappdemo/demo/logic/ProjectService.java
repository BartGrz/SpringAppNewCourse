package com.udemyspringappdemo.demo.logic;

import com.udemyspringappdemo.demo.model.Project;
import com.udemyspringappdemo.demo.model.ProjectRepository;
import com.udemyspringappdemo.demo.model.TaskConfigurationProperties;
import com.udemyspringappdemo.demo.model.TaskGroupRepository;
import com.udemyspringappdemo.demo.model.projection.GroupReadModel;
import com.udemyspringappdemo.demo.model.projection.GroupTaskWriteModel;
import com.udemyspringappdemo.demo.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class ProjectService {

    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService service;


    public ProjectService(ProjectRepository repository,
                          TaskGroupRepository taskGroupRepository,
                          TaskConfigurationProperties config, TaskGroupService service) {

        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
        this.service = service;
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

        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(project.getSteps()
                            .stream()
                            .map(projectStep -> {
                                var task = new GroupTaskWriteModel();
                                task.setDescription(projectStep.getDescription());
                                task.setDeadline(deadLine.plusDays(projectStep.getDaysToDeadLine()));
                                return task;
                            })
                            .collect(Collectors.toSet()));

                    return service.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }
}

