package com.example.iaso.iaso;

import android.app.Application;

public class ApplicationInstance extends Application {
    private static ApplicationInstance instance;

    private ApplicationInstance() {}

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initialize();
    }

    private void initialize() {

    }

    public static ApplicationInstance getInstance() {
        return instance;
    }
}