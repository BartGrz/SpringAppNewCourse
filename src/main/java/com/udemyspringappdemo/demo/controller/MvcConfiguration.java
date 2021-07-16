package com.udemyspringappdemo.demo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

/**
 * allows injected interceptor to be shown in log console - injecting all implementations
 * of HandlerInterceptor via Set<HandlerInterceptor> interceptors, then each of them is added to registry
 *
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    private Set<HandlerInterceptor> interceptors;

    public MvcConfiguration(Set<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor);
    }
}
