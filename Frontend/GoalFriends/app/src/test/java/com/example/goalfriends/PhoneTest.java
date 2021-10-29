package com.example.goalfriends;

import org.junit.Test;
import static org.junit.Assert.*;

public class PhoneTest {
    @Test
    public void phoneValid_CorrectPhoneNoSpace_ReturnsTrue(){
        assertTrue(ValidEmail.isValidEmail("1234567890"));
    }
    @Test
    public void phoneValid_CorrectPhoneSpace_ReturnsTrue() {
        assertTrue(ValidEmail.isValidEmail("123 456 7890"));
    }
    @Test
    public void phoneValid_CorrectPhoneHyphen_ReturnsTrue() {
        assertTrue(ValidEmail.isValidEmail("123-456-7890"));
    }
    @Test
    public void phoneValid_CorrectPhoneDot_ReturnsTrue() {
        assertTrue(ValidEmail.isValidEmail("123.456.7890"));
    }
    @Test
    public void phoneValid_ExtraDigit_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail("12345678901"));
    }
    @Test
    public void phoneValid_MissingDigit_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail("234567890"));
    }
    @Test
    public void phoneValid_InvalidChar_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail("a234567890"));
    }
    @Test
    public void phoneValid_InvalidSymbol_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail("(234567890"));
    }
    @Test
    public void phoneValid_EmptyPhone_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail(""));
    }
    @Test
    public void phoneValid_NullPhone_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail(null));
    }
}