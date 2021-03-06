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
 * Created by Brooke on 4/30/2017.
 */

public class AddPrescriptionTask extends AsyncTask<String,String,MedicineResponse> {
    private OnAddCallbackListener listener;
    @Override
    protected MedicineResponse doInBackground(String... params) {

        OkHttpClient client = ApplicationInstance.getNetUtils().client;
        //need some logic to calculate nextDose time
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "med_name=" + params[0]
                + "&description=" + params[1]
                + "&dosage=" + params[2]
                + "&doses_per_day=" + params[3]
                + "&instructions=" + params[4]
                + "&dosage_times=" + params[5]
                + "&main_usage=" + params[6]
                + "&prev_dose_taken=" + "T");

        Request request = new Request.Builder()
                .url("https://api.iaso.io/medicines?")//access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I")
                .post(body)
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

    public void setOnAddCallbackListener(OnAddCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnAddCallbackListener {
        void onCallBack(MedicineResponse response);
    }
}
