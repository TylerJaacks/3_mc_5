package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.FriendshipType;
import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.domainobjects.Friendship;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.exceptions.*;
import edu.iastate.goalfriend.reponses.IResponse;
import edu.iastate.goalfriend.reponses.SuccessResponse;
import edu.iastate.goalfriend.utils.TokenUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FriendshipController extends CoreController {
    @PostMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse addFriendship(@RequestHeader String token, @RequestParam String otherUsername) throws InvalidHeadersException, TokenHasExpiredException, UserDoesNotExistException, FriendshipAlreadyExistException {
        if (token == null || otherUsername == null || token.isEmpty() || otherUsername.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

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

        List<Friendship> usersFriends = friendshipRepository.getAllByUser1UsernameEquals(user.getUsername());

        for (Friendship friendship : usersFriends) {
            if (friendship.getUsername2().equals(otherUsername))
                throw new FriendshipAlreadyExistException(ErrorConstants.ERROR_CODE_FRIENDSHIP_ALREADY_EXISTS, "You are already friends with this user.");
        }

        Friendship userFriendship1 = new Friendship();
        Friendship userFriendship2 = new Friendship();

        userFriendship1.setUsername1(user.getUsername());
        userFriendship1.setUsername1(otherUser.getUsername());
        userFriendship1.setFriendshipType(FriendshipType.FRIENDS);

        userFriendship2.setUsername1(otherUser.getUsername());
        userFriendship2.setUsername2(user.getUsername());
        userFriendship2.setFriendshipType(FriendshipType.FRIENDS);

        friendshipRepository.save(userFriendship1);
        friendshipRepository.save(userFriendship2);

        return new SuccessResponse();
    }

    @DeleteMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse removeFriendship(@RequestHeader String token, @RequestParam String otherUsername) throws InvalidHeadersException, TokenHasExpiredException, UserDoesNotExistException, FriendshipAlreadyExistException, FriendshipDoesNotExistException {
        if (token == null || otherUsername == null || token.isEmpty() || otherUsername.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

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

        List<Friendship> usersFriends = friendshipRepository.getAllByUser1UsernameEquals(user.getUsername());

        // TODO: Fix Deleting User
        /**
         * rg.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint [null];
         * nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement\r\n\tat
         * org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:276)\r\n\tat
         */
        for (Friendship friendship : usersFriends) {
            if (friendship.getUsername2().equals(otherUsername)) {
                friendshipRepository.delete(friendship);
                friendshipRepository.save(friendship);
            }
        }

        List<Friendship> otherUsersFriends = friendshipRepository.getAllByUser1UsernameEquals(otherUser.getUsername());

        for (Friendship friendship : otherUsersFriends) {
            if (friendship.getUsername2().equals(user)) {
                friendshipRepository.delete(friendship);
                friendshipRepository.save(friendship);
            }
        }

        throw new FriendshipDoesNotExistException(ErrorConstants.ERROR_CODE_FRIENDSHIP_DOESNT_EXISTS, "You are already friends with this user.");
    }

    // TODO: Not sure that I like this method of returning a list of friends.
    @GetMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getFriendships(@RequestHeader String token) throws InvalidHeadersException, TokenHasExpiredException {
        Map<String, String> friendshipMap = new HashMap<>();

        if (token == null || token.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

        Token userToken = tokenRepository.getByToken(token);

        if (!TokenUtils.isTokenValid(userToken, token)) {
            throw new TokenHasExpiredException(ErrorConstants.ERROR_CODE_TOKEN_EXPIRED, "Token has expired.");
        }

        User user = userRepository.findByToken_Token(token);

        int i = 1;

        for (Friendship friendship : friendshipRepository.getAllByUser1UsernameEquals(user.getUsername())) {
            friendshipMap.put("friend" + i, friendship.getUsername2());
            i += 1;
        }

        return friendshipMap;
    }
}
