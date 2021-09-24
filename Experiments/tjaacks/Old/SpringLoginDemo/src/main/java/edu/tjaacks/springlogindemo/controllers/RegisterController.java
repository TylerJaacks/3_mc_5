package edu.tjaacks.springlogindemo.controllers;

import edu.tjaacks.springlogindemo.domainobjects.LoginToken;
import edu.tjaacks.springlogindemo.domainobjects.User;
import edu.tjaacks.springlogindemo.repositories.LoginTokenRepository;
import edu.tjaacks.springlogindemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginTokenRepository loginTokenRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestHeader(name = "email") String email,
                                                  @RequestHeader("password") String password) {
        Iterable<User> userList = userRepository.findAll();
        Iterable<LoginToken> loginTokens = loginTokenRepository.findAll();

        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return ResponseEntity.badRequest().body("User Already Exists!");
            }
        }
        userRepository.save(new User(email, password));

        return ResponseEntity.ok().body("User Registered");
    }
}
