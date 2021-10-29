package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class HomescreenActivity extends AppCompatActivity {

    private ImageButton addGoal;
    private ImageButton profile;
    private ImageButton search;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    public static ArrayList<Goal> goalset = new ArrayList<Goal>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


        addGoal = (ImageButton) findViewById(R.id.AddGoalbutton);
        profile = (ImageButton) findViewById(R.id.Profilebutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);

        // Uncomment to add 10 placeholder goals to the RecyclerView for testing
        // goalset = Goal.populateGoal(10);

        recyclerView = findViewById(R.id.rvGoals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, goalset);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // TODO: Add click listeners to goal rows that way when users tap on a goal it brings them to a page where they can edit it
        // recyclerViewAdapter.setClickListener(this);


        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomescreenActivity.this, PostActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomescreenActivity.this, ProfileActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomescreenActivity.this, SearchActivity.class));
            }
        });
    }
}