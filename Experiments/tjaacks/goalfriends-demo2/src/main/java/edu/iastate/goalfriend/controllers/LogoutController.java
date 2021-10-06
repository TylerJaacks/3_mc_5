package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.reponses.ErrorResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.SuccessResponse;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import edu.iastate.goalfriend.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/logout")
    public IResponse Login(@RequestHeader("email") String email, @RequestHeader("token") String token) {
        if (email == null || token == null || email.isEmpty() || token.isEmpty()) {
            return new ErrorResponse(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were given.");
        }

        User user = userRepository.findByEmail(email);

        if (user != null) {
            Token userToken = user.getToken();

            if (userToken != null) {
                if (TokenUtils.isTokenValid(userToken, token)) {
                    if (user.getIsLoggedIn() == 1) {
                        user.setIsLoggedIn(false);

                        // T0D0: Not deleting token.
                        tokenRepository.delete(user.getToken());
                        tokenRepository.save(user.getToken());

                        user.setToken(null);
                        userRepository.save(user);

                        return new SuccessResponse();
                    } else {
                        tokenRepository.delete(userToken);
                        tokenRepository.save(userToken);

                        return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_OUT, "User is already logged out.");
                    }
                } else {
                    tokenRepository.delete(userToken);
                    tokenRepository.save(userToken);

                    return new ErrorResponse(ErrorConstants.ERROR_CODE_TOKEN_EXPIRED, "Token has expired.");
                }
            }
            else {
                return new ErrorResponse(ErrorConstants.ERROR_CODE_TOKEN_NOT_AVAILABLE, "No Token Available!");
            }
        }

        if (user != null && user.getIsLoggedIn() == 1) {

        } else if (user != null && user.getIsLoggedIn() == 0) {
            return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_OUT, "User is already logged out.");
        }

        return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "User does not exist.");
    }
}
