package com.example.goalfriends.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalfriends.RestUtilities;
import com.example.goalfriends.goals.Goal;
import com.example.goalfriends.R;
import com.example.goalfriends.RecyclerViewAdapter;
import com.example.goalfriends.threads.UpdateGoalListThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Homescreen page that has 3 navigation buttons at the bottom. Also has two infinite scrolls
 * views that show the user's current goals and their friends recent activity.
 */
public class HomescreenActivity extends AppCompatActivity {

    private ImageButton addGoal;
    private ImageButton profile;
    private ImageButton search;

    public static ArrayList<String> listItems = new ArrayList<String>();

    public static ArrayAdapter<String> adapter;

    ListView goalListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


        addGoal = (ImageButton) findViewById(R.id.AddGoalbutton);
        profile = (ImageButton) findViewById(R.id.Profilebutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);

        // Uncomment to add 10 placeholder goals to the RecyclerView for testing
        // goalset = Goal.populateGoal(10);

        goalListView = (ListView) findViewById(R.id.rvList);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        goalListView.setAdapter(adapter);

        /*ArrayList<Goal> goals = getGoals(MainActivity.token);

        for(Goal goal : goals){
            System.out.println(goal.toString());
            listItems.add(goal.toString());
        }*/

        updateAdapter();

        String url = "http://coms-309-054.cs.iastate.edu:8080/goal/all";

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", MainActivity.token);

        UpdateGoalListThread uglt = new UpdateGoalListThread(this, Request.Method.GET, url, new JSONObject(), new HashMap<>(), headers);
        uglt.start();
        // TODO: Add click listeners to goal rows that way when users tap on a goal it brings them to a page where they can edit it


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

    public static void updateAdapter(){
        adapter.notifyDataSetChanged();
    }

    /**
     * Sends a volley GET request to the server to get an ArrayList of all of the user's current goals.
     * @param token String representing the user's current token to be sent as a header.
     * @return ArrayList of the user's current goals form the server
     */
    /*private ArrayList<Goal> getGoals(String token) {

        String response;
        ArrayList<Goal> goalList = new ArrayList<Goal>();

        //RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/goal/all";

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", MainActivity.token);

        try {
            response = RestUtilities.volleyRequestStr(this, Request.Method.GET, url, new JSONObject(), new HashMap<>(), headers);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, getGoalData, new Response.Listener<JSONObject>() {
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



        queue.add(jsonObjectRequest); *-/

        return goalList;
    }*/
}