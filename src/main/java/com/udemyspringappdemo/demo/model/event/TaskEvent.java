package com.udemyspringappdemo.demo.model.event;

import com.udemyspringappdemo.demo.model.Task;
import lombok.Getter;

import java.time.Clock;
import java.time.Instant;

public abstract class TaskEvent {

    public static TaskEvent changed(Task source) {
        return source.isDone() ? new TaskDone(source) : new TaskUndone(source);
    }

    @Getter
    private int task_id;
    @Getter
    private Instant occurrence;

    public TaskEvent(int task_id, Clock clock) {
        this.task_id = task_id;
        this.occurrence = Instant.now(clock);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "task_id=" + task_id +
                ", occurrence=" + occurrence +
                '}';
    }
}
