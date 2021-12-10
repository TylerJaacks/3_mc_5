package edu.iastate.goalfriends.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class TestAsyncTask extends AsyncTask<String, Void, List<String>> {
    private Context context;
    private String token;

    private static String responseStr = "";

    public TestAsyncTask(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> headers = new HashMap<>();

        headers.put("token", token);

        StringRequest sr = new StringRequest(
                Request.Method.GET,
                "http://coms-309-054.cs.iastate.edu:8080/friendship",
                response -> {
                    responseStr = response;

                    Log.e("HttpClient", "success! response: " + response);
                },
                error -> Log.e("HttpClient", "error: " + error.toString()))
        {
            @Override
            protected Map<String,String> getParams(){
                return new HashMap<>();
            }

            @Override
            public Map<String, String> getHeaders()  {
                return headers;
            }
        };
        queue.add(sr);

        List<String> users = new ArrayList<>();

        try {
            JSONObject friends = new JSONObject(responseStr);
            JSONArray friendsArray = friends.getJSONArray("friends");

            for (int i = 0; i < friendsArray.length(); i++) {
                JSONObject object = friendsArray.getJSONObject(i);

                String username = object.getString("username");

                users.add(username);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    protected void onPostExecute(List<String> user) {
        super.onPostExecute(user);
    }
}
