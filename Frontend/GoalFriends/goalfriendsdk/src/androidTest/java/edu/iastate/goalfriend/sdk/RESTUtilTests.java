package edu.iastate.goalfriend.sdk;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RESTUtilTests {
    @Test
    public void RESTUtilsPostTest() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        RESTUtils restUtils = new RESTUtils();

        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();

        JSONObject body = new JSONObject();

        try {
            body.put("email", "tjaacks@iastate.edu");
            body.put("username", "tjaacks");
            body.put("password", "password");
            body.put("phonenumber", "17128708993");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject response = restUtils.volleyRequest(
                appContext,
                Request.Method.POST,
                "http://localhost:8080/register",
                body,
                params,
                headers);

        try {
            Assert.assertEquals(200, response.get("status"));
            Assert.assertEquals("Successfully Register User.", response.get("response").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}