package com.example.iaso.iaso.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.iaso.iaso.ApplicationInstance;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetUserList extends AsyncTask<String,String,String> {
    private OnUserListCallbackListener listener;
    private OkHttpClient client;

    @Override
    protected String doInBackground(String... params) {
        client = ApplicationInstance.getNetUtils().client;
        Request request = new Request.Builder()
                .url("http://api.iaso.io/users")
                .get()
                .build();

        Response response;

        try {
            response = client.newCall(request).execute();

            if (response != null) {
                String userResponse = response.toString();
                Log.d("GetUserList: ", userResponse);

                return userResponse;
            }
        } catch (IOException e) {
            // do something with exception
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnUserListCallbackListener(OnUserListCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnUserListCallbackListener {
        void onCallBack(String response);
    }
}