package edu.iastate.goalfriend.exceptions;

public class TokenNotAvailableException extends Exception {
    private int errorCode;
    private String errorMessage;

    public TokenNotAvailableException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
