package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Spinner spinner = (Spinner) findViewById(R.id.GoalCategorySpinner);
        ImageButton cancelButton = (ImageButton) findViewById(R.id.postGoalCancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PostActivity.this, "Post New Goal Canceled", Toast.LENGTH_LONG).show();
                startActivity(new Intent(PostActivity.this, HomescreenActivity.class));

            }
        });
    }
}