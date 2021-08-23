package com.udemyspringappdemo.demo.logic;

import com.udemyspringappdemo.demo.model.Project;
import com.udemyspringappdemo.demo.model.TaskGroup;
import com.udemyspringappdemo.demo.model.TaskGroupRepository;
import com.udemyspringappdemo.demo.model.TaskRepository;
import com.udemyspringappdemo.demo.model.projection.GroupReadModel;
import com.udemyspringappdemo.demo.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskGroupService {

    private TaskGroupRepository repository;
    private TaskRepository taskRepository;


    public TaskGroupService( TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source, Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        return createGroup(source, null);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toogleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("TaskGroup with gven id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }


}
