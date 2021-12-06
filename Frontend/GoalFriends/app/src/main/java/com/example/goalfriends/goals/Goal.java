package com.example.goalfriends.goals;

import java.util.ArrayList;

/**
 *  The goal class is used to represent user goals and has instance variable like
 *  name, description, category and progress. Also has accessor and mutator methods
 *  for the instance variables.
 *
 * @Author Elijah Brungardt
 */
public class Goal {

    private String name;
    private String category;
    private String description;
    private int progress;

    /**
     * Default constructor for goal
     */
    public Goal() {
        this.name = "Default";
        this.category = "None";
        this.description = "None";
        this.progress = 0;
    }

    @Override
    public String toString(){
        return name + ": " + progress + "% Finished.";
    }

    /**
     * Goal constructor that only sets the name.
     * All other instance values are the default.
     * @param name Name of the goal as a String
     */
    public Goal(String name) {
        this();
        this.name = name;
    }

    /**
     * Full constructor for goal.
     * Sets all instance variables.
     * @param name Name of the goal as a String
     * @param category Category of the goal as a String
     * @param description Description of the goal as a String
     * @param progress Progress of the goal as an int between 1 and 100
     */
    public Goal(String name, String category, String description, int progress) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.progress = progress;
    }

    /**
     * Gets the name of the goal
     * @return Name of goal as String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description of the goal
     * @return Description of goal as String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the category of the goal
     * @return Category of the goal as String
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Get the progress of the goal
     * @return Progress of the goal as an Int
     */
    public int getProgress() {
        return this.progress;
    }

    /**
     * Sets the name of the goal
     * @param name String of new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the goal
     * @param desc String of the new description
     */
    public void setDescription(String desc) {
        this.description = desc;
    }

    /**
     * Sets the category of the goal
     * @param cat String of the new category
     */
    public void setCategory(String cat) {
        this.category = cat;
    }

    /**
     * Sest the progress of the goal. (1-100)
     * @param prog Int of the new progress
     */
    public void setProgress(int prog) {
        this.progress = prog;
    }

}
