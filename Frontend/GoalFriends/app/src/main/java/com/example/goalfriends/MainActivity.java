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

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/login";
        JsonObjectRequest getEmailRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(!response.getBoolean("error")) {
                        JSONObject emailJson = response.getJSONObject("email");
                        JSONObject passwordJson = response.getJSONObject("password");
                    }
                    else{

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
    }

}