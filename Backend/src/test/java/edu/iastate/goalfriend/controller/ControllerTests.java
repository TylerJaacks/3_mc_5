package edu.iastate.goalfriend.controller;

import edu.iastate.goalfriend.FriendshipType;
import edu.iastate.goalfriend.domainobjects.Friendship;
import edu.iastate.goalfriend.domainobjects.Goal;
import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.repositories.FriendshipRepository;
import edu.iastate.goalfriend.repositories.GoalRepository;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import edu.iastate.goalfriend.utils.TokenUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class ControllerTests {
    protected final String USERNAME1 = "test1";
    protected final String EMAIL1 = "test@test.com";
    protected final String PHONE1 = "12345678";

    protected final String USERNAME2 = "test2";
    protected final String EMAIL2 = "test2@test.com";
    protected final String PHONE2 = "87654321";

    @Autowired
    public FriendshipRepository friendshipRepository;

    @Autowired
    public GoalRepository goalRepository;

    @Autowired
    public TokenRepository tokenRepository;

    @Autowired
    public UserRepository userRepository;

    protected User user1 = new User();
    protected Token token1 = new Token();
    protected Goal goal1 = new Goal();
    protected Friendship friendship1 = new Friendship();

    protected User user2 = new User();
    protected Token token2 = new Token();
    protected Goal goal2 = new Goal();
    protected Friendship friendship2 = new Friendship();

    protected void createTestUser1() {
        user1.setUsername(USERNAME1);
        user1.setEmail(EMAIL1);
        user1.setPhoneNumber(PHONE1);

        userRepository.save(user1);
    }

    protected void deleteTestUser1() {
        userRepository.delete(user1);
        userRepository.save(user1);

        user1 = new User();
    }

    protected void loginTestUser1() {
        String tokenString = TokenUtils.tokenGenerator();

        token1 = new Token(tokenString, (new Date()).getTime(), TokenUtils.EXPIRATION_TIME);

        user1.setToken(token1);
        user1.setIsLoggedIn(true);

        tokenRepository.save(token1);
        userRepository.save(user1);

        token1 = new Token();
    }

    protected void logoutTestUser1() {
        user1.setToken(null);
        user1.setIsLoggedIn(false);

        userRepository.save(user1);
        tokenRepository.delete(token1);
        tokenRepository.save(token1);

        user1 = new User();
        token1 = new Token();
    }

    protected void createTestGoal1() {
        // TODO: Austin fill this out.
    }

    protected void editTestGoal1() {
        // TODO: Austin fill this out.
    }

    protected void deleteTestGoal1() {
        // TODO: Austin fill this out.
    }

    protected void createTestFriendship1() {
        friendship1.setUsername1(user1.getUsername());
        friendship1.setUsername2(user2.getUsername());
        friendship1.setFriendshipType(FriendshipType.FRIENDS);

        friendship2.setUsername1(user2.getUsername());
        friendship2.setUsername2(user1.getUsername());
        friendship2.setFriendshipType(FriendshipType.FRIENDS);

        friendshipRepository.save(friendship1);
        friendshipRepository.save(friendship2);
    }

    protected void editTestFriendship1() {

    }

    protected void deleteTestFriendship1() {
    }

    protected void createTestUser2() {
        user2.setUsername(USERNAME2);
        user2.setEmail(EMAIL2);
        user2.setPhoneNumber(PHONE2);

        userRepository.save(user2);
    }

    protected void deleteTestUser2() {
        userRepository.delete(user2);
        userRepository.save(user2);

        user2 = new User();
    }

    protected void loginTestUser2() {
        String tokenString = TokenUtils.tokenGenerator();

        token2 = new Token(tokenString, (new Date()).getTime(), TokenUtils.EXPIRATION_TIME);

        user2.setToken(token2);
        user2.setIsLoggedIn(true);

        tokenRepository.save(token2);
        userRepository.save(user2);

        token2 = new Token();
    }

    protected void logoutTestUser2() {
        user2.setToken(null);
        user2.setIsLoggedIn(false);

        userRepository.save(user2);
        tokenRepository.delete(token2);
        tokenRepository.save(token2);

        user2 = new User();
        token2 = new Token();
    }

    protected void createTestGoal2() {
        // TODO: Austin fill this out.
    }

    protected void editTestGoal2() {
        // TODO: Austin fill this out.
    }

    protected void deleteTestGoal2() {
        // TODO: Austin fill this out.
    }

    protected void createTestFriendship2() {
        friendship2.setUsername1(user2.getUsername());
        friendship2.setUsername2(user1.getUsername());
        friendship2.setFriendshipType(FriendshipType.FRIENDS);

        friendship1.setUsername1(user1.getUsername());
        friendship1.setUsername2(user2.getUsername());
        friendship1.setFriendshipType(FriendshipType.FRIENDS);

        friendshipRepository.save(friendship2);
        friendshipRepository.save(friendship1);
    }

    protected void editTestFriendship2() {

    }

    protected void deleteTestFriendship2() {
    }

    @BeforeEach
    public abstract void testSetup();

    @AfterEach
    public abstract void testTeardown();
}
