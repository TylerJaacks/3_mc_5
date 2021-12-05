package edu.iastate.goalfriend.sdk;

import android.content.Context;

public class RESTContext {
    private String serverIp;
    private Context applicationContext;

    public RESTContext(String serverIp, Context applicationContext) {
        this.serverIp = serverIp;
        this.applicationContext = applicationContext;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
}
