package com.example.goalfriends;

import java.util.ArrayList;

public class Goal {

    private String name;
    private String category;
    private String description;
    private int progress;

    public Goal() {
        this.name = "Default";
        this.category = "None";
        this.description = "None";
        this.progress = 0;
    }

    public Goal(String name) {
        this();
        this.name = name;
    }

    public Goal(String name, String category, String description, int progress) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.progress = progress;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() {
        return this.category;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setCategory(String cat) {
        this.category = cat;
    }

    public void setProgress(int prog) {
        this.progress = prog;
    }

    public static ArrayList<Goal> populateGoal(int amount) {
        ArrayList<Goal> arr = new ArrayList<Goal>(amount);
        for (int i = 0; i < amount; i++) {
            arr.add(new Goal("Goal " + (i + 1)));
        }

        return arr;
    }

    public String displayGoal() {
        return "Name: " + this.name + " Description : " + this.description + " Category: " + this.category + " Progress: " + this.progress;
    }
}
