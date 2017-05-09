package io.iaso.iaso.network.async;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.core.model.UserData;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserDataTask extends AsyncTask<String,String,UserData> {
    private OnUserDataCallbackListener listener;
    OkHttpClient client = ApplicationInstance.getNetUtils().client;
    @Override
    protected UserData doInBackground(String... params) {

        //compose a lookup url?
        HttpUrl urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host("api.iaso.io")
                .addPathSegment("users")
                .addPathSegment("me")
                .build();


        String url = urlBuilder.toString();
        Request request = new Request.Builder().url(urlBuilder).build();



        try {
            Response response = client.newCall(request).execute();

            if (response != null) {
                String body = response.body().string();
                JSONObject jsonObject = new JSONObject(body);
                UserData userDataResponse = new UserData();
                userDataResponse.setEmail(jsonObject.getString("email"));
                userDataResponse.setUsername(jsonObject.getString("name"));

                return userDataResponse;
            }
        } catch (IOException e) {
            // do something with exception
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(UserData response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnUserDataCallbackListener(OnUserDataCallbackListener listener) {
        this.listener = listener;
    }

   public interface OnUserDataCallbackListener {
        void onCallBack(UserData response);
    }
}
