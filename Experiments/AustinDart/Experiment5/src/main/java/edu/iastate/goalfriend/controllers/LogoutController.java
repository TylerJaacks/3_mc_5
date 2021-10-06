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
    public IResponse Login(@RequestHeader("email") String email, @RequestHeader("token") String tokenString) {
        if (email == null || tokenString == null || email.isEmpty() || tokenString.isEmpty()) {
            return new ErrorResponse(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were given.");
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "User does not exist.");
        }

        Token token = tokenRepository.findByTokenString(tokenString);
        if(token == null){
            return new ErrorResponse(ErrorConstants.ERROR_CODE_TOKEN_EXPIRED, "Token does not exist");
        }

        if(token.getUserId() != user.getId()){
            return new ErrorResponse(ErrorConstants.ERROR_CODE_TOKEN_EXPIRED, "Token does not match");
        }

        tokenRepository.delete(token);
        user.setIsLoggedIn(false);
        userRepository.save(user);
        return new SuccessResponse();

    }
}
