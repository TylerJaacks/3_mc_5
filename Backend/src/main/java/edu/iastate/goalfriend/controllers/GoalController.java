package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.GoalCategory;
import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Goal;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.InvalidGoalNameException;
import edu.iastate.goalfriend.exceptions.InvalidHeadersException;
import edu.iastate.goalfriend.reponses.GoalSearchSuccessResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.SuccessResponse;
import edu.iastate.goalfriend.repositories.GoalRepository;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse UpdateGoal(
            @RequestHeader("token") String token,
            @RequestParam("goalName") String goalName,
            @RequestParam("newGoalName") String newGoalName,
            @RequestParam("goalCategory") String goalCategoryStr,
            @RequestParam("goalProgress") String goalProgressStr) throws InvalidHeadersException, InvalidGoalNameException {
        if (token == null || goalName == null || token.isEmpty() || goalName.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

        Token tokenObj = tokenRepository.getByToken(token);
        User user = userRepository.findByToken(tokenObj);
        List<Goal> goals = goalRepository.getAllByGoalOwnerEquals(user);

        Goal goalToEdit = null;

        for(Goal goal : goals){
            if(goal.getGoalName().equalsIgnoreCase(newGoalName)){
                throw new InvalidGoalNameException(ErrorConstants.ERROR_CODE_INVALID_GOAL_NAME, "There is already a goal called " + newGoalName);
            }
            if(goal.getGoalName().equalsIgnoreCase(goalName)){
                goalToEdit = goal;
            }
        }

        if(goalToEdit == null){
            throw new InvalidGoalNameException(ErrorConstants.ERROR_CODE_INVALID_GOAL_NAME, goalName + " is not the name of an existing goal!");
        }else{
            goalToEdit.setGoalName(newGoalName);
            GoalCategory goalCategory = GoalCategory.valueOf(goalCategoryStr);
            goalToEdit.setGoalCategory(goalCategory);
            float goalProgress = Float.valueOf(goalProgressStr);
            goalToEdit.setGoalProgress(goalProgress);
            goalRepository.save(goalToEdit);
            return new SuccessResponse();
        }
    }

    @DeleteMapping(path = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse DeleteGoal(
            @RequestHeader("token") String token,
            @RequestParam("goalName") String goalName) throws InvalidHeadersException, InvalidGoalNameException {
        if (token == null || goalName == null || token.isEmpty() || goalName.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
        Token tokenObj = tokenRepository.getByToken(token);
        User user = userRepository.findByToken(tokenObj);
        List<Goal> goals = goalRepository.getAllByGoalOwnerEquals(user);

        Goal goalToDelete = null;

        for(Goal goal : goals){
            if(goal.getGoalName().equalsIgnoreCase(goalName)){
                goalToDelete = goal;
            }
        }

        if(goalToDelete == null){
            throw new InvalidGoalNameException(ErrorConstants.ERROR_CODE_INVALID_GOAL_NAME, goalName + " is not the name of an existing goal!");
        }else{
            goalRepository.delete(goalToDelete);
            return new SuccessResponse();
        }
    }

    @PostMapping(path = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse AddGoal(
            @RequestHeader("token") String token,
            @RequestParam("goalName") String goalName) throws InvalidHeadersException, InvalidGoalNameException {
        if (token == null || goalName == null || token.isEmpty() || goalName.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
        Token tokenObj = tokenRepository.getByToken(token);
        User user = userRepository.findByToken(tokenObj);
        List<Goal> goals = goalRepository.getAllByGoalOwnerEquals(user);

        for(Goal goal : goals){
            if(goal.getGoalName().equalsIgnoreCase(goalName)){
                throw new InvalidGoalNameException(ErrorConstants.ERROR_CODE_INVALID_GOAL_NAME, "There is already a goal called " + goalName);
            }
        }

        Goal goal = new Goal(goalName, user, GoalCategory.NOT_SET, 0f);

        goalRepository.save(goal);

        return new SuccessResponse();
    }

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
