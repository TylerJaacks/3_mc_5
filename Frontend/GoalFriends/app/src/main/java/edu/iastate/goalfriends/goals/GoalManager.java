package edu.iastate.goalfriends.goals;

import java.util.ArrayList;
import java.util.List;

public class GoalManager {

    private List<Goal> goalList;

    public GoalManager(){
        goalList = new ArrayList();
    }

    public List<Goal> getGoalList(){
        return goalList;
    }

    public void clearGoals(){
        goalList.clear();
    }

    public boolean addGoal(Goal goal){
        return goalList.add(goal);
    }

    public boolean removeGoal(Goal goal){
        return goalList.remove(goal);
    }

    public Goal getByName(String name){
        for(Goal goal : goalList){
            if(goal.getName().equals(name)){
                return goal;
            }
        }
        return null;
    }

}
