package com.udemyspringappdemo.demo.logic;

import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * creating async service for getting all task from database
 */
@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * CompletableFuture will preapere data and if succed it will contain List of tasks
     * annotation @Async added, and @EnableAsync added to the main method
     * @return
     */
    @Async
    public CompletableFuture<List<Task>> findAllAsync() {
    logger.info("Supply Async");
        return CompletableFuture.supplyAsync(taskRepository::findAll);
    }
}
