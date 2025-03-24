package ru.joxaren.springcourse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


public class HelloController {

    @GetMapping("/hello-world")
    public String hello(){
        return "hello-world";
    }

}
