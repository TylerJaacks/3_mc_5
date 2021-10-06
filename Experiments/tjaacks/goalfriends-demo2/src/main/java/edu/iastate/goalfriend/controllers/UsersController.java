package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.reponses.ErrorResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("/users")
    public IResponse usersController(@RequestHeader String token, @RequestParam String username, @RequestParam String email) {
        // T0D0: Check if Token is Valid.
        // T0D0: Search for the User using the user repository.
        // T0D0: Return a UsersSuccessResponse.

        return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "User doesn't exist.");
    }
}
