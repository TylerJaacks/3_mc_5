package com.example.goalfriends;

import static org.junit.Assert.*;

import com.example.goalfriends.validityutils.ValidPassword;

import org.junit.Test;

public class PasswordTest {
    @Test
    public void passwordValid_CorrectPassword_ReturnsTrue(){
        assertTrue(ValidPassword.isValidPassword("Cyclones1$"));
    }
    @Test
    public void passwordValid_NoSymbol_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword("Cyclones1"));
    }
    @Test
    public void passwordValid_NoUpperCase_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword("cyclones1$"));
    }
    @Test
    public void passwordValid_NoDigit_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword("Cyclones$"));
    }
    @Test
    public void passwordValid_NotBoundMin_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword("Cy$1"));
    }
    @Test
    public void passwordValid_NotBoundMax_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword("Cyclones$1Cyclones$1Cyclones$1"));
    }
    @Test
    public void passwordValid_InvalidChar_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword("a234567890"));
    }
    @Test
    public void passwordValid_InvalidSymbol_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword("(234567890"));
    }
    @Test
    public void passwordValid_EmptyPhone_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword(""));
    }
    @Test
    public void passwordValid_NullPhone_ReturnsFalse() {
        assertFalse(ValidPassword.isValidPassword(null));
    }
}
