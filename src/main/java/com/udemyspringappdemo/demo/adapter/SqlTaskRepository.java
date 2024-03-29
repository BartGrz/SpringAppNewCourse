package com.udemyspringappdemo.demo.adapter;

import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {


    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    List<Task> findAllByGroup_Id(Integer id);
}
    /*

  zmiana adnotacji z RepositoryRestResource na repository metody niżej i tak będą nam dostępne (repository dokleja @component w doc)

  metody zostawiam żeby nie zginelo, w api i tak ich nie udostepnie

    @Override
    @RestResource(exported = false ) //uniemozliwia wyslanie http delete  , blokuje operacje
    void deleteById(Integer integer);

    @Override
    @RestResource(exported = false )//uniemozliwia wyslanie http delete  , blokuje operacje trzeba nadpisac oby dwie metody delete
    void delete(Task task);


     */
//   @RestResource(path = "done", rel = "done")  ustawianie sciezki i odnosnika






