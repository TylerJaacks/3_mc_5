package edu.iastate.goalfriends.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.iastate.goalfriends.R;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        String token = MainActivity.token;
        TextView tokenTest = (TextView)findViewById(R.id.textView4);
        tokenTest.setText(token);
    }
}