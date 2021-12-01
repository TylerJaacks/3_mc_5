package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO: Update Javadoc
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
    private TextView Username;
    private TextView Friends;
    private TextView goalCount;
    private User mainUser;

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
        Friends = (TextView) findViewById(R.id.textView14);
        goalCount = (TextView) findViewById(R.id.textView15);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            String otherUsername = b.getString("username");
            token = getToken(otherUsername);
        }


        mainUser = getUsername(token);

        Username.setText(mainUser.getName());
        Friends.setText(mainUser.getFriends());
        goalCount.setText(mainUser.getGoalCount());



        //TODO: Add the users personal goals to their profile page w/ RecyclerView

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

    /**
     * Gets the Users Username, Friend Count, and Goal Count
     * @param token
     * @return
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

        return user;
    }

    // TODO: Add GET request to get user's token with the username
    private String getToken(String username) {
        return "";
    }
}