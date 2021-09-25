package edu.iastate.threemcfive.goalfriend.controllers;

import edu.iastate.threemcfive.goalfriend.domainobjects.User;
import edu.iastate.threemcfive.goalfriend.reponses.ErrorResponse;
import edu.iastate.threemcfive.goalfriend.reponses.IResponse;
import edu.iastate.threemcfive.goalfriend.reponses.SuccessResponse;
import edu.iastate.threemcfive.goalfriend.repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    final UserRepository userRepository;

    public LogoutController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/logout")
    public IResponse Login(@RequestHeader("email") String email, @RequestHeader("token") String token) {
        // T0D0: Check if the token is valid i.e. if it isn't expired and check that the token belongs to that email.
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setIsLoggedIn(false);

            userRepository.save(user);

            return new SuccessResponse();
        }

        return new ErrorResponse(3, "Failed to logout.");
    }
}
