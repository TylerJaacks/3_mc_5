package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Homescreen page that has 3 navigation buttons at the bottom. Also has 2 infinite scrolls
 * views that show the user's current goals and their friends recent activity.
 */
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
        recyclerViewAdapter = new RecyclerViewAdapter(this, getGoals(MainActivity.token));
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

    /**
     * Sends a volley GET request to the server to get an ArrayList of all of the user's current goals.
     * @param token String representing the user's current token to be sent as a header.
     * @return ArrayList of the user's current goals form the server
     */
    private ArrayList<Goal> getGoals(String token) {

        JSONObject getGoalData = new JSONObject();
        ArrayList<Goal> goalList = new ArrayList<Goal>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/goal/all";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, getGoalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray  arr = response.getJSONArray("Goals");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject goalJS = arr.getJSONObject(i);
                        goalList.add(new Goal(goalJS.getString("name"), goalJS.getString("description"), goalJS.getString("category"), goalJS.getInt("progress")));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                return params;
            }
        };

        queue.add(jsonObjectRequest);

        return goalList;
    }
}