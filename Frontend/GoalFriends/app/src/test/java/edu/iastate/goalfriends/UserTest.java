package edu.iastate.goalfriends;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import androidx.test.core.app.ActivityScenario;

import edu.iastate.goalfriends.activities.OtherUserProfileActivity;
import edu.iastate.goalfriends.users.User;
import edu.iastate.goalfriends.users.UserUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    @Mock
    User userTest;

    @Test
    public void checkDisplayUser() {
        when(userTest.getName()).thenReturn("User Name");
        when(userTest.getFriends()).thenReturn(10);
        when(userTest.getGoalCount()).thenReturn(5);
        assertThat(UserUtil.DisplayUsers(userTest), is("Name: User Name \nNumber of Friends: 10 \nNumber of Goals: 5"));
    }

    @Test
    public void checkIsEmptyUser() {
        when(userTest.getName()).thenReturn("User");
        when(userTest.getFriends()).thenReturn(0);
        when(userTest.getGoalCount()).thenReturn(0);
        assertThat(UserUtil.isUserEmpty(userTest), is(true));
    }
}
