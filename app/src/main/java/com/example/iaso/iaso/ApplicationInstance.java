package com.example.iaso.iaso;

import android.app.Application;
import com.example.iaso.iaso.network.NetworkUtilities;

import okhttp3.OkHttpClient;

public class ApplicationInstance extends Application {
    private static ApplicationInstance instance;
    public static NetworkUtilities netUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initialize();
    }

    private void initialize() {
        netUtils = new NetworkUtilities();
    }

    public static ApplicationInstance getInstance() {
        return instance;
    }
    public static NetworkUtilities getNetUtils() {
        return netUtils;
    }


}