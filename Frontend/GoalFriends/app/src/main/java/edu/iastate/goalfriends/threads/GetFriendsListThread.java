package edu.iastate.goalfriends.threads;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.iastate.goalfriends.activities.ProfileActivity;

public class GetFriendsListThread extends Thread {

    private ProfileActivity activity;

    public static String responseString = "-1";

    private int httpMethod;
    private String url;
    private JSONObject requestBody;
    private Map<String, String> params;
    private Map<String, String> headers;

    public GetFriendsListThread(ProfileActivity activity, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers){
        this.activity = activity;
        this.httpMethod = httpMethod;
        this.url = url;
        this.requestBody = requestBody;
        this.params = params;
        this.headers = headers;
    }

    @Override
    public synchronized void start() {
        super.start();
        try {
            volleyRequestStr(activity, httpMethod, url, requestBody, params, headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        super.run();

        while(responseString.equals("-1") || responseString.isEmpty()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ProfileActivity.friends.clear();

        try{
            JSONObject friends = new JSONObject(responseString);
            JSONArray friendsArray = friends.getJSONArray("friends");

            for (int i = 0; i < friendsArray.length(); i++) {
                JSONObject object = friendsArray.getJSONObject(i);
                String username = object.getString("username");
                ProfileActivity.friends.add(username);
            }

            activity.runOnUiThread(new GetFriendsListRunnable(activity, friendsArray.length()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void volleyRequestStr(Context context, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers) throws ExecutionException, InterruptedException {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(httpMethod, url,
                response -> {
                    responseString = response;
                    Log.e("HttpClient", "success! response: " + response);
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
}

class GetFriendsListRunnable implements Runnable {
    private ProfileActivity profileActivity;
    private int size;

    public GetFriendsListRunnable(ProfileActivity profileActivity, int size) {
        this.profileActivity = profileActivity;
        this.size = size;
    }

    @Override
    public void run() {
        profileActivity.goalCount.setText(size);
    }
}