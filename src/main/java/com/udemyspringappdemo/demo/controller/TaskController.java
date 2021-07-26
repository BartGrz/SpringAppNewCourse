package com.udemyspringappdemo.demo.controller;

import com.udemyspringappdemo.demo.logic.TaskService;
import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@RestController
//zmiana adnotacji z @RepositoryRestController na RestController [wystarczyłby @controller] (teraz mamy tylko odczyt tasków)
@RequestMapping("/tasks")
public class TaskController {

    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;
    private final TaskService service;

    //mozna tak @Qualifier("sqlTaskRepository"), rozwiazanie konfliktu z duplikatem beanu w testach
    TaskController(final TaskRepository repository, TaskService service) {
        this.repository = repository;
        this.service = service;
    }

    /**
     * if we want it to be async :
     * CompletableFuture<ResponseEntity<List<Task>>> readAllTasks() {
     * logger.warn("Exposing all the tasks!");
     * return service.findAllAsync().thenApply(ResponseEntity::ok);
     * @return
     */
    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());

    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {

        logger.info("custom pageable");
        return ResponseEntity.ok(repository.findAll(pageable).getContent());

    }

    @GetMapping("/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {

        return repository.findById(id).map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("search/undone/till-today")
    ResponseEntity<List<Task>> readAllUndoneForTodayTasks() {
        var result = repository.findAllByDeadline(LocalDateTime.now());
        if(result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }


    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> {
            task.updatefrom(toUpdate);
            repository.save(task);
        });
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(repository.findByDone(state));
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {

        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTask(@PathVariable int id, @RequestBody @Valid Task toDelete) {

        if (!repository.existsById(id)) {

            return ResponseEntity.notFound().build();
        }
        //  toDelete.setId(id);
        repository.findById(id).ifPresent(task -> task.deleteFrom(toDelete));
        repository.deleteInBatch(Collections.singleton(toDelete));

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toogleTask(@PathVariable int id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }

}
