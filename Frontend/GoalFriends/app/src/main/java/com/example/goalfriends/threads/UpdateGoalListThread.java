package com.example.goalfriends.threads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalfriends.RestUtilities;
import com.example.goalfriends.activities.HomescreenActivity;
import com.example.goalfriends.activities.MainActivity;
import com.example.goalfriends.goals.Goal;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class UpdateGoalListThread extends Thread{

    private HomescreenActivity activity;

    public static String responseString = "-1";

    private boolean running = true;
    private int httpMethod;
    private String url;
    private JSONObject requestBody;
    private Map<String, String> params;
    private Map<String, String> headers;

    public UpdateGoalListThread(HomescreenActivity activity, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers){
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
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while(responseString.equals("-1") || responseString.isEmpty()){
            System.out.println("Resp: " + responseString);
            Log.d("Thread", "Resp: " + responseString);
            super.run();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        activity.runOnUiThread(new UpdateList(activity, responseString));
        activity.runOnUiThread(new RestartThread(activity, httpMethod, url, requestBody, params, headers));


    }

    private void volleyRequestStr(Context context, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers) throws ExecutionException, InterruptedException {
        RequestQueue queue = Volley.newRequestQueue(context);

        /*RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.GET, url, future, future){

        };
        queue.add(request);

        String result = future.get();
        Log.d("GoalFriends", result);*/

        StringRequest sr = new StringRequest(httpMethod, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseString = response.toString();
                        Log.e("HttpClient", "success! response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        queue.add(sr);
    }

    private ArrayList<Goal> getGoals(String token) {

        String response;
        ArrayList<Goal> goalList = new ArrayList<Goal>();

        //RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/goal/all";

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", MainActivity.token);

        try {
            response = RestUtilities.volleyRequestStr(activity.getApplicationContext(), Request.Method.GET, url, new JSONObject(), new HashMap<>(), headers);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, getGoalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray  arr = response.getJSONArray("Goals");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject goalJS = arr.getJSONObject(i);
                        goalList.add(new Goal(goalJS.getString("name"), goalJS.getString("description"), goalJS.getString("category"), goalJS.getInt("progress")));

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



        queue.add(jsonObjectRequest);*/

        return goalList;
    }
}

class UpdateList implements Runnable {

    private HomescreenActivity activity;
    private String response;

    public UpdateList(HomescreenActivity activity, String response){
        this.activity = activity;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            HomescreenActivity.listItems.clear();
            JSONObject jsonObject = new JSONObject(response);
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                HomescreenActivity.listItems.add(jsonObject.get(key).toString());
            }
            HomescreenActivity.updateAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

class RestartThread implements Runnable {

    private HomescreenActivity activity;
    private int httpMethod;
    private String url;
    private JSONObject requestBody;
    private Map<String, String> params;
    private Map<String, String> headers;

    public RestartThread(HomescreenActivity activity, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers){
        this.activity = activity;
        this.httpMethod = httpMethod;
        this.url = url;
        this.requestBody = requestBody;
        this.params = params;
        this.headers = headers;
    }

    @Override
    public void run(){
        UpdateGoalListThread.responseString = "-1";
        UpdateGoalListThread uglt = new UpdateGoalListThread(activity, httpMethod, url, requestBody, params, headers);
        uglt.start();
    }
}
