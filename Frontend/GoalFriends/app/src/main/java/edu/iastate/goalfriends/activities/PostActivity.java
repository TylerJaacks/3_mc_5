package edu.iastate.goalfriends.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.RestUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * New goal page that allows the user to create a goal.
 * PostGoal() method sends the user's custom goal to the server to be stored.
 */
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
                startActivity(new Intent(PostActivity.this, HomescreenActivity.class));
            }
        });
    }

    /**
     * Sends a volley POST request to the server to store the user's new goal.
     * Sends the goal name, description, category and progress as headers.
     * @param goalName Name of the new goal as a String
     * @param goalDesc Description of the new goal as a String
     * @param goalCat Category of the new goal as a String
     * @param goalProg Progress of the new goal as an Int
     */
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
        String token = MainActivity.token;

        Toast toast = new Toast(getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://coms-309-054.cs.iastate.edu:8080/goal?goalName=" + goalName + "&" + "goalCategory=" + goalCat;

        JSONObject postGoalData = new JSONObject();

        try {
            postGoalData.put("name", goalName);
            postGoalData.put("description", goalDesc);
            postGoalData.put("category", goalCat);
            postGoalData.put("progress", goalProg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", token);

        HashMap<String, String> params = new HashMap<>();
        params.put("goalName", goalName);
        params.put("goalCategory", goalCat);

        RestUtilities.volleyRequest(this, Request.Method.POST, url, postGoalData, params, headers);
    }
}