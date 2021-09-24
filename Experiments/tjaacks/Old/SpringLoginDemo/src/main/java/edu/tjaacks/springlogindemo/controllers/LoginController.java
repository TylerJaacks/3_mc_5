package edu.tjaacks.springlogindemo.controllers;

import edu.tjaacks.springlogindemo.domainobjects.LoginToken;
import edu.tjaacks.springlogindemo.domainobjects.User;
import edu.tjaacks.springlogindemo.reponses.ErrorResponse;
import edu.tjaacks.springlogindemo.reponses.IResponse;
import edu.tjaacks.springlogindemo.reponses.LoginResponse;
import edu.tjaacks.springlogindemo.repositories.LoginTokenRepository;
import edu.tjaacks.springlogindemo.repositories.UserRepository;
import edu.tjaacks.springlogindemo.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginTokenRepository loginTokenRepository;

    @GetMapping("/login")
    public IResponse login(@RequestHeader("email") String email, @RequestHeader("password") String password) {
        Iterable<User> userList = userRepository.findAll();
        Iterable<LoginToken> loginTokens = loginTokenRepository.findAll();

        for (User user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                // TODO: I don't know if this will work.
                // TODO: Check if the token is expired.
                if (loginTokenRepository.findLoginTokenByUser(user) == null) {
                    System.out.println("Successful Login");

                    String loginToken = TokenGenerator.generateToken(32);

                    LoginToken newLoginToken = new LoginToken();

                    newLoginToken.setToken(loginToken);
                    newLoginToken.setExpiration("3600000");

                    loginTokenRepository.save(newLoginToken);

                    return new LoginResponse(loginToken, "3600000");
                }
            }
        }

        for (User user : userList) {
            if (user.getEmail().equals(email) && !user.getPassword().equals(password)) {
                System.out.println("Successful Failed");

                return new ErrorResponse(100,"INCORRECT_PASSWORD");
            }
        }

        return new ErrorResponse(200, "USER_NOT_FOUND");
    }
}
