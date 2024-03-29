package com.udemyspringappdemo.demo.reports;

import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/reports")
@RestController
public class ReportController {

    private final TaskRepository taskRepository;
    private final PersistedTaskEventRepository eventRepository;

    public ReportController(TaskRepository taskRepository, PersistedTaskEventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable("id") int id) {
        return taskRepository
                .findById(id)
                .map(task -> new TaskWithChangesCount(task, eventRepository.findByTask_id(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * inner class, description and done fields are the same as int the Task entity,
     * the one additional filed will represent how many times Task object has been changed
     */
    private class TaskWithChangesCount {
        public String description;
        public boolean done;
        public int changesCount;

        private TaskWithChangesCount(Task task, List<PersistedTaskEvent> events) {
            description=task.getDescription();
            done=task.isDone();
            changesCount = events.size();

        }
    }
}
