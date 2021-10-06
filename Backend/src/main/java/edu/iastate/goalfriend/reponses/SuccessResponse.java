package edu.iastate.goalfriend.reponses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class SuccessResponse implements IResponse {
    private String message;

    public SuccessResponse() {
        this.message = "";
    }

    public SuccessResponse(String message) {
        this.message = message;
    }
}
