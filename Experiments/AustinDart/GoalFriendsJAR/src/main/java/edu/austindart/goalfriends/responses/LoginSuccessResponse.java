package edu.austindart.goalfriends.responses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ACCEPTED)
public class LoginSuccessResponse implements IResponse{
    private String token;
    private long expirationTime;
    public LoginSuccessResponse(String token, long expirationTime){
        this.token = token;
        this.expirationTime = expirationTime;
    }

    public String getToken() {
        return token;
    }

    public long getExpirationTime(){
        return expirationTime;
    }
}
