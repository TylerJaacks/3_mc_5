package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * This ProfileActivity class is used to represent a users profile on Goal Friends.
 * Here a user will be able to edit their profile, check the amount of goals, see
 * their current goals, see their amount of followers, and who they are following.
 */
public class ProfileActivity extends AppCompatActivity {

    private ImageButton addGoal;
    private ImageButton homeScreen;
    private ImageButton search;
    private ImageButton settings;
    private Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        addGoal = (ImageButton) findViewById(R.id.AddGoalbutton);
        homeScreen = (ImageButton) findViewById(R.id.Homescreenbutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);
        settings = (ImageButton) findViewById(R.id.settingsButton);
        editProfile = (Button) findViewById(R.id.editProfileButton);

        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PostActivity.class));
            }
        });

        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HomescreenActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
            }
        });

    }
}