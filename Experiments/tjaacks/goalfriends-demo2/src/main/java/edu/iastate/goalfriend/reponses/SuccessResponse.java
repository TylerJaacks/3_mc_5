package edu.iastate.goalfriend.reponses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class SuccessResponse implements IResponse {
    public SuccessResponse() {

    }
}
