package com.udemyspringappdemo.demo.controller;

import com.udemyspringappdemo.demo.logic.TaskGroupService;
import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import com.udemyspringappdemo.demo.model.projection.GroupReadModel;
import com.udemyspringappdemo.demo.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Controller
//zmiana adnotacji z @RepositoryRestController na RestController [wystarczyłby @controller] (teraz mamy tylko odczyt tasków)
@RequestMapping("/groups")
public class TaskGroupController {

    public static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskRepository repository;
    private final TaskGroupService taskGroupService;

    TaskGroupController(final TaskRepository repository, TaskGroupService taskGroupService) {
        this.repository = repository;
        this.taskGroupService = taskGroupService;
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups(Pageable pageable) {

        logger.info("custom pageable");
        return ResponseEntity.ok(taskGroupService.readAll());

    }

    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }


    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {

        return ResponseEntity.created(URI.create("/")).body(taskGroupService.createGroup(toCreate));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toogleGroup(@PathVariable int id) {

        taskGroupService.toogleGroup(id);
        return ResponseEntity.noContent().build();
    }

}
