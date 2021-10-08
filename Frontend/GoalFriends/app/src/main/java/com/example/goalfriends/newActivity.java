package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class newActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        String token = MainActivity.token;
        TextView tokenTest = (TextView)findViewById(R.id.textView4);
        tokenTest.setText(token);
    }
}