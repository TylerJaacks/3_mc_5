package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.reponses.ErrorResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.LoginSuccessResponse;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import edu.iastate.goalfriend.utils.TokenUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path ="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse Login(@RequestHeader("email") String email, @RequestHeader("password") String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return new ErrorResponse(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

        Iterable<User> userList = userRepository.findAll();

        for (User user : userList) {
            if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)
                    && user.getIsLoggedIn() == 0) {
                user.setIsLoggedIn(1);

                String tokenString = TokenUtils.tokenGenerator();

                Token token = new Token(tokenString, (new Date()).getTime(), TokenUtils.EXPIRATION_TIME);

                user.setToken(token);

                this.userRepository.save(user);

                JSONObject errorObject = new JSONObject();
                List<JSONObject> jsonObjectList = new ArrayList<>();

                errorObject.put("token", tokenString);

                jsonObjectList.add(errorObject);

                return new LoginSuccessResponse(tokenString);
            } else if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)
                    && user.getIsLoggedIn() != 0) {
                return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_IN, "User already logged in.");
            } else if (user.getEmail().equals(email) && !user.getPassword().equals(password)) {
                return new ErrorResponse(ErrorConstants.ERROR_CODE_WRONG_PASSWORD, "Wrong Password.");
            }
        }

        return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "User doesn't exist.");
    }
}
