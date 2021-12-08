package edu.iastate.goalfriends.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.RestUtilities;

public class EditGoalActivity extends AppCompatActivity {

    private Spinner spinner;
    private ImageButton cancelButton;
    private Button editGoalButton;
    private EditText goalNameText;
    private EditText goalDescText;
    private SeekBar goalProgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);
        String oldName = HomescreenActivity.editingGoalName;
        spinner = (Spinner) findViewById(R.id.editGoalCategoryBox);
        cancelButton = (ImageButton) findViewById(R.id.editGoalCancelButton);
        editGoalButton = (Button) findViewById(R.id.editGoalButton);
        goalNameText = (EditText) findViewById(R.id.editGoalTitleBox);
        goalDescText = (EditText) findViewById(R.id.editGoalDescBox);
        goalProgBar = (SeekBar) findViewById(R.id.editGoalProgressBar);

        cancelButton.setOnClickListener(view -> {
            Toast.makeText(EditGoalActivity.this, "Edit Goal Canceled", Toast.LENGTH_LONG).show();
            startActivity(new Intent(EditGoalActivity.this, HomescreenActivity.class));
        });

        editGoalButton.setOnClickListener(view -> {
            EditGoal(goalNameText.getText().toString(), oldName, goalDescText.getText().toString(), spinner.getSelectedItem().toString(), goalProgBar.getProgress());
            startActivity(new Intent(EditGoalActivity.this, HomescreenActivity.class));
            HomescreenActivity.editingGoalName = "N/A";
        });

    }

    private void EditGoal(String goalName, String newGoalName, String goalDesc, String goalCat, int goalProg) {
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
        //TODO: Change url to be New Goal Url
        String url = "http://coms-309-054.cs.iastate.edu:8080/goal?goalName=" + goalName + "&newGoalName=" + newGoalName + "&goalCategory=" + goalCat +
                "&goalProgress=" + goalProg;

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

        RestUtilities.volleyRequest(this, Request.Method.PUT, url, postGoalData, params, headers);
    }

}