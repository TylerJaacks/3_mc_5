package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.*;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.SuccessResponse;
import edu.iastate.goalfriend.utils.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController extends CoreController {
    @PostMapping("/logout")
    public IResponse Login(@RequestHeader("token") String token) throws Exception {
        if (token == null || token.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were given.");
        }

        User user = userRepository.findByToken_Token(token);

        if (user != null) {
            Token userToken = user.getToken();

            if (userToken != null) {
                if (TokenUtils.isTokenValid(userToken, token)) {
                    if (user.getIsLoggedIn() == 1) {
                        user.setIsLoggedIn(false);

                        tokenRepository.delete(user.getToken());
                        tokenRepository.save(user.getToken());

                        user.setToken(null);
                        userRepository.save(user);

                        return new SuccessResponse();
                    } else {
                        tokenRepository.delete(userToken);
                        tokenRepository.save(userToken);

                        throw new UserAlreadyLoggedOutException(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_OUT, "User is already logged out.");
                    }
                } else {
                    tokenRepository.delete(userToken);
                    tokenRepository.save(userToken);

                    throw new TokenHasExpiredException(ErrorConstants.ERROR_CODE_TOKEN_EXPIRED, "Token has expired.");
                }
            }
            else if (user.getIsLoggedIn() == 0) {
                throw new UserAlreadyLoggedOutException(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_OUT, "User is already logged out.");
            }
            else {
                throw new TokenNotAvailableException(ErrorConstants.ERROR_CODE_TOKEN_NOT_AVAILABLE, "No Token Available!");
            }
        }

        throw new UserDoesNotExistException(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "User does not exist.");
    }
}
