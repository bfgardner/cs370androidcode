package io.iaso.iaso.network.async;

import android.os.AsyncTask;

import java.io.IOException;

import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.core.model.NotificationPreferences;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Brooke on 5/10/2017.
 */

public class NotificationUpdateTask extends AsyncTask<String, String, NotificationPreferences> {
    private OnNotificationUpdateCallbackListener listener;
    @Override
    protected NotificationPreferences doInBackground(String... params) {
        OkHttpClient client = ApplicationInstance.getNetUtils().client;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String body = "";
        if (params[1] != "") {
            body += "initial_notification=" + params[1];
        }
        if (params[2] != ""){
            if(body != ""){
                body += "&";
            }
            body += "additional_notification=" + params[2];
        }
        RequestBody requestBody = RequestBody.create(mediaType, body);

        Request request = new Request.Builder()
                .url("https://api.iaso.io/settings/" + params[0])// + "?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I")
                .put(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(NotificationPreferences response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnNotificationUpdateCallbackListener(OnNotificationUpdateCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnNotificationUpdateCallbackListener {
        void onCallBack(NotificationPreferences response);
    }
}
