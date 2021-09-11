package edu.tjaacks.logindemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    private enum LoginResultCode {
        LOGIN_SUCCESSFUL,
        LOGIN_FAILED_WRONG_EMAIL,
        LOGIN_FAILED_WRONG_PASSWORD,
        LOGIN_FAILED_ACCOUNT_DOES_NOT_EXIST,
        LOGIN_FAILED_UNKNOWN
    }

    private static final String LOGIN_REST_ENDPOINT = "http://10.8.197.128:8080/login";

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
        LoginResultCode loginResultCode = null;

        Map<String, String> loginJsonResults = loginRest(email, password);

        if (loginJsonResults.size() == 2) {
            loginResultCode = LoginResultCode.LOGIN_SUCCESSFUL;
        }
        else if (loginJsonResults.size() == 1) {
            // TODO: Detect the LoginResultCode error;
            loginResultCode = LoginResultCode.LOGIN_FAILED_UNKNOWN;
        }

        return loginResultCode;
    }

    private Map<String, String> loginRest(String email, String password) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        AtomicReference<String> responseLoginToken = new AtomicReference<>("");
        AtomicReference<String> responseLoginExpiration = new AtomicReference<>("");

        Map<String, String> responseMap = new HashMap<>();

        executor.execute(() -> {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest sr = new StringRequest(Request.Method.GET, LOGIN_REST_ENDPOINT,
                    response -> {
                        // TODO: Figure out Error from JSON Response.
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            responseLoginToken.set(jsonObject.getString("loginToken"));
                            responseLoginExpiration.set(jsonObject.getString("loginExpiration"));

                            Log.d(getPackageName(), "loginToken: " + responseLoginToken);
                            Log.d(getPackageName(), "loginExpiration: " + responseLoginExpiration);

                            responseMap.put("loginToken", responseLoginToken.get());
                            responseMap.put("loginExpiration", responseLoginExpiration.get());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Log.e("HttpClient", "error: " + error.toString())) {
                @Override
                protected Map<String,String> getParams(){
                    return new HashMap<>();
                }
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> params = new HashMap<>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    params.put("email",email);
                    params.put("password", password);
                    return params;
                }
            };

            queue.add(sr);
        });

        return responseMap;
    }
}