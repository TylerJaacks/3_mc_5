package edu.iastate.goalfriends.users;


import java.util.ArrayList;

import edu.iastate.goalfriends.goals.Goal;

public final class UserUtil {
    private UserUtil() {
    }

    public static String DisplayUsers(User user) {
        return "Name: " + user.getName() + " \nNumber of Friends: " + user.getFriends() + " \nNumber of Goals: " + user.getGoalCount();
    }

    public static boolean isUserEmpty(User user) {
        if (user.equals(null)) {
            return true;
        }
        else {
            return user.getName().equals("User") && user.getFriends() == 0 && user.getGoalCount() == 0;
        }
    }

    public static ArrayList<User> mUsers(int amount) {
        ArrayList<User> arr = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            arr.add(new User("User" + (i + 1)));
        }
        return arr;
    }
}

