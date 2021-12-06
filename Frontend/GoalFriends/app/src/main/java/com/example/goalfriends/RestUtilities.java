package com.example.goalfriends;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class RestUtilities {

    public static String responseString;

    public static JSONObject volleyRequest(Context context, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        AtomicReference<JSONObject> responseJsonObject = new AtomicReference<>(new JSONObject());

        int statusCode = -1;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpMethod, url, null, response -> {
            Log.d("goalfriend-sdk", "Response: " + response);

            responseJsonObject.set(response);
        }, error -> {
            Log.e("goalfriend-sdk", error.getLocalizedMessage());
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers.isEmpty()) {
                    return super.getHeaders();
                } else {
                    return headers;
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params.isEmpty()) {
                    return super.getParams();
                } else {
                    return params;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody()  {
                return requestBody  == null ? null : requestBody.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                Log.e("goalfriend-sdk", volleyError.getLocalizedMessage());

                return super.parseNetworkError(volleyError);
            }
        };

        requestQueue.add(jsonObjectRequest);

        return responseJsonObject.get();
    }


    private static void dummy(String response){
        responseString = response;
    }

    public static String volleyRequestStr(Context context, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers) throws ExecutionException, InterruptedException {
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
                        //responseString = response;
                        dummy(response);
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
        return "";
        //Log.d("goalfriends-sdk", responseString);
        //return result;
    }
}
