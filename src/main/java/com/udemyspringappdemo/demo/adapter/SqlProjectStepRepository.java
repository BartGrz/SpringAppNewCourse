package com.udemyspringappdemo.demo.adapter;

import com.udemyspringappdemo.demo.model.ProjectStep;
import com.udemyspringappdemo.demo.model.ProjectStepRepository;
import com.udemyspringappdemo.demo.model.TaskGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlProjectStepRepository extends ProjectStepRepository, JpaRepository<ProjectStep, Integer> {
}
