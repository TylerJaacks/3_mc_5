package edu.iastate.goalfriends.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import edu.iastate.goalfriends.FriendManager;
import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.goals.Goal;
import edu.iastate.goalfriends.threads.AlreadyFriendsThread;
import edu.iastate.goalfriends.threads.ProfileUpdateGoalListThread;
import edu.iastate.goalfriends.users.User;

//TODO: Update Javadoc
/**
 * This ProfileActivity class is used to represent a users profile on Goal Friends.
 * Here a user will be able to edit their profile, check the amount of goals, see
 * their current goals, see their amount of followers, and who they are following.
 */
public class ProfileActivity extends AppCompatActivity {
    private static final String userGoalsEndpoint = "http://coms-309-054.cs.iastate.edu:8080/goal/all";
    private static final String friendsEndpoint = "http://coms-309-054.cs.iastate.edu:8080/friendship";

    public static List<String> friends = new ArrayList();

    private ImageButton addGoal;
    private ImageButton homeScreen;
    private ImageButton search;
    private TextView Username;
    private TextView friendCount;
    public TextView goalCount;
    private Button viewFriends;
    private User mainUser;

    private String myUsername = "Username";

    public static ArrayList<String> profileUserGoalsArrayList = new ArrayList<>();
    public static ArrayAdapter<String> adapter;

    public String goalCountStr = "0";

    ListView goalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", MainActivity.token);

        AlreadyFriendsThread aft = new AlreadyFriendsThread(this, 0, friendsEndpoint, new JSONObject(), new HashMap<>(), headers);
        aft.start();

        String token = MainActivity.token;


        addGoal = findViewById(R.id.AddGoalbutton);
        homeScreen = (ImageButton) findViewById(R.id.Homescreenbutton);
        search = (ImageButton) findViewById(R.id.Searchbutton);
        Username = (TextView) findViewById(R.id.textView10);
        friendCount = (TextView) findViewById(R.id.textView15);
        goalCount = (TextView) findViewById(R.id.textView14);
        goalListView = (ListView) findViewById(R.id.goalList);
        viewFriends = (Button) findViewById(R.id.ViewFriendsButton);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                profileUserGoalsArrayList);

        goalListView.setAdapter(adapter);

        updateAdapter();

        refreshGoals();

        addGoal.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, PostActivity.class)));
        homeScreen.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, HomescreenActivity.class)));
        search.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, SearchActivity.class)));
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

        viewFriends.setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivity.this, FriendsActivity.class));
        });

        String email = MainActivity.email;

        getUsername(token, email);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateCounts();
            }
        }, 500);


//        GetFriendsListThread getFriendsListThread = new GetFriendsListThread(this, 0, "http://coms-309-054.cs.iastate.edu:8080/friendship", new JSONObject(), new HashMap<>(), headers);
//        getFriendsListThread.start();
    }

    public void updateCounts(){
        goalCount.setText(String.valueOf(MainActivity.goalManager.getGoalList().size()));
        friendCount.setText(String.valueOf(friends.size()));
    }

    private void getUsername(String token, String email){
        RequestQueue queue = Volley.newRequestQueue(this);

        HashMap<String, String> params = new HashMap<>();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", token);


        StringRequest sr = new StringRequest(Request.Method.GET, "http://coms-309-054.cs.iastate.edu:8080/users?email=" + email,
                response -> {
                    Log.e("HttpClient", "success! response: " + response);
                    try {
                        JSONObject jo = new JSONObject(response);
                        String username = jo.getString("username");
                        myUsername = username;
                        Username.setText(myUsername);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("HttpClient", "error: " + error.toString()))
        {
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
            @Override
            public Map<String, String> getHeaders()  {
                return headers;
            }
        };
        queue.add(sr);
    }

    private JSONObject getFriends(String token) {
        RequestQueue queue = Volley.newRequestQueue(this);

        HashMap<String, String> params = new HashMap<>();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", token);

        AtomicReference<JSONObject> friends = new AtomicReference<>(new JSONObject());

        StringRequest sr = new StringRequest(Request.Method.GET, "http://coms-309-054.cs.iastate.edu:8080/friendship",
                response -> {
                    Log.e("HttpClient", "success! response: " + response);
                    try {
                        friends.set(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("HttpClient", "error: " + error.toString()))
        {
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
            @Override
            public Map<String, String> getHeaders()  {
                return headers;
            }
        };

        queue.add(sr);

        return friends.get();
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
