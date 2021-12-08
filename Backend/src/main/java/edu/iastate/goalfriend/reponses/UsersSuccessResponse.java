package edu.iastate.goalfriend.reponses;

import edu.iastate.goalfriend.domainobjects.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class UsersSuccessResponse extends SuccessResponse{

    private String username;
    private int id;

    public UsersSuccessResponse(User user){
        this.username = user.getUsername();
        this.id = user.getId();
    }

    public String getUsername(){
        return username;
    }

    public int getId(){
        return id;
    }
}
