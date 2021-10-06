package edu.iastate.goalfriend.reponses;

import edu.iastate.goalfriend.domainobjects.User;

public class UsersSuccessResponse extends SuccessResponse{

    private User user;

    public UsersSuccessResponse(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

}
