package edu.iastate.goalfriend.reponses;

import edu.iastate.goalfriend.domainobjects.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.ACCEPTED)
public class AllUsersSuccessResponse extends SuccessResponse{
    List<User> userList = new ArrayList<>();

    public AllUsersSuccessResponse(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
