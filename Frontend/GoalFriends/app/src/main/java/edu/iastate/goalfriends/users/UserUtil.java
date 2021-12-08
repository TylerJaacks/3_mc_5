package edu.iastate.goalfriends.users;


import java.util.ArrayList;

import edu.iastate.goalfriends.goals.Goal;

public final class UserUtil {
    private UserUtil() {
    }

    public static String DisplayUsers(User user) {
        return user.getName();
    }

    public static ArrayList<User> mUsers(int amount) {
        ArrayList<User> arr = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            arr.add(new User("User" + (i + 1)));
        }
        return arr;
    }
}

