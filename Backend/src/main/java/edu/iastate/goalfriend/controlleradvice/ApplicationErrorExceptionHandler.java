package edu.iastate.goalfriend.controlleradvice;

import edu.iastate.goalfriend.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationErrorExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidHeadersException.class)
    public ResponseEntity<Map<String, String>> handleUserInvalidHeadersException(InvalidHeadersException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyLoggedInException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyLoggedInException(UserAlreadyLoggedInException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<Map<String, String>> handleUserDoesNotExistException(UserDoesNotExistException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Map<String, String>> handleWrongPasswordException(WrongPasswordException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyLoggedOutException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyLoggedOut(UserAlreadyLoggedOutException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenHasExpiredException.class)
    public ResponseEntity<Map<String, String>> handleTokenHasExpiredException(TokenHasExpiredException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotAvailableException.class)
    public ResponseEntity<Map<String, String>> handleNoTokenAvailableException(TokenNotAvailableException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FriendshipAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleNoTokenAvailableException(FriendshipAlreadyExistException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("errorCode", String.valueOf(e.getErrorCode()));
        errorResponse.put("errorMessage", String.valueOf(e.getErrorMessage()));
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
