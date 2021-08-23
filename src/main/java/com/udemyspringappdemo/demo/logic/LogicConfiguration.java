package com.udemyspringappdemo.demo.logic;

import com.udemyspringappdemo.demo.model.ProjectRepository;
import com.udemyspringappdemo.demo.model.TaskConfigurationProperties;
import com.udemyspringappdemo.demo.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ImportResource("classpath:applicationContext.xml") dawny typ konfiguracji
public class LogicConfiguration {

    @Bean
    public ProjectService projectService(
            ProjectRepository repository,
            TaskGroupRepository taskGroupRepository,
            TaskConfigurationProperties config,TaskGroupService taskGroupService
    ) {

        return new ProjectService(repository,taskGroupRepository,config, taskGroupService);
    }



}
