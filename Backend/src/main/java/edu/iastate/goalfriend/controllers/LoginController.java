package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.*;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.LoginSuccessResponse;
import edu.iastate.goalfriend.utils.TokenUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: Clean up this code here.
// TODO: Provide better documentation.
@RestController
public class LoginController extends CoreController {
    @ApiOperation(value = "Logs in a user and gives the users an API Token.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in a user and return a valid token."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "User already logged in."),
            @ApiResponse(code = 400, message = "Wrong password was provided."),
            @ApiResponse(code = 400, message = "User does not exist.")
    })
    @GetMapping(path ="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse Login(@RequestHeader("email") String email, @RequestHeader("password") String password) throws CoreException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

        Iterable<User> userList = userRepository.findAll();

        for (User user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                Token foundToken = user.getToken();

                if (foundToken == null) {
                    user.setIsLoggedIn(1);

                    String tokenString = TokenUtils.tokenGenerator();

                    Token token = new Token(tokenString, (new Date()).getTime(), TokenUtils.EXPIRATION_TIME);

                    user.setToken(token);

                    this.userRepository.save(user);

                    JSONObject jsonObject = new JSONObject();
                    List<JSONObject> jsonObjectList = new ArrayList<>();

                    jsonObject.put("token", tokenString);

                    jsonObjectList.add(jsonObject);

                    return new LoginSuccessResponse(tokenString);
                }

                boolean isTokenValid = TokenUtils.isTokenValid(foundToken, foundToken.getToken());

                if (isTokenValid) {
                    tokenRepository.delete(foundToken);
                    tokenRepository.save(foundToken);

                    String tokenString = TokenUtils.tokenGenerator();

                    Token token = new Token(tokenString, (new Date()).getTime(), TokenUtils.EXPIRATION_TIME);

                    user.setToken(token);

                    this.userRepository.save(user);

                    JSONObject jsonObject = new JSONObject();
                    List<JSONObject> jsonObjectList = new ArrayList<>();

                    jsonObject.put("token", tokenString);

                    jsonObjectList.add(jsonObject);

                    return new LoginSuccessResponse(tokenString);
                }

                tokenRepository.delete(foundToken);
                tokenRepository.save(foundToken);

                user.setIsLoggedIn(1);

                String tokenString = TokenUtils.tokenGenerator();

                Token token = new Token(tokenString, (new Date()).getTime(), TokenUtils.EXPIRATION_TIME);

                user.setToken(token);

                this.userRepository.save(user);

                JSONObject jsonObject = new JSONObject();
                List<JSONObject> jsonObjectList = new ArrayList<>();

                jsonObject.put("token", tokenString);

                jsonObjectList.add(jsonObject);

                return new LoginSuccessResponse(tokenString);
            } else if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)
                    && user.getIsLoggedIn() != 0) {
                throw new UserAlreadyLoggedInException(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_IN, "User already logged in.");
            } else if (user.getEmail().equals(email) && !user.getPassword().equals(password)) {
                throw new WrongPasswordException(ErrorConstants.ERROR_CODE_WRONG_PASSWORD, "Wrong Password.");
            }
        }

        throw new UserDoesNotExistException(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "User doesn't exist.");
    }
}
