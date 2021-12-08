package edu.iastate.goalfriends.threads;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import edu.iastate.goalfriends.activities.HomescreenActivity;
import edu.iastate.goalfriends.activities.SearchActivity;

public class SearchUsersThread extends Thread {
    private SearchActivity activity;

    public static String responseString = "-1";

    private int httpMethod;
    private String url;
    private JSONObject requestBody;
    private Map<String, String> params;
    private Map<String, String> headers;

    public SearchUsersThread(SearchActivity activity, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers) {
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
            System.out.println("Resp: " + responseString);
            Log.d("Thread", "Resp: " + responseString);
            super.run();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        activity.runOnUiThread(new SearchUsersRunnable(activity, responseString));
    }

    private void volleyRequestStr(Context context,
                                  int httpMethod,
                                  String url,
                                  JSONObject requestBody,
                                  Map<String, String> params,
                                  Map<String, String> headers) {
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

class SearchUsersRunnable implements Runnable {
    private SearchActivity activity;
    private String response;

    public SearchUsersRunnable(SearchActivity activity, String response){
        this.activity = activity;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            JSONObject jsonObject = new JSONObject(response);

            activity.usersJSONObject = jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
