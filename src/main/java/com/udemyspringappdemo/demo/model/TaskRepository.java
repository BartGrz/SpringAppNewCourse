package com.udemyspringappdemo.demo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

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

  //  List<Task> findByDone(@Param("state") boolean done); //ustawienie zapytania o kolumne done, ustawienie parametru za pomoca ktoreego ustala sie zapytanie
    // np localhost:8080/tasks/search/done?state=false

}
