package com.udemyspringappdemo.demo;

import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

@Configuration
@Profile("integration")
public class TestConfiguration {


    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource() {

        var result = new DriverManagerDataSource("jdbc:h2:mem:test","sa","");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }


    @Bean
    public TaskRepository taskRepository() {

      return new TaskRepository() {

          private Map<Integer, Task> tasks = new HashMap<>();


          public List<Task> findAll() {
              return new ArrayList<>(tasks.values());
          }


          public Page<Task> findAll(Pageable pageable) {
              return null;
          }


          public Optional<Task> findById(Integer id) {
              return Optional.ofNullable(tasks.get(id));
          }


          public void deleteInBatch(Iterable<Task> var1) {

          }


          public boolean existsById(Integer id) {
              return false;
          }


          public boolean existsByDoneIsFalseAndGroup_Id(Integer groupId) {
              return false;
          }


          public Task save(Task entity) {
              return tasks.put(tasks.size()+1, entity);
          }
      };
    }

}
