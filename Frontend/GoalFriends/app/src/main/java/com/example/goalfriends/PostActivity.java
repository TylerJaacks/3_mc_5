package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    private Spinner spinner;
    private ImageButton cancelButton;
    private Button postNewGoalButton;
    private EditText goalNameText;
    private EditText goalDescText;
    private SeekBar goalProgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        spinner = (Spinner) findViewById(R.id.GoalCategorySpinner);
        cancelButton = (ImageButton) findViewById(R.id.postGoalCancelButton);
        postNewGoalButton = (Button) findViewById(R.id.postNewGoalButton);

        goalNameText = (EditText) findViewById(R.id.editTextGoalName);
        goalDescText = (EditText) findViewById(R.id.editTextGoalDesc);
        goalProgBar = (SeekBar) findViewById(R.id.seekBarGoalProg);




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PostActivity.this, "Post New Goal Canceled", Toast.LENGTH_LONG).show();
                startActivity(new Intent(PostActivity.this, HomescreenActivity.class));

            }
        });

        postNewGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostGoal(goalNameText.getText().toString(), goalDescText.getText().toString(), spinner.getSelectedItem().toString(), goalProgBar.getProgress());
            }
        });
    }

    private void PostGoal(String goalName, String goalDesc, String goalCat, int goalProg) {
        if (goalName.isEmpty()) {
            goalNameText.setError("Goal Name is required");
            goalNameText.requestFocus();
            return;
        } else if (goalDesc.isEmpty()) {
            goalDescText.setError("Goal Description is required");
            goalDescText.requestFocus();
            return;
        }

        Toast toast = new Toast(getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(this);
        //TODO: Change url to be New Goal Url
        String url = "http://coms-309-054.cs.iastate.edu:8080/login";

        JSONObject postGoalData = new JSONObject();

        try {
            postGoalData.put("name", goalName);
            postGoalData.put("description", goalDesc);
            postGoalData.put("category", goalCat);
            postGoalData.put("progress", goalProg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postGoalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(PostActivity.this, "New Goal Succesfully Posted", Toast.LENGTH_LONG).show();
                startActivity(new Intent(PostActivity.this, HomescreenActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
    });

        queue.add(jsonObjectRequest);
    }
}