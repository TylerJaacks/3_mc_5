package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;
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
    RecyclerView RecyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Goals> goalset = new ArrayList<Goals>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


        addGoal = (ImageButton) findViewById(R.id.AddGoalbutton);
        profile = (ImageButton) findViewById(R.id.Homescreenbutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);

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
    private void populateGoals(){
        int i = 0;
                while(i < 10){
                    goalset.add("Goal " + i);
                    i++;
                }
                goalset.add(null);
    }

    private void setupAdapter(){
        recyclerViewAdapter = new RecyclerViewAdapter(goalset);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


}