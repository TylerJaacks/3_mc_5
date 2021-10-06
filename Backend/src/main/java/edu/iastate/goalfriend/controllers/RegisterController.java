package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.reponses.ErrorResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.SuccessResponse;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/register")
    @ResponseBody
    public IResponse registerController(HttpEntity<String> httpEntity) {
        Iterable<User> users = userRepository.findAll();

        JSONObject userJSONObject = new JSONObject(httpEntity.getBody());

        String email = userJSONObject.getString("email");
        String username = userJSONObject.getString("username");
        String password = userJSONObject.getString("password");

        for (User user : users) {
            if (user != null) {
                if (user.getEmail().equals(email)) {
                    return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_OUT,
                            "User already exists with that email.");
                } else if (user.getUsername().equals(username)) {
                    return new ErrorResponse(ErrorConstants.ERROR_CODE_USER_ALREADY_EXIST_WITH_USERNAME,
                            "User already exists with that username.");
                }
            }
        }

        User newUser = new User();

        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);

        userRepository.save(newUser);

        return new SuccessResponse();
    }
}