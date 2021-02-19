package com.udemyspringappdemo.demo.controller;

import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController //zmiana adnotacji z @RepositoryRestController na RestController [wystarczyłby @controller] (teraz mamy tylko odczyt tasków)
public class TaskController {

    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
   private final TaskRepository repository;


     TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks",params ={"!sort","!page","!size"} )
    ResponseEntity<List<Task>> readAllTasks() {
         logger.warn("Exposing all the tasks!");
         return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping("/tasks" )
    ResponseEntity<List<Task>>readAllTasks(Pageable pageable) {

        logger.info("custom pageable");
        return ResponseEntity.ok(repository.findAll(pageable).getContent());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask( @PathVariable int id , @RequestBody @Valid Task toUpdate) {

         if(!repository.existsById(id)) {
             return ResponseEntity.notFound().build();
         }
         toUpdate.setId(id);
         repository.save(toUpdate);
         return ResponseEntity.noContent().build();
    }

}
