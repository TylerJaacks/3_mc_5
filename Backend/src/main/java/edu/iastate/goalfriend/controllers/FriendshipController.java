package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.exceptions.InvalidHeadersException;
import edu.iastate.goalfriend.reponses.IResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class FriendshipController {
    @PostMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse addFriend(@RequestHeader String token, @RequestHeader String otherUsername) throws InvalidHeadersException {
        if (token == null || otherUsername == null || token.isEmpty() || otherUsername.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
    }

    @DeleteMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse removeFriend(@RequestHeader String token, @RequestHeader String otherUsername) throws InvalidHeadersException {
        if (token == null || otherUsername == null || token.isEmpty() || otherUsername.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
    }
}
