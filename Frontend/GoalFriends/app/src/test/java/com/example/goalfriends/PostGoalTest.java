package com.example.goalfriends;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import android.content.SharedPreferences;

@RunWith(MockitoJUnitRunner.class)
public class PostGoalTest {
    @Mock
    Goal goalTest;

    @Test
    public void checkGoalName() {
        when(goalTest.getName()).thenReturn("Goal Name Test");
        String goalName = goalTest.getName();
        assertThat(goalName, is("Goal Name Test"));
    }

    @Test
    public void checkGoalDesc() {
        when(goalTest.getDescription()).thenReturn("Goal Desc Test");
        String goalDesc = goalTest.getDescription();
        assertThat(goalDesc, is("Goal Desc Test"));
    }

    @Test
    public void checkGoalCategory() {
        when(goalTest.getCategory()).thenReturn("Health");
        String goalCat = goalTest.getCategory();
        assertThat(goalCat, is("Health"));
    }

    @Test
    public void checkGoalProgress() {
        when(goalTest.getProgress()).thenReturn(100);
        int goalProg = goalTest.getProgress();
        assertThat(goalProg, is(100));
    }
}
