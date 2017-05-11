package io.iaso.iaso.network.async;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.core.model.NotificationPreferences;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationResponseTask extends AsyncTask<String,String,NotificationPreferences> {
    private OnNotificationCallbackListener listener;

    @Override
    protected NotificationPreferences doInBackground(String... params) {
        OkHttpClient client = ApplicationInstance.getNetUtils().client;

            //compose a lookup url?
            HttpUrl urlBuilder = new HttpUrl.Builder()
                    .scheme("https")
                    .host("api.iaso.io")
                    .addPathSegment("settings")
                    .build();


            String url = urlBuilder.toString();
            Request request = new Request.Builder().url(urlBuilder).build();
            NotificationPreferences notificationPreferences = new NotificationPreferences();


            try {
                Response response = client.newCall(request).execute();

                if (response != null) {
                    String body = response.body().string();
                    JSONArray jsonArray = new JSONArray(body);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    notificationPreferences.setAdditional_notif(jsonObject.getString("additional_notification"));
                    notificationPreferences.setInitial_notif(jsonObject.getString("initial_notification"));
                    notificationPreferences.setID(jsonObject.getString("id"));

                    //return notificationPreferences;
                }
            } catch (IOException e) {
                // do something with exception
            } catch (JSONException e) {
                e.printStackTrace();
            }


        return notificationPreferences;
    }

    @Override
    protected void onPostExecute(NotificationPreferences response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnNotificationCallbackListener(OnNotificationCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnNotificationCallbackListener {
        void onCallBack(NotificationPreferences response);
    }
}