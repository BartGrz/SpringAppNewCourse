package com.udemyspringappdemo.demo.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


    @Configuration
    @ConfigurationProperties("task")
    public class TaskConfigurationProperties {

        public boolean allowMultipleTaskFromTemplate;

        public boolean isAllowMultipleTaskFromTemplate() {
        return allowMultipleTaskFromTemplate;
    }

    public void setAllowMultipleTaskFromTemplate(boolean allowMultipleTaskFromTemplate) {
        this.allowMultipleTaskFromTemplate = allowMultipleTaskFromTemplate;
    }

}
