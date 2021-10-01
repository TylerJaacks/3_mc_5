package edu.austindart.goalfriends.controllers;

import edu.austindart.goalfriends.domainobjects.User;
import edu.austindart.goalfriends.repositories.UserRepository;
import edu.austindart.goalfriends.responses.ErrorResponse;
import edu.austindart.goalfriends.responses.IResponse;
import edu.austindart.goalfriends.responses.LoginSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    final UserRepository userRepository;

    public LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public IResponse Login(@RequestHeader("email") String email, @RequestHeader("password") String password){

        Iterable<User> userList = userRepository.findAll();

        for(User user : userList){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                user.setLoggedIn(true);
                userRepository.save(user);
                return new LoginSuccessResponse("randomlyGeneratedToken", 3600000);
            }else if(user.getEmail().equals(email) && !user.getPassword().equals(password)){
                return new ErrorResponse(1, "Incorrect password!!!");
            }
        }

        return new ErrorResponse(2, "No user with email <" + email + "> found.");
    }

}
