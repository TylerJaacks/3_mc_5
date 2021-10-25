package edu.iastate.goalfriend.reponses;

import edu.iastate.goalfriend.domainobjects.Friendship;
import edu.iastate.goalfriend.domainobjects.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.ACCEPTED)
public class FriendshipsResponse extends SuccessResponse {
    List<Friendship> friendshipList;

    public FriendshipsResponse(List<Friendship> friends) {
        this.friendshipList = friends;
    }
}
