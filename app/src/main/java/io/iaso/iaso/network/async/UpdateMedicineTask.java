package io.iaso.iaso.network.async;

import android.os.AsyncTask;

import java.io.IOException;

import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.core.model.MedicineResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Brooke on 5/8/2017.
 */
public class UpdateMedicineTask extends AsyncTask<String, String, MedicineResponse> {

    private OnUpdateCallbackListener listener;
    @Override
    protected MedicineResponse doInBackground(String... params) {
        OkHttpClient client = ApplicationInstance.getNetUtils().client;
        //need some logic to calculate nextDose time
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String body = "";
        if (params[1] != params[2]){
            body += "med_name=" + params[1];
        }
        if (params[3] != params[4]){
            body += "&description=" + params[3];
        }
        if (params[5] != params[6]){
            body += "&main_usage=" + params[5];
        }
        if (params[7] != params[8]){
            body += "&dosage=" + params[7];
        }
        if (params[9] != params[10]){
            body += "&instructions=" + params[9];
        }
        if (body == ""){
            return null;
        }
        RequestBody requestBody = RequestBody.create(mediaType, body);

        Request request = new Request.Builder()
                .url("https://api.iaso.io/medicines/" + params[0] + "?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I")
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
    protected void onPostExecute(MedicineResponse response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnUpdateCallbackListener(OnUpdateCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnUpdateCallbackListener {
        void onCallBack(MedicineResponse response);
    }
}
