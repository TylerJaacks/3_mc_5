package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.FriendshipType;
import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Friendship;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.*;
import edu.iastate.goalfriend.reponses.FriendshipSuccessResponse;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.SuccessResponse;
import edu.iastate.goalfriend.utils.TokenUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FriendshipController extends CoreController {
    private void checkForValidHeaders(String token, String otherUsername) throws InvalidHeadersException {
        if (token == null || otherUsername == null || token.isEmpty() || otherUsername.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
    }

    @ApiOperation(value = "Creates a friendship between two users.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a friendship between two people."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "Token is expired."),
            @ApiResponse(code = 400, message = "Friendship already exists."),
            @ApiResponse(code = 400, message = "User does not exist.")
    })
    @PostMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse addFriendship(@RequestHeader String token, @RequestParam String otherUsername) throws CoreException {
        checkForValidHeaders(token, otherUsername);

        Token userToken = tokenRepository.getByToken(token);

        if (!TokenUtils.isTokenValid(userToken, token)) {
            throw new TokenHasExpiredException(ErrorConstants.ERROR_CODE_TOKEN_EXPIRED, "Token has expired.");
        }

        User user = userRepository.findByToken_Token(token);

        if (user == null) {
            throw new UserDoesNotExistException(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "This user doesn't exist.");
        }

        User otherUser = userRepository.findByUsername(otherUsername);

        if (otherUser == null) {
            throw new UserDoesNotExistException(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "This user doesn't exist.");
        }

        List<Friendship> usersFriends = friendshipRepository.getAllByUsername1(user.getUsername());

        for (Friendship friendship : usersFriends) {
            if (friendship.getUsername2().equals(otherUsername))
                throw new FriendshipAlreadyExistException(ErrorConstants.ERROR_CODE_FRIENDSHIP_ALREADY_EXISTS, "You are already friends with this user.");
        }

        Friendship userFriendship1 = new Friendship();
        Friendship userFriendship2 = new Friendship();

        userFriendship1.setUsername1(user.getUsername());
        userFriendship1.setUsername2(otherUser.getUsername());
        userFriendship1.setFriendshipType(FriendshipType.FRIENDS);

        userFriendship2.setUsername1(otherUser.getUsername());
        userFriendship2.setUsername2(user.getUsername());
        userFriendship2.setFriendshipType(FriendshipType.FRIENDS);

        friendshipRepository.save(userFriendship1);
        friendshipRepository.save(userFriendship2);

        return new SuccessResponse();
    }

    @ApiOperation(value = "Deletes a friendship between two users.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed a friendship between two people."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "Token is expired."),
            @ApiResponse(code = 400, message = "User does not exist.")
    })
    @DeleteMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse removeFriendship(@RequestHeader String token, @RequestParam String otherUsername) throws CoreException {
        checkForValidHeaders(token, otherUsername);

        Token userToken = tokenRepository.getByToken(token);

        if (!TokenUtils.isTokenValid(userToken, token)) {
            throw new TokenHasExpiredException(ErrorConstants.ERROR_CODE_TOKEN_EXPIRED, "Token has expired.");
        }

        User user = userRepository.findByToken_Token(token);

        if (user == null) {
            throw new UserDoesNotExistException(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "This user doesn't exist.");
        }

        User otherUser = userRepository.findByUsername(otherUsername);

        if (otherUser == null) {
            throw new UserDoesNotExistException(ErrorConstants.ERROR_CODE_USER_DOESNT_EXIST, "This user doesn't exist.");
        }

        List<Friendship> usersFriends = friendshipRepository.getAllByUsername1Equals(user.getUsername());

        boolean userFound1 = false;
        boolean userFound2 = false;

        for (Friendship friendship : usersFriends) {
            if (friendship.getUsername2().equals(otherUsername)) {
                friendship.setUsername1(null);
                friendship.setUsername2(null);

                friendshipRepository.delete(friendship);
                friendshipRepository.save(friendship);

                userFound1 = true;
            }
        }

        List<Friendship> otherUsersFriends = friendshipRepository.getAllByUsername1Equals(otherUser.getUsername());

        for (Friendship friendship : otherUsersFriends) {
            if (friendship.getUsername2().equals(user.getUsername())) {
                friendship.setUsername1(null);
                friendship.setUsername2(null);

                friendshipRepository.delete(friendship);
                friendshipRepository.save(friendship);

                userFound2 = true;
            }
        }

        if (userFound1 && userFound2) {
            return new SuccessResponse();
        }
        else {
            throw new FriendshipDoesNotExistException(ErrorConstants.ERROR_CODE_FRIENDSHIP_DOESNT_EXISTS, "You are already friends with this user.");
        }
    }

    // TODO: Not sure that I like this method of returning a list of friends.
    @ApiOperation(value = "Get a list of friendships.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got a list of friendships."),
            @ApiResponse(code = 400, message = "Invalid headers provided."),
            @ApiResponse(code = 400, message = "Token is expired."),
            @ApiResponse(code = 400, message = "Friendship already exists."),
    })
    @GetMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse getFriendships(@RequestHeader String token) throws CoreException {
        if (token == null || token.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

        Token userToken = tokenRepository.getByToken(token);

        if (!TokenUtils.isTokenValid(userToken, token)) {
            throw new TokenHasExpiredException(ErrorConstants.ERROR_CODE_TOKEN_EXPIRED, "Token has expired.");
        }

        User user = userRepository.findByToken_Token(token);

        List<User> userList = new ArrayList<>();

        for (Friendship friendship : friendshipRepository.getAllByUsername1Equals(user.getUsername())) {
            userList.add(userRepository.findByUsername(friendship.getUsername2()));
        }

        return new FriendshipSuccessResponse(userList);
    }
}
