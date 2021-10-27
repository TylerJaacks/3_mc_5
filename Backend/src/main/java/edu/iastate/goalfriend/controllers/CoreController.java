package edu.iastate.goalfriend.controllers;

import edu.iastate.goalfriend.repositories.FriendshipRepository;
import edu.iastate.goalfriend.repositories.GoalRepository;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CoreController {
    @Autowired
    public FriendshipRepository friendshipRepository;

    @Autowired
    public GoalRepository goalRepository;

    @Autowired
    public TokenRepository tokenRepository;

    @Autowired
    public UserRepository userRepository;
}
