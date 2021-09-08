package me.austindart.experiment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> userPass = new HashMap();

    private Button loginButton;
    private Button registerButton;
    private EditText passwordTextBox;
    private EditText emailTextBox;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        passwordTextBox = findViewById(R.id.passwordTextBox);
        emailTextBox = findViewById(R.id.emailTextBox);
        resultTextView = findViewById(R.id.resultTextView);
        

        registerButtonListeners(loginButton, registerButton);
    }

    protected void registerButtonListeners(Button loginButton, Button registerButton){
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onLoginButtonClick();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRegisterButtonClick();
            }
        });
    }

    protected boolean onRegisterButtonClick(){
        String email = emailTextBox.getText().toString();
        if(email.length() < 5 && !email.contains("@") && !email.contains(".")){
            resultTextView.setText("Invalid email address!");
            return false;
        }
        String password = passwordTextBox.getText().toString();
        if(password.length() < 8){
            resultTextView.setText("Password must be at least 8 characters!");
            return false;
        }
        if(userPass.containsKey(email)){
            resultTextView.setText("That email is already in use!");
            return false;
        }
        resultTextView.setText("Successfully registered!");
        userPass.put(email, password);
        return true;
    }

    protected boolean onLoginButtonClick(){
        String email = emailTextBox.getText().toString();
        String password = passwordTextBox.getText().toString();
        if(userPass.containsKey(email)){
            if(password.equals(userPass.get(email))){
                resultTextView.setText("Successfully logged in!");
                return true;
            }else{
                resultTextView.setText("Incorrect password!");
                return false;
            }
        }else{
            resultTextView.setText("That email is not registered!");
            return false;
        }

    }
}