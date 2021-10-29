package com.example.goalfriends;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmailTest {
    @Test
    public void emailValid_CorrectEmailFormat_ReturnsTrue(){
        assertTrue(ValidEmail.isValidEmail("name@email.com"));
    }
    @Test
    public void emailValid_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(ValidEmail.isValidEmail("name@email.co.uk"));
    }
    @Test
    public void emailValid_InvalidEmailCom_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail("name@email"));
    }
    @Test
    public void emailValid_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail("name@email..com"));
    }
    @Test
    public void emailValid_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail("@email.com"));
    }
    @Test
    public void emailValid_EmptyInsert_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail(""));
    }
    @Test
    public void emailValid_NullEmail_ReturnsFalse() {
        assertFalse(ValidEmail.isValidEmail(null));
    }
}
