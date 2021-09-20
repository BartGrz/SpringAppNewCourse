package com.udemyspringappdemo.demo.reports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface PersistedTaskEventRepository extends JpaRepository<PersistedTaskEvent,Integer> {

    @Override
    List<PersistedTaskEvent> findAll();
    @Query("select ps from PersistedTaskEvent ps where ps.task_id=:task_id")
    List<PersistedTaskEvent> findByTask_id(@Param("task_id") int task_id);
}
