package edu.iastate.goalfriends.threads;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.iastate.goalfriends.activities.HomescreenActivity;
import edu.iastate.goalfriends.activities.MainActivity;
import edu.iastate.goalfriends.activities.ProfileActivity;
import edu.iastate.goalfriends.goals.Goal;

public class ProfileUpdateGoalListThread extends Thread{

    private ProfileActivity activity;

    public static String responseString = "-1";

    private int httpMethod;
    private String url;
    private JSONObject requestBody;
    private Map<String, String> params;
    private Map<String, String> headers;

    public ProfileUpdateGoalListThread(ProfileActivity activity, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers){
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
    public void run() {

        while(responseString.equals("-1") || responseString.isEmpty()){
            //System.out.println("Resp: " + responseString);
            //Log.d("Thread", "Resp: " + responseString);
            super.run();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        activity.runOnUiThread(new ProfileUpdateList(activity, responseString));
        activity.runOnUiThread(new ProfileRestartThread(activity, httpMethod, url, requestBody, params, headers));
    }

    private void volleyRequestStr(Context context, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers) throws ExecutionException, InterruptedException {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(httpMethod, url,
                response -> {
                    responseString = response;
                    //Log.e("HttpClient", "success! response: " + response);
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

class ProfileUpdateList implements Runnable {

    private ProfileActivity activity;
    private String response;

    public ProfileUpdateList(ProfileActivity activity, String response){
        this.activity = activity;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            ProfileActivity.profileUserGoalsArrayList.clear();
            MainActivity.goalManager.clearGoals();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("goalList");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jo = (JSONObject) jsonArray.get(i);
                String goalName = jo.getString("goalName");
                double goalProgress = jo.getDouble("goalProgress");
                String goalCategory = jo.getString("goalCategory");
                String goalCategoryPrefix = goalCategory.equals("NOT_SET") ? "" : "[" + goalCategory + "]";
                String goalString = goalCategoryPrefix + " " + goalName + ": " + goalProgress + "%";
                ProfileActivity.profileUserGoalsArrayList.add(goalString);
                Goal goal = new Goal(goalName, goalCategory, (int)goalProgress);
                MainActivity.goalManager.addGoal(goal);
            }

            activity.goalCount.setText(String.valueOf(jsonArray.length()));

            ProfileActivity.updateAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

class ProfileRestartThread implements Runnable {

    private ProfileActivity activity;
    private int httpMethod;
    private String url;
    private JSONObject requestBody;
    private Map<String, String> params;
    private Map<String, String> headers;

    public ProfileRestartThread(ProfileActivity activity, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers){
        this.activity = activity;
        this.httpMethod = httpMethod;
        this.url = url;
        this.requestBody = requestBody;
        this.params = params;
        this.headers = headers;
    }

    @Override
    public void run(){
        ProfileUpdateGoalListThread.responseString = "-1";
        /*UpdateGoalListThread uglt = new UpdateGoalListThread(activity, httpMethod, url, requestBody, params, headers);
        uglt.start();*/
    }
}