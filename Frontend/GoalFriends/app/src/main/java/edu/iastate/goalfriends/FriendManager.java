package edu.iastate.goalfriends;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.goalfriends.users.User;

public class FriendManager {

    private List<User> friendList;

    public FriendManager(){
        friendList = new ArrayList();
    }

    public List<User> getFriendList(){
        return friendList;
    }

    public void clearFriends(){
        friendList.clear();
    }

    public boolean addFriend(User friend){
        return friendList.add(friend);
    }

    public boolean removeFriend(User friend){
        return friendList.remove(friend);
    }

    public User getByName(String name){
        for(User friend : friendList){
            if(friend.getName().equals(name)){
                return friend;
            }
        }
        return null;
    }
}
