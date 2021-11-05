package com.example.goalfriends;

import java.util.ArrayList;

public final class GoalUtil {
    private GoalUtil() { }

    public static String DisplayGoal(Goal goal) {
        return "Name: " + goal.getName() + " Description: " + goal.getDescription() + " Category: " + goal.getCategory() + " Progress: " + goal.getProgress();
    }

    public static ArrayList<Goal> populateGoals(int amount) {
        ArrayList<Goal> arr = new ArrayList<Goal>(amount);
        for (int i = 0; i < amount; i++) {
            arr.add(new Goal("Goal " + (i + 1)));
        }

        return arr;
    }

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

    public static double getGoalProgAsDecimal(Goal goal) {
        return goal.getProgress() / 100.00;
    }
}
