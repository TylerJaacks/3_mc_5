package edu.iastate.goalfriend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

public class UserControllerTests extends ControllerTests {
    @Test
    public void findByEmail() {

    }

    @Override
    @BeforeEach
    public void testSetup() {
        createTestUser1();
        loginTestUser1();

        createTestUser2();
        loginTestUser2();
    }

    @Override
    @AfterEach
    public void testTeardown() {
        logoutTestUser1();
        deleteTestUser1();

        logoutTestUser2();
        deleteTestUser2();
    }
}
