package edu.tjaacks.springlogindemo.util;

public class TokenGenerator {
    private static final String alphaNumericString = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateToken(int size) {
        StringBuilder tokenStringBuilder = new StringBuilder();

        for (int i = 0; i < size + 1; i++) {
            int index = (int) (alphaNumericString.length() * Math.random());

            tokenStringBuilder.append(alphaNumericString.indexOf(index));
        }

        return tokenStringBuilder.toString();
    }
}
