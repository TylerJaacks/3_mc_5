package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registerHere extends AppCompatActivity {

    private Button cancel;
    private Button createAccount;
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText cpassword;
    private int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_here);

        cancel = (Button) findViewById(R.id.cancelButton);
        createAccount = (Button) findViewById(R.id.MakeNewAccount);
        username = (EditText) findViewById(R.id.SetUsername);
        email = (EditText) findViewById(R.id.SetEmailAddress);
        phone = (EditText) findViewById(R.id.editTextPhone);
        password = (EditText) findViewById(R.id.SetPassword);
        cpassword = (EditText) findViewById(R.id.ConfirmPassword);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerHere.this, MainActivity.class));
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add more requirements for registration (e.g. All text fields must be non-empty, phone number and email must be in correct formats)
                register(username.getText().toString().trim(), email.getText().toString().trim(), phone.getText().toString().trim(),
                        password.getText().toString().trim(), cpassword.getText().toString().trim());

                if (password.getText().toString().equals(cpassword.getText().toString()) && !password.getText().toString().matches("")) {
                    Toast.makeText(registerHere.this, "Account Created!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(registerHere.this, MainActivity.class));
                } else {
                    Toast.makeText(registerHere.this, "Confirm Password must match Password!", Toast.LENGTH_LONG).show();
                    password.getText().clear();
                    cpassword.getText().clear();
                }


            }
        });


    }

    private void register(String regUsername, String regEmail, String regPhone, String regPassword, String regCpassword) {
        if (regUsername.isEmpty()) {
            username.setError("Username is required");
            username.requestFocus();
            return;
        } else if (regEmail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        } else if (regPhone.isEmpty()) {
            phone.setError("Phone Number is required");
            phone.requestFocus();
            return;
        } else if (regPassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        } else if (regCpassword.isEmpty()) {
            cpassword.setError("Password is required");
            cpassword.requestFocus();
            return;
        }

        Toast toast = new Toast(getApplicationContext());
        final TextView textView = (TextView) findViewById(R.id.text);
// ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/register";

        // Request a string response from the provided URL.
        StringRequest stringObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            int statusCode = 0;

            @Override
            public void onResponse(String response) {
                Log.d("hey", response);
                JSONObject responseJSON = null;
                try {
                    responseJSON = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (statusCode == 200) {
                    try {

                        String token = responseJSON.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("hey", "Error");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.e("Response error", error.getMessage());
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("email", regEmail);
                    jsonObject.put("username", regUsername);
                    jsonObject.put("phonenumber", regPhone);
                    jsonObject.put("password", regPassword);

                    return jsonObject == null ? null : jsonObject.toString().getBytes("utf-8");
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json;");

                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringObjectRequest);
    }
}
