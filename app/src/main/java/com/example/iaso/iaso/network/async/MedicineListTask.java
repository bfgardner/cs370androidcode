package com.example.iaso.iaso.network.async;

import android.os.AsyncTask;

import com.example.iaso.iaso.core.model.MedicineResponse;
import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MedicineListTask extends AsyncTask<String,String,MedicineResponse> {
    private OnMedicineCallbackListener listener;

    @Override
    protected MedicineResponse doInBackground(String... params) {
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
                MedicineResponse medicineResponse = gson.fromJson(response.body().string(), MedicineResponse.class);

                return medicineResponse;
            }
        } catch (IOException e) {
            // do something with exception
        }

        return null;
    }

    @Override
    protected void onPostExecute(MedicineResponse response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnRecipeCallbackListener(OnMedicineCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnMedicineCallbackListener {
        void onCallBack(MedicineResponse response);
    }
}