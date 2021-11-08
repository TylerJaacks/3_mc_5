package edu.iastate.goalfriend.exceptions;

public class UserAlreadyLoggedInException extends CoreException {
    private int errorCode;
    private String errorMessage;

    public UserAlreadyLoggedInException(int errorCode, String errorMessage) {
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
