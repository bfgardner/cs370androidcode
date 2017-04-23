package com.example.iaso.iaso.network.async;

import android.os.AsyncTask;

import com.example.iaso.iaso.core.model.UserDataResponse;
import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class UserDataTask extends AsyncTask<String,String,UserDataResponse> {
    private OnUserDataCallbackListener listener;

    @Override
    protected UserDataResponse doInBackground(String... params) {
        String myParams = "";

        if(params.length > 0) {
            for(int i = 0; i < params.length; i++) {
                myParams = myParams + params[i] + "+";
            }
        }
        //none of this will work; gotta wait for the actual ApplicationInstance to be up with the key + id for the api and all that
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(ApplicationInstance.getInstance().getBaseApiUrl()).newBuilder();
        urlBuilder.addQueryParameter("_app_key", ApplicationInstance.getInstance().getApiKey());
        urlBuilder.addQueryParameter("_app_id", ApplicationInstance.getInstance().getAppId());
        urlBuilder.addQueryParameter("q", myParams);

        String url = urlBuilder.build().toString();

        Response response = null;

        try {
            response = client.newCall(request).execute();

            if (response != null) {
                Gson gson = new Gson();
                UserDataResponse userDataResponse = gson.fromJson(response.body().string(), UserDataResponse.class);

                return userDataResponse;
            }
        } catch (IOException e) {
            // do something with exception
        }

        return null;
    }

    @Override
    protected void onPostExecute(UserDataResponse response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnUserDataCallbackListener(OnUserDataCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnUserDataCallbackListener {
        void onCallBack(UserDataResponse response);
    }
}