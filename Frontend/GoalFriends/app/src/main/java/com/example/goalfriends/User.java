package com.example.goalfriends;

public class User {
    private String name;
    private int friends;
    private int GoalCount;

    public User(){
        this.name = "User";
        this.friends = 0;
        this.GoalCount = 0;
    }

    public User(String name) {
        this();
        this.name = name;
    }

    public User(String name, int friends, int GoalCount){
        this.name = name;
        this.friends = friends;
        this.GoalCount = GoalCount;
    }

    public String getName() { return this.name; }

    public void setName(String mName) {
        this.name = mName;
    }

    public int getFriends() {
        return this.friends;
    }

    public void setFriends(int numFriends) {
        this.friends = numFriends;
    }

    public int getGoalCount() {
        return this.GoalCount;
    }

    public void setGoalCount(int mGoalCount) {
        this.GoalCount = mGoalCount;
    }
}
