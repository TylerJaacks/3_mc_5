package edu.iastate.goalfriend.utils;

import edu.iastate.goalfriend.domainobjects.Token;

import java.util.Date;

public class TokenUtils {
    public static boolean isTokenValid(Token token, String tokenStr) {
        Long currentTime = (new Date()).getTime();

        Long expirationTime = token.getCreationDate() + token.getExpirationTime();

        if (!token.getToken().equals(tokenStr)) {
            return false;
        }

        if (expirationTime < currentTime) {
            return false;
        }

        return true;
    }
}
