package com.udemyspringappdemo.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class InfoController {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${my.prop}")
    private String myProp;

    @GetMapping("/info/url")
    String url() {
        return url;
    }
    @GetMapping("/info/myProp")
    String myProp() {
        return myProp;
    }
}
