package com.example.jenkins_demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @Value("${app.message:Default Message}")
    private String appMessage;

    @GetMapping("/hello")
    public String hello() {
            return "Hello World Jenkins Pipelines UI execution - " + appMessage;
    }

    @GetMapping("/hi")
    public String hi() {
        return "Hi there! Jenkins UI update! - " + appMessage;
    }
}
