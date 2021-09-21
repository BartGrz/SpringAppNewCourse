package com.udemyspringappdemo.demo.reports;

import com.udemyspringappdemo.demo.model.event.TaskEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "task_events")
public class PersistedTaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    LocalDateTime occurrence;
    int task_id;
    String name;

    public PersistedTaskEvent() {
    }

    /**
     * saving to database if changed of Task object occurred, with occurrence time
     * @param source (instance of TaskEvent class)
     */
    public PersistedTaskEvent(TaskEvent source) {
        task_id = source.getTask_id();
        name = source.getClass().getSimpleName();
        occurrence=LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }
}
