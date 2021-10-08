package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.UserAlreadyExistsException;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.SuccessResponse;
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

    @PostMapping("/register")
    @ResponseBody
    public IResponse registerController(HttpEntity<String> httpEntity) throws Exception {
        Iterable<User> users = userRepository.findAll();

        JSONObject userJSONObject = new JSONObject(httpEntity.getBody());

        // T0D0: Do Error Checking Here.
        String email = userJSONObject.getString("email");
        String username = userJSONObject.getString("username");
        String password = userJSONObject.getString("password");
        String phoneNumber = userJSONObject.getString("phonenumber");

        for (User user : users) {
            if (user != null) {
                if (user.getEmail().equals(email)) {
                    throw new UserAlreadyExistsException(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_OUT, "User already exists with that email.");
                } else if (user.getUsername().equals(username)) {
                    throw new UserAlreadyExistsException(ErrorConstants.ERROR_CODE_USER_ALREADY_LOGGED_OUT, "User already exists with that username.");
                }
            }
        }

        User newUser = new User();

        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setPhoneNumber(phoneNumber);

        userRepository.save(newUser);

        return new SuccessResponse();
    }
}