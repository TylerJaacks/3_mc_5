package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.constants.ErrorConstants;
import edu.iastate.goalfriend.exceptions.InvalidHeadersException;
import edu.iastate.goalfriend.reponses.IResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class FriendshipController {
    @PostMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse addFriendship(@RequestHeader String token, @RequestHeader String otherUsername) throws InvalidHeadersException {
        if (token == null || otherUsername == null || token.isEmpty() || otherUsername.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }

        // TODO: Check if the token represents a logged in
    }

    @DeleteMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse removeFriendship(@RequestHeader String token, @RequestHeader String otherUsername) throws InvalidHeadersException {
        if (token == null || otherUsername == null || token.isEmpty() || otherUsername.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
    }

    @GetMapping(path = "/friendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public IResponse getFriendships(@RequestHeader String token) throws InvalidHeadersException {
        if (token == null || token.isEmpty()) {
            throw new InvalidHeadersException(ErrorConstants.ERROR_CODE_INVALID_HEADERS, "Invalid headers were supplied.");
        }
    }
}
