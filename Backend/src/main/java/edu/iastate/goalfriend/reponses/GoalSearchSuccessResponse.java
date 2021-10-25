package edu.iastate.goalfriend.reponses;

import edu.iastate.goalfriend.domainobjects.Goal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class GoalSearchSuccessResponse implements IResponse {
    private Goal goal;

    public GoalSearchSuccessResponse(Goal goal) {
        this.goal = goal;
    }
}
