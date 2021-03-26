package com.udemyspringappdemo.demo.adapter;

import com.udemyspringappdemo.demo.model.Project;
import com.udemyspringappdemo.demo.model.ProjectRepository;
import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project,Integer> {


    @Override
    @Query("SELECT distinct g from Project p join fetch p.steps")
    List<Project> findAll();




}
