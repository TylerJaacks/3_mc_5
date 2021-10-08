package edu.iastate.goalfriend.exceptions;

public class InvalidHeadersException extends Exception {
    private int errorCode;
    private String errorMessage;

    public InvalidHeadersException(int errorCode, String errorMessage) {
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
