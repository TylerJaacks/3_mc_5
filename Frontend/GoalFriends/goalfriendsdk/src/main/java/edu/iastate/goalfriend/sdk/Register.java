package edu.iastate.goalfriend.sdk;


import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register {
    public boolean RegisterUser(RESTContext context, User user) {
        RESTUtils restUtils = new RESTUtils();

        String baseUri = context.getServerIp();

        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();

        JSONObject body = new JSONObject();

        try {
            body.put("email", user.getEmail());
            body.put("username", user.getUsername());
            body.put("password", user.getPassword());
            body.put("phonenumber", user.getPhonenumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject response = restUtils.volleyRequest(
                        context.getApplicationContext(),
                        Request.Method.POST,
                        baseUri + "/register",
                        body,
                        params,
                        headers);

        // TODO: Check to see that the response was successful.

        return true;
    }
}
