package me.austindart.testingspringdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    // This is based off of the tutorial class. I made a few small changes.

    @GetMapping("/")
    public String message() {
        return "I am Austin Dart. I am trying Springboot.";
    }

    @GetMapping("/{name}")
    public String message(@PathVariable String name) {
        return "I am Austin Dart. I am trying Springboot. Your name is " + name;
    }
}
