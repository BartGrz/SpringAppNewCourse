package com.udemyspringappdemo.demo.logic;

import com.udemyspringappdemo.demo.model.ProjectRepository;
import com.udemyspringappdemo.demo.model.TaskConfigurationProperties;
import com.udemyspringappdemo.demo.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {


    @Bean
    public ProjectService projectService(
            ProjectRepository repository,
            TaskGroupRepository taskGroupRepository,
            TaskConfigurationProperties config
    ) {

        return new ProjectService(repository,taskGroupRepository,config);
    }

}
