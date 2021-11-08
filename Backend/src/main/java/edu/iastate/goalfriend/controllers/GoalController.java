package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.GoalCategory;
import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Friendship;
import edu.iastate.goalfriend.domainobjects.Goal;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.CoreException;
import edu.iastate.goalfriend.exceptions.InvalidGoalNameException;
import edu.iastate.goalfriend.exceptions.InvalidHeadersException;
import edu.iastate.goalfriend.reponses.GoalSearchSuccessResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GoalController extends CoreController {

    @ApiOperation(value = "Edits a Goal.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited a goal ."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "Token is expired."),
            @ApiResponse(code = 400, message = "Invalid goal name."),
    })
    @PutMapping(path = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse UpdateGoal(
            @RequestHeader("token") String token,
            @RequestParam("goalName") String goalName,
            @RequestParam("newGoalName") String newGoalName,
            @RequestParam("goalCategory") String goalCategoryStr,
            @RequestParam("goalProgress") String goalProgressStr) throws CoreException {
        if (token == null || goalName == null || token.isEmpty() || goalName.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

        Token tokenObj = tokenRepository.getByToken(token);
        User user = userRepository.findByToken(tokenObj);
        List<Goal> goals = goalRepository.getAllByGoalOwnerEquals(user);

        Goal goalToEdit = null;

        for(Goal goal : goals){
            if(goal.getGoalName().equalsIgnoreCase(newGoalName) && !goalName.equals(newGoalName)){
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

    @ApiOperation(value = "Deletes a Goal.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted a goal ."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "Token is expired."),
            @ApiResponse(code = 400, message = "Invalid goal name."),
    })
    @DeleteMapping(path = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse DeleteGoal(
            @RequestHeader("token") String token,
            @RequestParam("goalName") String goalName) throws CoreException {
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
            // TODO: Goal is not deleting from database; throws an error
            goalRepository.delete(goalToDelete);
            return new SuccessResponse();
        }
    }

    @ApiOperation(value = "Adds a Goal.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added a goal ."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "Token is expired."),
            @ApiResponse(code = 400, message = "Invalid goal name."),
    })
    @PostMapping(path = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse AddGoal(
            @RequestHeader("token") String token,
            @RequestParam("goalName") String goalName) throws CoreException {
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

    @ApiOperation(value = "Gets a Goal from the given goal name.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found the goal ."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "Token is expired."),
            @ApiResponse(code = 400, message = "Invalid goal name."),
    })
    @GetMapping(path = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse GetGoal(
            @RequestHeader("token") String token,
            @RequestParam("goalName") String goalName) throws CoreException {
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

    @ApiOperation(value = "Gets all goals belonging to a user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found the goals of the user ."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "Token is expired."),
            @ApiResponse(code = 400, message = "Invalid goal name."),
    })
    @GetMapping(path = "/goal/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map GetAllGoal(@RequestHeader("token") String token) throws CoreException {
        if (token == null || token.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
        Token tokenObj = tokenRepository.getByToken(token);
        User user = userRepository.findByToken(tokenObj);
        List<Goal> goals = goalRepository.getAllByGoalOwnerEquals(user);

        Map<String, String> goalsMap = new HashMap<>();

        int i = 1;

        for (Goal goal : goalRepository.getAllByGoalOwnerEquals(user)) {
            goalsMap.put("gaol" + i, goal.getGoalName());
            i += 1;
        }

        return goalsMap;
    }
}
