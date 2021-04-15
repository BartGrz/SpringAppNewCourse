package com.udemyspringappdemo.demo.model;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface TaskGroupRepository {

    List<TaskGroup> findAll();

    Optional<TaskGroup> findById(Integer id);

    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);

    TaskGroup save (TaskGroup entity);

    //List<Project> findAllByDoneIsFalse();

}
