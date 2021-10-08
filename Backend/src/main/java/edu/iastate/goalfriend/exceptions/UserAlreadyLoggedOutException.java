package edu.iastate.goalfriend.exceptions;

public class UserAlreadyLoggedOutException extends Exception {
    private int errorCode;
    private String errorMessage;

    public UserAlreadyLoggedOutException(int errorCode, String errorMessage) {
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
