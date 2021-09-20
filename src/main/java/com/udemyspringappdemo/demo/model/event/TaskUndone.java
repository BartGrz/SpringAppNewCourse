package com.udemyspringappdemo.demo.model.event;

import com.udemyspringappdemo.demo.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent {
    TaskUndone(Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
