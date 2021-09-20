package com.udemyspringappdemo.demo.model.event;

import com.udemyspringappdemo.demo.model.Task;

import java.time.Clock;

public class TaskDone extends TaskEvent {
    TaskDone(Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
