package com.udemyspringappdemo.demo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    Page<Task> findAll(Pageable pageable);

    Optional<Task> findById(Integer id );

    void deleteInBatch(Iterable<Task> var1);

   boolean existsById (Integer id);

    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    Task save (Task entity);

    @Query("select t from Task t where t.done =:state")
    List<Task> findByDone(@Param("state") boolean state);

    @Query(nativeQuery = true , value = "Select *from TASKS t join TASK_GROUPS TG on TG.ID = t.TASK_GROUP_ID where t.TASK_GROUP_ID=:id")
    List<Task> findAllByGroup_Id(@Param("id") Integer id);

    @Query("select t from Task t where t.done=false and t.deadline < :dateTime or t.done= false and t.deadline is null")
    List<Task> findAllByDeadline(@Param("dateTime") LocalDateTime dateTime);

    //  List<Task> findByDone(@Param("state") boolean done); //ustawienie zapytania o kolumne done, ustawienie parametru za pomoca ktoreego ustala sie zapytanie
    // np localhost:8080/tasks/search/done?state=false

}
