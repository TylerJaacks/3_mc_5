package edu.iastate.goalfriends.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.threads.SearchUsersThread;
import edu.iastate.goalfriends.users.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Search page that allows the user to search for other users in
 * the server to view their goals or add them as friends.
 */
public class SearchActivity extends AppCompatActivity {
    public static List<String> userList = new ArrayList<>();

    public static final String USERS_ENDPOINT = "http://coms-309-054.cs.iastate.edu:8080/users/all";
    private SearchView searchView;
    private ListView listView;
    public ArrayAdapter<String > adapter;
    private ImageButton cancelButton;

    public JSONObject usersJSONObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.resultslistView);
        cancelButton = (ImageButton) findViewById(R.id.searchCancelButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(adapter);

        cancelButton.setOnClickListener(view -> {
            Toast.makeText(SearchActivity.this, "Search Canceled", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SearchActivity.this, HomescreenActivity.class));
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                renderList(true);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (searchView.getQuery().length() == 0) {
//                    renderList(true);
//                }
//                return false;
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Map<String, String> headers = new HashMap<>();

                headers.put("token", MainActivity.token);

                SearchUsersThread searchUsersThread = new SearchUsersThread(SearchActivity.this, Request.Method.GET, USERS_ENDPOINT, new JSONObject(), new HashMap<>(), headers, query);
                searchUsersThread.start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // Click listener for the search list
        listView.setOnItemClickListener((adapter, v, position, arg3) -> {
            String username = (String)adapter.getItemAtPosition(position);

            // Starts the Other User Profile Activity with a bundle containing the username of the searched user
            Intent intent = new Intent(SearchActivity.this, OtherUserProfileActivity.class);
            Bundle b = new Bundle();
            b.putString("username", username);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        });
    }
}