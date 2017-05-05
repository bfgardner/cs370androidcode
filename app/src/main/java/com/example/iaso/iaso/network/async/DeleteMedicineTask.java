package com.example.iaso.iaso.network.async;

import android.os.AsyncTask;

import com.example.iaso.iaso.core.model.MedicineResponse;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Brooke on 5/4/2017.
 */

public class DeleteMedicineTask extends AsyncTask <String, String, MedicineResponse> {

    private OnDeleteCallbackListener listener;
    @Override
    protected MedicineResponse doInBackground(String... params){
        OkHttpClient client = new OkHttpClient();
        String id = params[0];
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.iaso.io/medicines/" + id + "?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I")
                .delete()
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

        public void setOnDeleteCallbackListener(OnDeleteCallbackListener listener) {
            this.listener = listener;
        }

        public interface OnDeleteCallbackListener {
            void onCallBack(MedicineResponse response);
        }
}
