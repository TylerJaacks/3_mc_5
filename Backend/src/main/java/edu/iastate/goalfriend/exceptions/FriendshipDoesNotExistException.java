package edu.iastate.goalfriend.exceptions;

public class FriendshipDoesNotExistException extends Throwable {
    private int errorCode;
    private String errorMessage;

    public FriendshipDoesNotExistException(int errorCode, String errorMessage) {
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
