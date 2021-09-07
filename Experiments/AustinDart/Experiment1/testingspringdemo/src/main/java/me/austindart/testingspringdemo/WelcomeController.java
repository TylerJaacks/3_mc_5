package me.austindart.testingspringdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "I am Austin Dart. I am trying Springboot.";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "I am Austin Dart. I am trying Springboot. Your name is " + name;
    }
}
