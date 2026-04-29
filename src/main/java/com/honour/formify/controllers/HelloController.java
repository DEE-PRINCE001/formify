package com.honour.formify.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController{

    @Value("${SECRET_KEY}")
    private String secretKey;

    @GetMapping("/hello")
    public String sayHello(){
        return ("Hello, World! The secret key is: " + secretKey);
    }
}