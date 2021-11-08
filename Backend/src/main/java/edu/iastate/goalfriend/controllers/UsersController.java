package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.TokenNotAvailableException;
import edu.iastate.goalfriend.exceptions.UserDoesNotExistException;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.UsersSuccessResponse;
import edu.iastate.goalfriend.utils.TokenUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: Do more documentation of the API.
@RestController
public class UsersController extends CoreController {
    @ApiOperation(value = "Find a Registered User.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful found a user with given email and a valid token."),
            @ApiResponse(code = 400, message = "User doesn't exist with this email."),
            @ApiResponse(code = 400, message = "Provided token is invalid.")
    })
    @GetMapping(value = "/users", produces = "application/json")
    public IResponse usersController(@RequestHeader String token, @RequestParam String email) throws Exception {

        Token tokenObj = tokenRepository.getByToken(token);

        boolean tokenIsValid = TokenUtils.isTokenValid(tokenObj, token);

        boolean emailIsValid = !email.isEmpty();

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
