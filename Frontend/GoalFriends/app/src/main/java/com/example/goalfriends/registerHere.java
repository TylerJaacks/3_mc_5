package com.example.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerHere extends AppCompatActivity {

    private Button cancel;
    private Button createAccount;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_here);

        cancel = (Button)findViewById(R.id.cancelButton);
        createAccount = (Button)findViewById(R.id.MakeNewAccount);
        username = (EditText)findViewById(R.id.SetUsername);
        email = (EditText)findViewById(R.id.SetEmailAddress);
        password = (EditText)findViewById(R.id.SetPassword) ;
        cpassword = (EditText)findViewById(R.id.ConfirmPassword);

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

                if (password.getText().toString().equals(cpassword.getText().toString())) {
                    Toast.makeText(registerHere.this, "Account Created!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(registerHere.this, MainActivity.class));
                }
                else {
                    Toast.makeText(registerHere.this, "Confirm Password must match Password!", Toast.LENGTH_LONG).show();
                    password.getText().clear();
                    cpassword.getText().clear();
                }
            }
        });


    }
}