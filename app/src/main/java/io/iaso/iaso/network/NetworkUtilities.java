package io.iaso.iaso.network;

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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;

import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.auth.AuthenticatorActivity;
import okhttp3.Authenticator;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.TlsVersion;

/**
 * Provides utility methods for communicating with the server.
 */
final public class NetworkUtilities {

    public static OkHttpClient client;
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
     * Base URL for the iaso api
     */
    public static final String BASE_URL = "https://api.iaso.io";
    /**
     * URI for authentication service
     */
    public static final String AUTH_URI = BASE_URL + "/auth";

    private String mCurrentToken;

    public NetworkUtilities() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();

        client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) {
                        Context context = ApplicationInstance.getInstance();

                        AccountManager accountManager = AccountManager.get(context);
                        Account[] accounts = accountManager.getAccountsByType("io.iaso.iaso.auth");
                        // No account, do not even try to authenticate
                        if (accounts.length == 0) {
                            Log.i(TAG, "... But we dont have any account yet, so I will just back off for now.");
                        }

                        Account account = accounts[0];

                        try {
                            mCurrentToken = accountManager.blockingGetAuthToken(account, "io.iaso.iaso.auth", false);

                            accountManager.invalidateAuthToken("io.iaso.iaso.auth", mCurrentToken);
                            mCurrentToken = accountManager.blockingGetAuthToken(account, "io.iaso.iaso.auth", false);

                            Log.i(TAG, "Install temporary auth token in request");

                            return response.request().newBuilder()
                                    .removeHeader("Authorization")
                                    .addHeader("Authorization", String.format("Bearer %s", mCurrentToken))
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
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        try {
                            if (mCurrentToken == null) {
                                return chain.proceed(chain.request());
                            }

                            Request request = chain.request();
                            Request authenticatedRequest = request.newBuilder()
                                    .removeHeader("Authorization")
                                    .addHeader("Authorization", String.format("Bearer %s", mCurrentToken))
                                    .build();

                            return chain.proceed(authenticatedRequest);

                        } catch (IOException e) {
                            Log.e(TAG, "IO Error");
                            return null;
                        }
                    }
                })
                .connectionSpecs(Collections.singletonList(spec))
                .build();
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
        OkHttpClient authClient = new OkHttpClient();

        try {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "access_token=" + MASTER_TOKEN);
            Request request = new Request.Builder()
                    .url(AUTH_URI)
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("authorization", Credentials.basic(username, password))
                    .addHeader("cache-control", "no-cache")
                    .build();

            Response response = authClient.newCall(request).execute();
            JSONObject jsonObj = new JSONObject(response.body().string());
            String temp = jsonObj.getString("token");
            return temp;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}