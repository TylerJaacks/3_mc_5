package edu.iastate.goalfriends.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.threads.SearchUsersThread;

/**
 * Search page that allows the user to search for other users in
 * the server to view their goals or add them as friends.
 */
public class FriendsListActivity extends AppCompatActivity {
    public static List<String> friendList = new ArrayList<>();


    private ListView listView;
    public ArrayAdapter<String > adapter;
    private ImageButton cancelButton;

    public JSONObject usersJSONObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);

        listView = (ListView) findViewById(R.id.friendListView);
        cancelButton = (ImageButton) findViewById(R.id.friendlistCancelButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendList);
        listView.setAdapter(adapter);

        cancelButton.setOnClickListener(view -> {
            startActivity(new Intent(FriendsListActivity.this, ProfileActivity.class));
        });

    }
}