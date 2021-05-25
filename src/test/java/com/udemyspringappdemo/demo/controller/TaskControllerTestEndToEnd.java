package com.udemyspringappdemo.demo.controller;

import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTestEndToEnd {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;  //odpytywanie innych uslug

    @Autowired
    TaskRepository repo;

    @Test
    void httpGet_returnsAllTheTasks() {
        //given
       // repo.save(new Task("foo", LocalDateTime.now()));
      //  repo.save(new Task("bar", LocalDateTime.now()));
       // when
      Task[] result =   restTemplate.getForObject("http://localhost:"+port+"/tasks",Task[].class);
        assertThat(result).hasSize(9);
    }

}