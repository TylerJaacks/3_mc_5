package edu.iastate.goalfriends.activities;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import edu.iastate.goalfriends.FriendManager;
import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.goals.GoalManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private Button login;
    private int statusCode;
    public static String token;
    public static GoalManager goalManager;
    public static FriendManager friendManager;

    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.uName);
        password = (EditText) findViewById(R.id.uPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        TextView here = findViewById(R.id.registerHere);
        goalManager = new GoalManager();
        friendManager = new FriendManager();

        here.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterHere.class)));

        login.setOnClickListener(view -> login(user.getText().toString().trim(), password.getText().toString().trim()));
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


        final TextView textView = (TextView) findViewById(R.id.text);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/login";

        StringRequest stringObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

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

                        token = responseJSON.getString("token");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();

                    email = userEmail;

                    startActivity(new Intent(MainActivity.this, HomescreenActivity.class));
                }
                else{
                    try {
                        int errorCode = responseJSON.getInt("errorCode");
                        String errorMessage = responseJSON.getString("errorMessage");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();

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