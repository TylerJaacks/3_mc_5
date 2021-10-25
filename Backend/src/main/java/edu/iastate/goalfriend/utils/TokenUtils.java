package edu.iastate.goalfriend.utils;

import edu.iastate.goalfriend.domainobjects.Token;

import java.security.SecureRandom;
import java.util.Date;

public class TokenUtils {
    private final static String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static int SECURE_TOKEN_LENGTH = 32;

    public static final long EXPIRATION_TIME = 3600000L;

    public boolean nonStaticIsTokenValid(Token token, String tokenStr){
        Long currentTime = (new Date()).getTime();
        Long expirationTime = token.getCreationDate() + token.getExpirationTime();

        if (!token.getToken().equals(tokenStr)) return false;
        if (expirationTime < currentTime) return false;

        return true;
    }

    public static boolean isTokenValid(Token token, String tokenStr) {
        Long currentTime = (new Date()).getTime();
        Long expirationTime = token.getCreationDate() + token.getExpirationTime();

        if (!token.getToken().equals(tokenStr)) return false;
        if (expirationTime < currentTime) return false;

        return true;
    }

    public static String tokenGenerator() {
        return nextToken();
    }

    private static String nextToken() {
        final SecureRandom random = new SecureRandom();

        final char[] symbols = CHARACTERS.toCharArray();

        final char[] buffer = new char[SECURE_TOKEN_LENGTH];

        for (int index = 0; index < buffer.length; ++index)
            buffer[index] = symbols[random.nextInt(symbols.length)];

        return new String(buffer);
    }
}
