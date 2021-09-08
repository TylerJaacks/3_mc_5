package edu.tjaacks.springlogindemo.controllers;

import edu.tjaacks.springlogindemo.domainobjects.User;
import edu.tjaacks.springlogindemo.reponses.ErrorResponse;
import edu.tjaacks.springlogindemo.reponses.IResponse;
import edu.tjaacks.springlogindemo.reponses.LoginResponse;
import edu.tjaacks.springlogindemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public IResponse login(@RequestHeader("email") String email, @RequestHeader("password") String password) {
        IResponse response = null;

        Iterable<User> userList = userRepository.findAll();

        for (User user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                response = new LoginResponse("123456789", "9999999");
            }
        }

        for (User user : userList) {
            if (user.getEmail().equals(email) && !user.getPassword().equals(password)) {
                response = new ErrorResponse(100,"INCORRECT_PASSWORD");
            }
        }

        return new ErrorResponse(200, "USER_NOT_FOUND");
    }
}
