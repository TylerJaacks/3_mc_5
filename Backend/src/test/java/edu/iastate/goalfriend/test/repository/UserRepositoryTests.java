package edu.iastate.goalfriend.test.repository;

import edu.iastate.goalfriend.controllers.LoginController;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.CoreException;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.LoginSuccessResponse;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import edu.iastate.goalfriend.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;

public class UserRepositoryTests {

    @Test
    public void getUserByEmailTest(){

        UserRepository userRepository = mock(UserRepository.class);

        User user = new User();
        user.setEmail("john.doe@gmail.com");
        user.setPhoneNumber("1234567890");
        user.setUsername("JohnDoe");
        user.setPassword("superSecretPassword");

        when(userRepository.findByEmail("john.doe@gmail.com")).thenReturn(user);

        User foundUser = userRepository.findByEmail("john.doe@gmail.com");

        assertEquals("john.doe@gmail.com", foundUser.getEmail());
        assertEquals("JohnDoe", foundUser.getUsername());
        assertEquals("1234567890", foundUser.getPhoneNumber());
        assertEquals("superSecretPassword", foundUser.getPassword());
    }

    @Test
    public void getUserByUsernameTest(){

        UserRepository userRepository = mock(UserRepository.class);

        User user = new User();
        user.setEmail("jane.doe@gmail.com");
        user.setPhoneNumber("1234567899");
        user.setUsername("JaneDoe");
        user.setPassword("superSecretPassword2");

        when(userRepository.findByUsername("JaneDoe")).thenReturn(user);

        User foundUser = userRepository.findByUsername("JaneDoe");

        assertEquals("jane.doe@gmail.com", foundUser.getEmail());
        assertEquals("JaneDoe", foundUser.getUsername());
        assertEquals("1234567899", foundUser.getPhoneNumber());
        assertEquals("superSecretPassword2", foundUser.getPassword());
    }

    @Test
    public void loginTest() throws CoreException {
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        LoginController loginController = mock(LoginController.class);
        loginController.tokenRepository = tokenRepository;
        loginController.userRepository = userRepository;

        User user = new User();
        user.setEmail("jane.doe@gmail.com");
        user.setPhoneNumber("1234567899");
        user.setUsername("JaneDoe");
        user.setPassword("superSecretPassword2");

        String tokenString = TokenUtils.tokenGenerator();

        Date now = new Date();
        long time = now.getTime();

        Token token = new Token(tokenString, time, TokenUtils.EXPIRATION_TIME);

        user.setToken(token);

        when(loginController.Login(user.getEmail(), user.getPassword())).thenReturn(new LoginSuccessResponse(tokenString));

        try {
            IResponse response = loginController.Login(user.getEmail(), user.getPassword());
            assertTrue(response instanceof LoginSuccessResponse);
            if(response instanceof LoginSuccessResponse){
                LoginSuccessResponse loginSuccessResponse = (LoginSuccessResponse) response;
                assertEquals(loginSuccessResponse.getToken(), user.getToken().getToken());
            }

        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
