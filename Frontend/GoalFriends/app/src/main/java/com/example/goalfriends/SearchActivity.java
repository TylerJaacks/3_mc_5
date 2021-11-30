package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;

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

/**
 * Search page that allows the user to search for other users in
 * the server to view their goals or add them as friends.
 */
public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String > adapter;
    private ImageButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.resultslistView);
        cancelButton = (ImageButton) findViewById(R.id.searchCancelButton);


        list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this, "Search Canceled", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SearchActivity.this, HomescreenActivity.class));

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (list.contains(query)){
                    adapter.getFilter().filter(query);
                } else {
                    Toast.makeText(SearchActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private ArrayList<User> getUsers(String token) {

        JSONObject getUserData = new JSONObject();
        ArrayList<User> userList = new ArrayList<User>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/goal/all";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, getUserData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("Users");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject userJS = arr.getJSONObject(i);
                        userList.add(new User(userJS.getString("name")));

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

        return userList;
    }
}