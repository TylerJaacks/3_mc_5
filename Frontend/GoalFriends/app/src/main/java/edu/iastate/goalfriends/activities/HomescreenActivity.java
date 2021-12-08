package edu.iastate.goalfriends.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Request;
import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.threads.UpdateGoalListThread;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Homescreen page that has 3 navigation buttons at the bottom. Also has two infinite scrolls
 * views that show the user's current goals and their friends recent activity.
 */
public class HomescreenActivity extends AppCompatActivity {

    private ImageButton addGoal;
    private ImageButton profile;
    private ImageButton search;

    public static ArrayList<String> listItems = new ArrayList<>();
    public static ArrayAdapter<String> adapter;

    ListView goalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


        addGoal = (ImageButton) findViewById(R.id.AddGoalbutton);
        profile = (ImageButton) findViewById(R.id.Profilebutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);

        goalListView = (ListView) findViewById(R.id.rvList);

        adapter = new ArrayAdapter<>(this,
                R.layout.homescreen_listview,
                listItems);

        goalListView.setAdapter(adapter);

        updateAdapter();

        String url = "http://coms-309-054.cs.iastate.edu:8080/goal/all";

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", MainActivity.token);

        UpdateGoalListThread uglt = new UpdateGoalListThread(this, Request.Method.GET, url, new JSONObject(), new HashMap<>(), headers);
        uglt.start();

        // TODO: Add click listeners to goal rows that way when users tap on a goal it brings them to a page where they can edit it

        addGoal.setOnClickListener(v -> startActivity(new Intent(HomescreenActivity.this, PostActivity.class)));

        profile.setOnClickListener(v -> startActivity(new Intent(HomescreenActivity.this, ProfileActivity.class)));

        search.setOnClickListener(v -> startActivity(new Intent(HomescreenActivity.this, SearchActivity.class)));
    }

    public static void updateAdapter(){
        adapter.notifyDataSetChanged();
    }
}