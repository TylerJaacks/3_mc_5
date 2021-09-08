package edu.tjaacks.logindemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private enum LoginResultCode {
        LOGIN_SUCCESSFUL,
        LOGIN_FAILED_WRONG_EMAIL,
        LOGIN_FAILED_WRONG_PASSWORD,
        LOGIN_FAILED_ACCOUNT_DOES_NOT_EXIST,
        LOGIN_FAILED_UNKNOWN
    }

    private static final String LOGIN_REST_ENDPOINT = "http://localhost:8080/login";

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailAddressEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Log.d(getPackageName(), "Email: " + email);
            Log.d(getPackageName(), "Password: " + password);

        Toast toast = new Toast(getApplicationContext());

        LoginResultCode loginResultCode = login(email, password);

        if (loginResultCode.equals(LoginResultCode.LOGIN_SUCCESSFUL)) {
            // TODO: Navigate to successful login activity.
            toast.setText("Login Successful!");
        } else {
            switch (loginResultCode) {
                case LOGIN_FAILED_WRONG_EMAIL:
                    toast.setText("Login Failed: Wrong Email!");
                    break;
                case LOGIN_FAILED_WRONG_PASSWORD:
                    toast.setText("Login Failed: Wrong Password!");
                    break;
                case LOGIN_FAILED_ACCOUNT_DOES_NOT_EXIST:
                    toast.setText("Login Failed: Account Does Not Exist!");
                    break;
                case LOGIN_FAILED_UNKNOWN:
                    toast.setText("Login Failed: Unknown Reason");
                    break;
                default:
                    break;
            }
        }

        toast.show();
        });
    }

    private LoginResultCode login(String email, String password) {
        LoginResultCode loginResultCode = LoginResultCode.LOGIN_SUCCESSFUL;

        Map<String, String> loginJsonResults = loginRest(email, password);

        return loginResultCode;
    }

    private Map<String, String> loginRest(String email, String password) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            URL endpointURL = null;
            HttpURLConnection httpURLConnection = null;

            try {
                endpointURL = new URL(LOGIN_REST_ENDPOINT);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                if (endpointURL != null) {
                    httpURLConnection = (HttpURLConnection) endpointURL.openConnection();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpURLConnection != null) {
                    httpURLConnection.setRequestMethod("GET");

                    httpURLConnection.setRequestProperty("Accept", "application/json");
                    httpURLConnection.setRequestProperty("email", email);
                    httpURLConnection.setRequestProperty("password", password);

                    httpURLConnection.setDoOutput(true);
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            if (httpURLConnection != null) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;

                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    Log.d(getPackageName(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            handler.post(() -> {
                //UI Thread work here
            });
        });

        return null;
    }
}