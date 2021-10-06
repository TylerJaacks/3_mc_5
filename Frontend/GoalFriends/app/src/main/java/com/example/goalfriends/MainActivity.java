package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private Button login;
    private int statusCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.uName);
        password = (EditText) findViewById(R.id.uPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        TextView here = findViewById(R.id.registerHere);


        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, registerHere.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(user.getText().toString().trim(), password.getText().toString().trim());
            }
        });
    }

    private void login(String userEmail, String userPassword) {
        if (userEmail.isEmpty()) {
            user.setError("Email is required");
            user.requestFocus();
            return;
        } else if (userPassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        Toast toast = new Toast(getApplicationContext());

     /*   RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
        String url = "http://coms-309-054.cs.iastate.edu:8080/login";
        StringRequest getEmailRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hey = new JSONObject(response);
                    if (!hey.getBoolean("error")) {
                        JSONObject emailJson = hey.getJSONObject("email");
                        JSONObject passwordJson = hey.getJSONObject("password");
                        toast.setText("Test");
                        toast.show();

                    } else {
                        toast.setText("Error");
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("email", userEmail);
                params.put("password", userPassword);
                return params;
            }
        };
        queue.add(getEmailRequest);

      */


        final TextView textView = (TextView) findViewById(R.id.text);
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/login";

// Request a string response from the provided URL.
        StringRequest stringObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            int statusCode = 0;

            @Override
            public void onResponse(String response) {
                Log.d("hey",response);
                JSONObject responseJSON = null;
                try {
                    responseJSON = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(statusCode == 200){
                    try {

                        String token = responseJSON.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        int errorCode = responseJSON.getInt("errorCode");
                        String errorMessage = responseJSON.getString("errorMessage");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null && error.getMessage() != null) {
                    Log.e("Response error", error.getMessage());
                }
            }
        })
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("email", userEmail);
                params.put("password", userPassword);
                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringObjectRequest);
    }

}