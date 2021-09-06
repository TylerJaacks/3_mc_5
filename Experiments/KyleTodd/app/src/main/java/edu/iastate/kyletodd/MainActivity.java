package edu.iastate.kyletodd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText)findViewById(R.id.uName);
        password = (EditText)findViewById(R.id.uPassword);
        login = (Button)findViewById(R.id.buttonLogin);
    }

    private void register(String userName, String userPassword){
        if((userName == "kyletodd") && (userPassword == "password"));
        Intent i = new Intent(MainActivity.this, newActivity.class);
        startActivity(i);
    }
}