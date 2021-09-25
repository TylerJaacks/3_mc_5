package edu.iastate.threemcfive.goalfriend.reponses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class LoginSuccessResponse implements IResponse {
    private String token;
    private long tokenExpiration;

    public LoginSuccessResponse(String token, long tokenExpiration) {
        this.token = token;
        this.tokenExpiration = tokenExpiration;
    }

    public String getToken() {
        return token;
    }

    public long getTokenExpiration() {
        return tokenExpiration;
    }
}
