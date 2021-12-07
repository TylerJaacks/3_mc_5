package edu.iastate.goalfriends;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import edu.iastate.goalfriends.goals.Goal;
import edu.iastate.goalfriends.goals.GoalUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GoalUtilTest {
    @Mock
    Goal goalTest;

    @Mock
    Goal otherGoalTest;

    @Test
    public void checkDisplayGoal() {
        when(goalTest.getName()).thenReturn("Goal Name");
        when(goalTest.getCategory()).thenReturn("Fitness");
        when(goalTest.getDescription()).thenReturn("Goal Description");
        when(goalTest.getProgress()).thenReturn(100);
        assertThat(GoalUtil.DisplayGoal(goalTest), is("Name: Goal Name Description: Goal Description Category: Fitness Progress: 100"));
    }

    @Test
    public void checkIsEmptyGoal() {
        when(goalTest.getName()).thenReturn("Default");
        when(goalTest.getCategory()).thenReturn("None");
        when(goalTest.getDescription()).thenReturn("None");
        when(goalTest.getProgress()).thenReturn(0);
        assertThat(GoalUtil.isGoalEmpty(goalTest), is(true));
    }

    @Test
    public void checkIsGoalsEqual() {
        when(goalTest.getName()).thenReturn("Goal Name");
        when(goalTest.getCategory()).thenReturn("Fitness");
        when(goalTest.getDescription()).thenReturn("Goal Description");
        when(goalTest.getProgress()).thenReturn(50);

        when(otherGoalTest.getName()).thenReturn("Goal Name");
        when(otherGoalTest.getCategory()).thenReturn("Fitness");
        when(otherGoalTest.getDescription()).thenReturn("Goal Description");
        when(otherGoalTest.getProgress()).thenReturn(50);


        assertThat(GoalUtil.isGoalsEqual(goalTest, otherGoalTest), is(true));
    }

    @Test
    public void checkGoalProgAsDecimal() {
        when(goalTest.getName()).thenReturn("Goal Name");
        when(goalTest.getCategory()).thenReturn("Fitness");
        when(goalTest.getDescription()).thenReturn("Goal Description");
        when(goalTest.getProgress()).thenReturn(42);
        assertThat(GoalUtil.getGoalProgAsDecimal(goalTest), is(0.42));
    }

}
