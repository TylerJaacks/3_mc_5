package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.reponses.ErrorResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.LoginSuccessResponse;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * T0D0: There is a fucking foreign key issue between Token and users for now run SET FOREIGN_KEY_CHECKS=0; before creating and delete tokens and users.
 */
@RestController
public class LoginController {
    private static final long EXPIRATION_TIME = 3600000L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("/login")
    public IResponse Login(@RequestHeader("email") String email, @RequestHeader("password") String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return new ErrorResponse(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were given.");
        }

        Iterable<User> userList = userRepository.findAll();

        for (User user : userList) {
            if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)
                    && user.getIsLoggedIn() == 0) {
                user.setIsLoggedIn(1);

                String tokenString = UUID.randomUUID().toString();

                Token token = new Token(user, tokenString,  (new Date()).getTime(), EXPIRATION_TIME);

                this.userRepository.save(user);
                this.tokenRepository.save(token);

                return new LoginSuccessResponse(tokenString, EXPIRATION_TIME);
            }
            else if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)
                    && user.getIsLoggedIn() != 0) {
                return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_IN, "User Already Logged In.");
            }
            else if (user.getEmail().equals(email) && !user.getPassword().equals(password)) {
                return new ErrorResponse(ErrorConstants.ERROR_CODE_WRONG_PASSWORD, "Invalid Password.");
            }
        }

        return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "User doesn't exist.");
    }
}
