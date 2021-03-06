package io.iaso.iaso;

import android.app.Application;
import android.content.Intent;

import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.auth.AuthenticatorActivity;
import io.iaso.iaso.network.NetworkUtilities;

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