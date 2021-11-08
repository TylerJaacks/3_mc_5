package com.example.goalfriends;

import java.util.ArrayList;

/**
 * Utlity class for Goal.java.
 *
 * @Author Elijah Brungardt
 */
public final class GoalUtil {
    private GoalUtil() { }

    /**
     * Displays the name, description, category
     * and progress of a goal as a single String.
     * @param goal Goal that you want to be displayed
     * @return A String that displays the name, description, category and progress of a goal
     */
    public static String DisplayGoal(Goal goal) {
        return "Name: " + goal.getName() + " Description: " + goal.getDescription() + " Category: " + goal.getCategory() + " Progress: " + goal.getProgress();
    }

    /**
     * Creates an ArrayList of empty goals.
     * @param amount Int being the number of Goals the arrayList will have
     * @return An ArrayList of a set amount of empty goals
     */
    public static ArrayList<Goal> populateGoals(int amount) {
        ArrayList<Goal> arr = new ArrayList<Goal>(amount);
        for (int i = 0; i < amount; i++) {
            arr.add(new Goal("Goal " + (i + 1)));
        }

        return arr;
    }

    /**
     * Checks whether or not a given goal is empty or not.
     * Returns true if goal is null or default, returns false otherwise.
     * @param goal A Goal that is to be checked if empty
     * @return True if the given goal is empty, false otherwise.
     */
    public static boolean isGoalEmpty(Goal goal) {
        if (goal.equals(null)) {
            return true;
        }
        else {
            if (goal.getName().equals("Default") && goal.getProgress() == 0 && goal.getCategory().equals("None") && goal.getDescription().equals("None")) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * Checks to see if two given goals are truly equivalent.
     * Returns true if the two goals are equal, false if they are not.
     * @param goal1 First goal to be checked for equivalence with second goal
     * @param goal2 Second goal to be checked for equivalence with first goal
     * @return True if the two given goals are equal, false if they are not equal.
     */
    public static boolean isGoalsEqual(Goal goal1, Goal goal2) {
        if (goal1 == null || goal2 == null) {
            return false;
        }
        else if (goal1.getClass() != goal2.getClass()) {
            return false;
        }
        else if (goal1.getName().equals(goal2.getName()) && goal1.getDescription().equals(goal2.getDescription()) && goal1.getCategory().equals(goal2.getCategory()) && goal1.getProgress() == goal2.getProgress()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the goal's current progress a decimal between 0 and 1
     * @param goal Goal that has progress you want as a decimal
     * @return Double value that is between 0 and 1.
     */
    public static double getGoalProgAsDecimal(Goal goal) {
        return goal.getProgress() / 100.00;
    }
}
