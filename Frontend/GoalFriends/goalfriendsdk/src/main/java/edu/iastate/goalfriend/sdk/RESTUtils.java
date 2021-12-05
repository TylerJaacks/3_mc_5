package edu.iastate.goalfriend.sdk;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RESTUtils {
    public JSONObject volleyRequest(Context context, int httpMethod, String url, JSONObject requestBody, Map<String, String> params, Map<String, String> headers) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        AtomicReference<JSONObject> responseJsonObject = new AtomicReference<>(new JSONObject());

        int statusCode = -1;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpMethod, url, null, response -> {
            Log.d("goalfriend-sdk", "Response: " + response);

            responseJsonObject.set(response);
        }, error -> {
            Log.e("goalfriend-sdk", error.getLocalizedMessage());
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers.isEmpty()) {
                    return super.getHeaders();
                } else {
                    return headers;
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params.isEmpty()) {
                    return super.getParams();
                } else {
                    return params;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody()  {
                return requestBody  == null ? null : requestBody.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                Log.e("goalfriend-sdk", volleyError.getLocalizedMessage());

                return super.parseNetworkError(volleyError);
            }
        };

        requestQueue.add(jsonObjectRequest);

        return responseJsonObject.get();
    }
}
