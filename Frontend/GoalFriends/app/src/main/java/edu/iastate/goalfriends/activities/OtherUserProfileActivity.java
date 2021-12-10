package edu.iastate.goalfriends.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.RestUtilities;
import edu.iastate.goalfriends.users.User;

/**
 * This ProfileActivity class is used to represent a different users profile on Goal Friends.
 * Here a user will be able to view their profile, check the amount of goals, see
 * their current goals, see their amount of followers, and who they are following.
 * And add them as friends.
 */
public class OtherUserProfileActivity extends AppCompatActivity {

    private ImageButton addGoal;
    private ImageButton homeScreen;
    private ImageButton search;
    private ImageButton settings;
    private TextView Username;
    private TextView Friends;
    private TextView goalCount;
    private User otherUser;
    private Button addFriend;
    private ImageButton cancelButton;

    public static ArrayList<String> listItems = new ArrayList<>();
    public static ArrayAdapter<String> adapter;

    ListView goalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheruserprofile);

        addGoal = (ImageButton) findViewById(R.id.AddGoalbutton);
        homeScreen = (ImageButton) findViewById(R.id.Homescreenbutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);
        Username = (TextView) findViewById(R.id.otherUserName);
        Friends = (TextView) findViewById(R.id.otherUserNumFriends);
        goalCount = (TextView) findViewById(R.id.otherUserGoalNumber);
        goalListView = (ListView) findViewById(R.id.otherUsergoalList);
        addFriend = (Button) findViewById(R.id.AddFriendButton);
        cancelButton = (ImageButton) findViewById(R.id.otherProfileCancel);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        goalListView.setAdapter(adapter);

        updateAdapter();

        Bundle b = getIntent().getExtras();

        String otherUsername = b.getString("username");

        Username.setText(otherUsername);

        //TODO: Add method detecting if a friendship already exists between the two users. If so, then change the "Add Friend" button to "Remove Friend" button and add method that remove friendships


        String url = "http://coms-309-054.cs.iastate.edu:8080/goal/all";

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", getToken(otherUsername));

        //TODO Make new goallistthread

        // = new UpdateGoalListThread(this, Request.Method.GET, url, new JSONObject(), new HashMap<>(), headers);
        //uglt.start();


//        Username.setText(mainUser.getName());
//        Friends.setText(mainUser.getFriends());
//        goalCount.setText(mainUser.getGoalCount());

        //TODO: Add the users personal goals to their profile page w/ RecyclerView

        addGoal.setOnClickListener(v -> startActivity(new Intent(OtherUserProfileActivity.this, PostActivity.class)));

        homeScreen.setOnClickListener(v -> startActivity(new Intent(OtherUserProfileActivity.this, HomescreenActivity.class)));

        search.setOnClickListener(v -> startActivity(new Intent(OtherUserProfileActivity.this, SearchActivity.class)));

        cancelButton.setOnClickListener(v -> startActivity(new Intent(OtherUserProfileActivity.this, SearchActivity.class)));

        addFriend.setOnClickListener(v -> addFriend(otherUsername, MainActivity.token));

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

    private void addFriend(String otherUsername, String userToken) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://coms-309-054.cs.iastate.edu:8080/friendship?otherUsername=" + otherUsername;

        JSONObject friendshipJSON = new JSONObject();

        try {
            friendshipJSON.put("token", userToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", userToken);

        HashMap<String, String> params = new HashMap<>();

        RestUtilities.volleyRequest(this, Request.Method.POST, url, friendshipJSON, params, headers);

        Toast.makeText(OtherUserProfileActivity.this, "Added " + otherUsername + " as a friend!", Toast.LENGTH_LONG).show();
    }
}
