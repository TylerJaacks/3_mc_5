package com.example.goalfriends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterHere extends AppCompatActivity {

    private static final String TAG = "registerHere";
    private Button cancel;
    private Button createAccount;
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText confirmPassword;
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private ValidEmail mEmailValidator;
    private ValidPhoneNumber mPhoneValidator;
    private ValidPassword mPasswordValidator;

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
        confirmPassword = (EditText) findViewById(R.id.ConfirmPassword);

        mEmailValidator = new ValidEmail();
        email.addTextChangedListener(mEmailValidator);

        mPasswordValidator = new ValidPassword();
        password.addTextChangedListener(mPasswordValidator);

        mPhoneValidator = new ValidPhoneNumber();
        phone.addTextChangedListener(mPhoneValidator);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);
        populateUi();

        cancel.setOnClickListener(v -> startActivity(new Intent(RegisterHere.this, MainActivity.class)));


        createAccount.setOnClickListener(v -> {

            register(username.getText().toString().trim(), email.getText().toString().trim(), phone.getText().toString().trim(),
                    password.getText().toString().trim(), confirmPassword.getText().toString().trim());

            if (password.getText().toString().equals(confirmPassword.getText().toString()) && !password.getText().toString().matches("")) {
                Toast.makeText(RegisterHere.this, "Account Created!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterHere.this, MainActivity.class));
            } else {
                Toast.makeText(RegisterHere.this, "Confirm Password must match Password!", Toast.LENGTH_LONG).show();
                password.getText().clear();
                confirmPassword.getText().clear();
            }

            String nName = username.getText().toString();
            String nEmail = email.getText().toString();
            String nPhone = phone.getText().toString();
            String nPassword = password.getText().toString();

            SharedPreferenceEntry sharedPreferenceEntry = new SharedPreferenceEntry(nName, nEmail, nPhone, nPassword);
            boolean isSuccess = mSharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry);
            if (isSuccess) {
                Toast.makeText(RegisterHere.this, "Personal information saved", Toast.LENGTH_LONG).show();
                Log.i(TAG, "Personal information saved");
            } else {
                Log.e(TAG, "Failed to write personal information to SharedPreferences");
            }
        });


    }

    private void populateUi() {
        SharedPreferenceEntry sharedPreferenceEntry;
        sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();
        username.setText(sharedPreferenceEntry.getName());
        email.setText(sharedPreferenceEntry.getEmail());
        phone.setText(sharedPreferenceEntry.getPhone());
        password.setText(sharedPreferenceEntry.getPassword());
    }


    private void register(String regUsername, String regEmail, String regPhone, String regPassword, String regCpassword) {
        if (regUsername.isEmpty()) {
            username.setError("Username is required");
            username.requestFocus();
            return;
        } else if (!mEmailValidator.isValid()) {
            email.setError("Invalid Email");
            Log.w(TAG, "Not saving personal information: Invalid Email");
            email.requestFocus();
            return;
        } else if (!mPhoneValidator.isValid()) {
            phone.setError("Invalid Phone Number");
            Log.w(TAG, "Not saving personal information: Invalid Phone Number");
            phone.requestFocus();
            return;
        } else if (!mPasswordValidator.isValid()) {
            password.setError("Invalid Password");
            Log.w(TAG, "Not saving personal information: Invalid Password");
            password.requestFocus();
            return;
        } else if (regCpassword.isEmpty()) {
            confirmPassword.setError("Password is required");
            confirmPassword.requestFocus();
            return;
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-054.cs.iastate.edu:8080/register";

        // Request a string response from the provided URL.
        StringRequest stringObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            final int statusCode = 0;

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
        }, error -> Log.e("Response error", error.getMessage())) {
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

                    return jsonObject == null ? null : jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json;");

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringObjectRequest);
    }
}