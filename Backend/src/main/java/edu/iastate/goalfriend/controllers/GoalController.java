package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Goal;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.InvalidGoalNameException;
import edu.iastate.goalfriend.exceptions.InvalidHeadersException;
import edu.iastate.goalfriend.reponses.GoalSearchSuccessResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.repositories.GoalRepository;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// get, delete, post, update

@RestController
public class GoalController {
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping(path = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse GetGoal(
            @RequestHeader("token") String token,
            @RequestParam("goalName") String goalName) throws InvalidHeadersException, InvalidGoalNameException {
        if (token == null || goalName == null || token.isEmpty() || goalName.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
        Token tokenObj = tokenRepository.getByToken(token);
        User user = userRepository.findByToken(tokenObj);
        List<Goal> goals = goalRepository.getAllByGoalOwnerEquals(user);

        Goal goalToReturn = null;

        for(Goal goal : goals){
            if(goal.getGoalName().equalsIgnoreCase(goalName)){
                goalToReturn = goal;
                break;
            }
        }

        if(goalToReturn == null){

            throw new InvalidGoalNameException(ErrorConstants.ERROR_CODE_INVALID_GOAL_NAME, goalName + " is not the name of an existing goal!");
        }else{
            return new GoalSearchSuccessResponse(goalToReturn);
        }
    }
}
