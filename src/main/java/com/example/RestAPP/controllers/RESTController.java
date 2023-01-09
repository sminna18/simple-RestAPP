package com.example.RestAPP.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody н каждый метод
@RequestMapping("/api")
public class RESTController {

//    @ResponseBody
    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello world!";
    }
}
