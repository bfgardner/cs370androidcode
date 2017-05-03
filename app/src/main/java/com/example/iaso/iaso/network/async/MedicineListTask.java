package com.example.iaso.iaso.network.async;

import android.os.AsyncTask;

import com.example.iaso.iaso.UserAccountHome;
import com.example.iaso.iaso.core.model.Medicine;
import com.example.iaso.iaso.core.model.MedicineResponse;
import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MedicineListTask extends AsyncTask<String,String,MedicineResponse> {
    private MedicineCallbackListener medicineCallbackListener;
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
                .host("api.iaso.io")
                .addPathSegment("medicines")
                .addQueryParameter("access_token", params[0]) //assume this is the magical token of destiny?
                .build();

        //HttpUrl.Builder urlBuilder = HttpUrl.parse(ApplicationInstance.getInstance().getBaseApiUrl()).newBuilder();
        //urlBuilder.addQueryParameter("_app_key", ApplicationInstance.getInstance().getApiKey());
        //urlBuilder.addQueryParameter("_app_id", ApplicationInstance.getInstance().getAppId());
        //urlBuilder.addQueryParameter("access_token", magicToken);

        String url = urlBuilder.toString();
        Request request = new Request.Builder().url(urlBuilder).build();
        Response response = null;
        MedicineResponse medicineResponse = new MedicineResponse();
        try {
            response = client.newCall(request).execute();

            if (response != null) {
                String body = response.body().string();
                String actualBody = body.substring(1, body.length()-1);
                Gson gson = new Gson();
               // MedicineResponse medicineResponse = new MedicineResponse();
                JSONObject object = new JSONObject(actualBody);
                String key = "med_name";
                String value = object.getString(key);
                Medicine myMedicine = new Medicine.Builder()
                        .name(value)
                        .build();
                //medicineResponse.getMedicines().add(myMedicine);
                ArrayList<Medicine> meds = new ArrayList<Medicine>();
                meds.add(myMedicine);
                medicineResponse.setMedicines(meds);
                //Medicine medicine = gson.fromJson(response.body().toString(), Medicine.class);
              // medicineResponse = gson.fromJson(actualBody, MedicineResponse.class);
               // return medicineResponse;
            }
        } catch (IOException e) {
          // return medicineResponse;
        } catch (JSONException e) {
            e.printStackTrace();
            return medicineResponse;
        }

        return medicineResponse;
    }

    @Override
    protected void onPostExecute(MedicineResponse response) {
        super.onPostExecute(response);
        this.medicineCallbackListener.onMedicineCallback(response);
    }

    public void setMedicineCallbackListener(MedicineCallbackListener listener) {
        this.medicineCallbackListener = listener;
    }

   /* public interface OnMedicineCallbackListener {
        void onCallBack(MedicineResponse response);
    }*/
}