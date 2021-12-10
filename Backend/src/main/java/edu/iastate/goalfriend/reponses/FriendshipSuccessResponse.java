package edu.iastate.goalfriend.reponses;

import edu.iastate.goalfriend.domainobjects.User;

import java.util.List;

public class FriendshipSuccessResponse implements IResponse {
    private List<User> friends;

    public FriendshipSuccessResponse(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
