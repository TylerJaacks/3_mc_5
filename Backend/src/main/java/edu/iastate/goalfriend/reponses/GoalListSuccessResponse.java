package edu.iastate.goalfriend.reponses;

import edu.iastate.goalfriend.domainobjects.Goal;

import java.util.List;

public class GoalListSuccessResponse implements IResponse {
    private List<Goal> goalList;

    public GoalListSuccessResponse(List<Goal> goalList) {
        this.goalList = goalList;
    }

    public List<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(List<Goal> goalList) {
        this.goalList = goalList;
    }
}
