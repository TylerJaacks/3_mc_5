package edu.iastate.goalfriend.reponses;

import edu.iastate.goalfriend.domainobjects.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.ACCEPTED)
public class FriendshipsResponse extends SuccessResponse {
    List<User> friends;

    public FriendshipsResponse(List<User> friends) {
        this.friends = friends;
    }
}
