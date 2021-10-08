package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.TokenNotAvailableException;
import edu.iastate.goalfriend.exceptions.UserDoesNotExistException;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.UsersSuccessResponse;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import edu.iastate.goalfriend.utils.TokenUtils;
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
    public IResponse usersController(@RequestHeader String token, @RequestParam String email) throws Exception {

        Token tokenObj = tokenRepository.getByToken(token);

        boolean tokenIsValid = TokenUtils.isTokenValid(tokenObj, token);

        boolean emailIsValid = !email.isEmpty() && email != null;

        if (tokenIsValid) {

            if (emailIsValid) {
                User user = userRepository.findByEmail(email);
                return new UsersSuccessResponse(user);
            } else {
                throw new UserDoesNotExistException(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "Could not find a user with that information.");
            }

        } else {
            throw new TokenNotAvailableException(ErrorConstants.ERROR_CODE_TOKEN_NOT_AVAILABLE, "Invalid tokenObj.");
        }
    }
}
