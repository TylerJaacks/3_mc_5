package edu.iastate.threemcfive.goalfriend.utils;

import edu.iastate.threemcfive.goalfriend.domainobjects.Token;

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
