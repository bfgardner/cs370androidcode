package com.example.iaso.iaso.network.async;

import android.os.AsyncTask;

import com.example.iaso.iaso.core.model.MedicineResponse;
import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MedicineListTask extends AsyncTask<String,String,MedicineResponse> {
    private OnMedicineCallbackListener listener;
    //who knows what's going to happen....
    private String baseApiUrl = "api.iaso.io";
    private String apiKey = "test_user_access_token"; //will be taken care of elsewhere...soon....
    private String appId;

    @Override
    protected MedicineResponse doInBackground(String... params) {
        //String myParams = "";
        //param = magic token??
        //if(params.length > 0) {
           // for(int i = 0; i < params.length; i++) {
             //   myParams = myParams + params[i] + "+";
            //}
        //}
        OkHttpClient client = new OkHttpClient();
        //compose a lookup url?
        HttpUrl urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host(baseApiUrl)
                .addQueryParameter("access_token", params[0]) //assume this is the magical token of destiny?
                .build();

        //HttpUrl.Builder urlBuilder = HttpUrl.parse(ApplicationInstance.getInstance().getBaseApiUrl()).newBuilder();
        //urlBuilder.addQueryParameter("_app_key", ApplicationInstance.getInstance().getApiKey());
        //urlBuilder.addQueryParameter("_app_id", ApplicationInstance.getInstance().getAppId());
        //urlBuilder.addQueryParameter("access_token", magicToken);

        String url = urlBuilder.toString();
        Request request = new Request.Builder().url(url).build();
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

    public void setOnMedicineCallbackListener(OnMedicineCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnMedicineCallbackListener {
        void onCallBack(MedicineResponse response);
    }
}