package edu.iastate.goalfriends.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.goals.Goal;
import edu.iastate.goalfriends.threads.ProfileUpdateGoalListThread;
import edu.iastate.goalfriends.threads.UpdateFriendGoalsListThread;
import edu.iastate.goalfriends.threads.UpdateGoalListThread;
import edu.iastate.goalfriends.users.User;

//TODO: Update Javadoc
/**
 * This ProfileActivity class is used to represent a users profile on Goal Friends.
 * Here a user will be able to edit their profile, check the amount of goals, see
 * their current goals, see their amount of followers, and who they are following.
 */
public class ProfileActivity extends AppCompatActivity {
    private static final String userGoalsEndpoint = "http://coms-309-054.cs.iastate.edu:8080/goal/all";

    private ImageButton addGoal;
    private ImageButton homeScreen;
    private ImageButton search;
    private ImageButton settings;
    private TextView Username;
    private TextView friendCount;
    private TextView goalCount;
    private User mainUser;

    public static ArrayList<String> profileUserGoalsArrayList = new ArrayList<>();
    public static ArrayAdapter<String> adapter;

    ListView goalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String token = MainActivity.token;

        addGoal = (ImageButton) findViewById(R.id.AddGoalbutton);
        homeScreen = (ImageButton) findViewById(R.id.Homescreenbutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);
        settings = (ImageButton) findViewById(R.id.settingsButton);
        Username = (TextView) findViewById(R.id.textView10);
        friendCount = (TextView) findViewById(R.id.textView15);
        goalCount = (TextView) findViewById(R.id.textView14);
        goalListView = (ListView) findViewById(R.id.goalList);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                profileUserGoalsArrayList);

        goalListView.setAdapter(adapter);

        updateAdapter();

        refreshGoals();

        goalCount.setText(String.valueOf(MainActivity.goalManager.getGoalList().size()));

        addGoal.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, PostActivity.class)));

        homeScreen.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, HomescreenActivity.class)));

        search.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, SearchActivity.class)));

        settings.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, SettingsActivity.class)));

        goalListView.setOnItemClickListener((parent, view, position, id) -> {
            if(view instanceof TextView){
                TextView tv = (TextView)  view;
                String text = tv.getText().toString();
                text = text.replace("[FITNESS] ", "").replace("[FOOD] ", "")
                        .replace("[SOCIAL] ", "").replace("[OTHER] ", "");
                String[] split = text.split(":");
                text = split[0];
                text = text.trim();
                Goal goal = MainActivity.goalManager.getByName(text);
                HomescreenActivity.editingGoal = goal;
                startActivity(new Intent(ProfileActivity.this, EditGoalActivity.class));
            }
        });
    }

    /**
     * Gets the Users Username, Friend Count, and Goal Count
     * @param token Login Token
     * @return a User for a given token.
     */
    private User getUsername(String token) {
        JSONObject getUser = new JSONObject();
        User user = new User();

        String url = "http://coms-309-054.cs.iastate.edu:8080/user";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, getUser, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                        JSONObject userJS = response.getJSONObject("MainUser");
                    new User(userJS.getString("name"), userJS.getInt("friends"), userJS.getInt("goalCount"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Throwable::printStackTrace) {
            @Override
            public Map<String, String> getHeaders()   {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };

        return user;
    }

    // TODO: Add GET request to get user's token with the username
    private String getToken(String username) {
        return "";
    }

    public static void updateAdapter(){
        adapter.notifyDataSetChanged();
    }

    private void refreshGoals(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", MainActivity.token);

        ProfileUpdateGoalListThread updateUserGoalsListThread =
                new ProfileUpdateGoalListThread (this, Request.Method.GET, userGoalsEndpoint, new JSONObject(), new HashMap<>(), headers);
        updateUserGoalsListThread.start();
    }
}
