package com.example.goalfriends;

public class User {
    private String mName;
    private String mBio;
    private int mFollowerCount;
    private int mFollowingCount;
    private int mGoalCount;

    public User(String name, String bio, int followerCount, int followingCount, int goalCount){
        mName = name;
        mBio = bio;
        mFollowerCount = followerCount;
        mFollowingCount = followingCount;
        mGoalCount = goalCount;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmBio() {
        return mBio;
    }

    public void setmBio(String mBio) {
        this.mBio = mBio;
    }

    public int getmFollowerCount() {
        return mFollowerCount;
    }

    public void setmFollowerCount(int mFollowerCount) {
        this.mFollowerCount = mFollowerCount;
    }

    public int getmFollowingCount() {
        return mFollowingCount;
    }

    public void setmFollowingCount(int mFollowingCount) {
        this.mFollowingCount = mFollowingCount;
    }

    public int getmGoalCount() {
        return mGoalCount;
    }

    public void setmGoalCount(int mGoalCount) {
        this.mGoalCount = mGoalCount;
    }
}
