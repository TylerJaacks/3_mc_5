package edu.iastate.goalfriend.test.repository;

import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;

public class UserRepositoryTests {

    @Test
    public void getUserTest(){

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
}
