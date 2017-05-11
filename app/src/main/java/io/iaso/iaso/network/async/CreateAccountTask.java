package io.iaso.iaso.network.async;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.core.model.UserData;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateAccountTask extends AsyncTask<String,String, Response> {
    private OnCreateAccountCallbackListener listener;

    OkHttpClient client = ApplicationInstance.getNetUtils().client;
    @Override
    protected Response doInBackground(String... params) {
        // Grab the username, password, and email
        String email = params[0];
        String password = params[1];
        String username = params[2];

        // Build the request
        OkHttpClient client = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("name", username)
                .add("access_token", "YTTZWldYhLCHN040k7dxM1tStJUa5efw")
                .build();

        Request request = new Request.Builder()
                .url("https://api.iaso.io/users")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();

            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnCreateAccountCallbackListener(OnCreateAccountCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnCreateAccountCallbackListener {
        void onCallBack(Response response);
    }
}
