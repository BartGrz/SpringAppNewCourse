package com.udemyspringappdemo.demo.adapter;

import com.udemyspringappdemo.demo.model.Project;
import com.udemyspringappdemo.demo.model.TaskGroup;
import com.udemyspringappdemo.demo.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {

    @Override
    @Query("select distinct g from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();

    @Query("from TaskGroup g join fetch g.tasks where g.done = false ")
    List<Project> findAllByDoneIsFalse();

    @Override
    @Query(nativeQuery = true,value = "SELECT count(*) > 0 from TASK_GROUPS where DESCRIPTION= :desc ")
    boolean existByDescription(@Param("desc") String description);

    @Override
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);

    @Override
    TaskGroup save(TaskGroup entity);
}
