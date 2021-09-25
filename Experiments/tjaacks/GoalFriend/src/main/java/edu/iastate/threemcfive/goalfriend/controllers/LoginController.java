package edu.iastate.threemcfive.goalfriend.controllers;

import edu.iastate.threemcfive.goalfriend.domainobjects.User;
import edu.iastate.threemcfive.goalfriend.reponses.ErrorResponse;
import edu.iastate.threemcfive.goalfriend.reponses.IResponse;
import edu.iastate.threemcfive.goalfriend.reponses.LoginSuccessResponse;

import edu.iastate.threemcfive.goalfriend.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public IResponse Login(@RequestHeader("email") String email, @RequestHeader("password") String password) {
        Iterable<User> userList = userRepository.findAll();

        for (User user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                user.setIsLoggedIn(1);

                this.userRepository.save(user);

                // T0D0: Handle all the token shit here.
                return new LoginSuccessResponse("123456789", 3600000);
            } else if (user.getEmail().equals(email) && !user.getPassword().equals(password)) {
                return new ErrorResponse(1, "Invalid Password!");
            }
        }

        return new ErrorResponse(2, "User doesn't exist.");
    }
}
