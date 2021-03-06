package edu.iastate.goalfriends.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import edu.iastate.goalfriends.R;

/**
 * This EditProfileActivity class will allow users to edit their personal
 * profile. They will be able to change their profile picture and description.
 * @author Kyle Todd
 */
public class EditProfileActivity extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backButton = (ImageButton) findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class)));
    }
}