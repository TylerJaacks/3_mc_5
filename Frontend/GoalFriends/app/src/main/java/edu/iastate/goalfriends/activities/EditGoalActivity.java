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
import edu.iastate.goalfriends.threads.EditGoalThread;

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


        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", MainActivity.token);

        editGoalButton.setOnClickListener(view -> {
            String url = "http://coms-309-054.cs.iastate.edu:8080/goal?goalName=" + oldName + "&newGoalName=" + goalNameText.getText().toString() +
                    "&goalCategory=" + spinner.getSelectedItem().toString() + "&goalProgress=" + goalProgBar.getProgress();

            final String newurl = url.replace(" ", "%20");
            EditGoalThread egt = new EditGoalThread(EditGoalActivity.this, Request.Method.PUT, newurl, new JSONObject(), new HashMap<String, String>(), headers);
            egt.start();
            //EditGoal(goalNameText.getText().toString(), oldName, goalDescText.getText().toString(), spinner.getSelectedItem().toString(), goalProgBar.getProgress());
            startActivity(new Intent(EditGoalActivity.this, HomescreenActivity.class));
            HomescreenActivity.editingGoalName = "N/A";
        });

    }

}