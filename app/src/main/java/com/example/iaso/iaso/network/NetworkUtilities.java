package com.example.iaso.iaso.network;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import com.example.iaso.iaso.auth.AuthenticatorActivity;
import com.example.iaso.iaso.ApplicationInstance;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import android.accounts.AccountManager;
import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;

/**
 * Provides utility methods for communicating with the server.
 */
final public class NetworkUtilities {

    private final OkHttpClient.Builder client;
    private static final String MASTER_TOKEN = "YTTZWldYhLCHN040k7dxM1tStJUa5efw";
    /**
     * The tag used to log to adb console.
     */
    private static final String TAG = "NetworkUtilities";
    /**
     * POST parameter name for the user's account name
     */
    public static final String PARAM_USERNAME = "username";
    /**
     * POST parameter name for the user's password
     */
    public static final String PARAM_PASSWORD = "password";
    /**
     * POST parameter name for the user's authentication token
     */
    public static final String PARAM_AUTH_TOKEN = "authtoken";
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;
    /**
     * Base URL for the v2 Sample Sync Service
     */
    public static final String BASE_URL = "https://api.iaso.io";
    /**
     * URI for authentication service
     */
    public static final String AUTH_URI = BASE_URL + "/auth";

    private NetworkUtilities() {
        client = new OkHttpClient.Builder();

        client.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) {
                Context context = ApplicationInstance.getInstance();

                AccountManager accountManager = AccountManager.get(context);
                Account[] accounts = accountManager.getAccountsByType("com.example.iaso.iaso.auth");
                // No account, do not even try to authenticate
                if (accounts.length == 0) {
                    Log.i(TAG, "... But we dont have any account yet, so I will just back off for now.");
                    return null;
                }

                Account account = accounts[0];

                try {
                    final String mCurrentToken = accountManager.blockingGetAuthToken(account, "", false);

                    // For now, we just re-install blindly an interceptor
                    client.interceptors().clear();
                    Log.i(TAG, "... Installing interceptor after authentication");
                    client.interceptors().add(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request newReq = request.newBuilder()
                                    .addHeader("Authorization", mCurrentToken)
                                    .build();
                            Response response = chain.proceed(newReq);

                            return response;
                        }
                    });
                    Log.i(TAG, "Install temporary auth token in request");
                    return response.request().newBuilder()
                            .addHeader("Authorization", mCurrentToken)
                            .build();

                } catch (OperationCanceledException e) {
                    Log.e(TAG, "Interrupted exception");
                    return null;
                } catch (AuthenticatorException e) {
                    Log.e(TAG, "Authentication error");
                    return null;
                } catch (IOException e) {
                    Log.e(TAG, "IO Error");
                    return null;
                }
            }
        });
    }

    /**
     * Connects to the Iaso server, authenticates the provided
     * username and password.
     *
     * @param username The server account username
     * @param password The server account password
     * @return String The authentication token returned by the server (or null)
     */
    public static String authenticate(String username, String password) {
        OkHttpClient client = new OkHttpClient();

        try {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "access_token=" + MASTER_TOKEN);
            Request request = new Request.Builder()
                    .url("http://api.iaso.io/auth")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("authorization", Credentials.basic(username, password))
                    .addHeader("cache-control", "no-cache")
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().toString();
        } catch (IOException e) {
            return null;
        }
    }
}