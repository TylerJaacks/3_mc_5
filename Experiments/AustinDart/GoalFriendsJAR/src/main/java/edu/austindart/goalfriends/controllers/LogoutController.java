package edu.austindart.goalfriends.controllers;

import edu.austindart.goalfriends.domainobjects.User;
import edu.austindart.goalfriends.repositories.UserRepository;
import edu.austindart.goalfriends.responses.ErrorResponse;
import edu.austindart.goalfriends.responses.IResponse;
import edu.austindart.goalfriends.responses.SuccessResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    final UserRepository userRepository;

    public LogoutController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/logout")
    public IResponse Logout(@RequestHeader("email") String email, @RequestHeader("token") String token){
        // TODO: Check if token is valid

        User user = userRepository.findByEmail(email);

        if(user != null && user.getEmail().equals(email)){
            user.setLoggedIn(false);
            userRepository.save(user);
            return new SuccessResponse();
        }

        return new ErrorResponse(3, "Failed to log out");

    }
}
