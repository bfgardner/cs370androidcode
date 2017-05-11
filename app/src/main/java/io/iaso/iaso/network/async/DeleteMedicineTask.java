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
 * Created by Brooke on 5/4/2017.
 */

public class DeleteMedicineTask extends AsyncTask <String, String, MedicineResponse> {

    private OnDeleteCallbackListener listener;
    @Override
    protected MedicineResponse doInBackground(String... params){
        OkHttpClient client = ApplicationInstance.getNetUtils().client;
        String id = params[0];
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.iaso.io/medicines/" + id)
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
