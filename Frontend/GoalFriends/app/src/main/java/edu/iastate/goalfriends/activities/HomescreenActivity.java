package edu.iastate.goalfriends.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.goals.Goal;
import edu.iastate.goalfriends.threads.UpdateFriendGoalsListThread;
import edu.iastate.goalfriends.threads.UpdateGoalListThread;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Homescreen page that has 3 navigation buttons at the bottom. Also has two infinite scrolls
 * views that show the user's current goals and their friends recent activity.
 */
public class HomescreenActivity extends AppCompatActivity {
    private static final String userGoalsEndpoint = "http://coms-309-054.cs.iastate.edu:8080/goal/all";
    private static final String friendGoalsEndpoint = "http://coms-309-054.cs.iastate.edu:8080/goal/friends/all";

    private ImageButton addGoal;
    private ImageButton profile;
    private ImageButton search;
    private ImageButton refreshButton;

    public static Goal editingGoal;

    public static ArrayList<String> userGoalsArrayList = new ArrayList<>();
    public static ArrayList<String> friendsGoalArrayList = new ArrayList<>();
    public static ArrayAdapter<String> userGoalsAdapter;
    public static ArrayAdapter<String> friendsGoalAdapter;

    ListView userGoalsListView;
    ListView friendsGoalsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        addGoal = (ImageButton) findViewById(R.id.AddGoalbutton);
        profile = (ImageButton) findViewById(R.id.Profilebutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);
        refreshButton = (ImageButton) findViewById(R.id.refreshButton);

        userGoalsListView = (ListView) findViewById(R.id.rvList);
        friendsGoalsListView = (ListView) findViewById(R.id.friendsActivityListView);

        userGoalsAdapter = new ArrayAdapter<>(this,
                R.layout.homescreen_listview,
                userGoalsArrayList);

        friendsGoalAdapter = new ArrayAdapter<>(this,
                R.layout.homescreen_listview,
                friendsGoalArrayList);

        userGoalsListView.setAdapter(userGoalsAdapter);
        friendsGoalsListView.setAdapter(friendsGoalAdapter);

        updateAdapter();

        refreshGoals();

        userGoalsListView.setOnItemClickListener((parent, view, position, id) -> {
            if(view instanceof TextView){
                TextView tv = (TextView)  view;
                String text = tv.getText().toString();
                text = text.replace("[FITNESS] ", "").replace("[FOOD] ", "")
                        .replace("[SOCIAL] ", "").replace("[OTHER] ", "");
                String[] split = text.split(":");
                text = split[0];
                text = text.trim();
                Goal goal = MainActivity.goalManager.getByName(text);
                editingGoal = goal;
                startActivity(new Intent(HomescreenActivity.this, EditGoalActivity.class));
            }
        });

        addGoal.setOnClickListener(v -> startActivity(new Intent(HomescreenActivity.this, PostActivity.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(HomescreenActivity.this, ProfileActivity.class)));
        search.setOnClickListener(v -> startActivity(new Intent(HomescreenActivity.this, SearchActivity.class)));
        refreshButton.setOnClickListener(v -> refreshGoals());
    }

    private void refreshGoals(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", MainActivity.token);

        UpdateGoalListThread updateUserGoalsListThread =
                new UpdateGoalListThread (this, Request.Method.GET, userGoalsEndpoint, new JSONObject(), new HashMap<>(), headers);
        updateUserGoalsListThread.start();

        UpdateFriendGoalsListThread updateFriendGoalsListThread =
                new UpdateFriendGoalsListThread(this, Request.Method.GET, friendGoalsEndpoint, new JSONObject(), new HashMap<>(), headers);
        updateFriendGoalsListThread.start();
    }

    public static void updateAdapter(){
        userGoalsAdapter.notifyDataSetChanged();
        friendsGoalAdapter.notifyDataSetChanged();
    }
}