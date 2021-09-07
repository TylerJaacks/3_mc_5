package com.example.logonpagetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    EditText username = (EditText)findViewById(R.id.editText1);
    EditText password = (EditText)findViewById(R.id.editText2);

    Toast success = Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG);
    Toast fail = Toast.makeText(getApplicationContext(), "Wrong Username or Password!", Toast.LENGTH_SHORT);

    public void login(View view) {
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            success.show();
        }
        else {
            fail.show();
        }
    }
}