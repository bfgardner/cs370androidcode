package com.example.iaso.iaso.network.async;

import android.os.AsyncTask;

import com.example.iaso.iaso.UserAccountHome;
import com.example.iaso.iaso.core.model.Medicine;
import com.example.iaso.iaso.core.model.MedicineResponse;
import com.example.iaso.iaso.network.async.MedicineCallbackListener; //why is this unused??
import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
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

        OkHttpClient client = new OkHttpClient();
        //compose a lookup url?
        HttpUrl urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host("api.iaso.io")
                .addPathSegment("medicines")
                .addQueryParameter("access_token", params[0]) //assume this is the magical token of destiny?
                .build();


        String url = urlBuilder.toString();
        Request request = new Request.Builder().url(urlBuilder).build();
       // Response response = null;
        MedicineResponse medicineResponse = new MedicineResponse();
        try {
            Response response = client.newCall(request).execute();

            if (response != null) {
                String body = response.body().string();
                JSONArray jsonArray = new JSONArray(body);
                ArrayList<Medicine> meds = new ArrayList<Medicine>();
                //^I know this is super dumb and I should just add a method to medicineResponse to do this but I don't feel like breaking it
                //again until I get the async task to work
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Medicine medicine = new Medicine.Builder()
                            .name(jsonObject.getString("med_name"))
                            .dosage(jsonObject.getString("dosage"))
                            .description(jsonObject.getString("description"))
                            .dosesPerDay(jsonObject.getString("doses_per_day"))
                            .mainUsage(jsonObject.getString("main_usage"))
                            .doseTimes(jsonObject.getString("dosage_times"))
                            .instruct(jsonObject.getString("instructions"))
                            .build();
                    meds.add(medicine);
                }
                medicineResponse.setMedicines(meds);

                String key = "random line that I can set a break point on to check out the values";
            }
        } catch (IOException e) {
            e.printStackTrace();
           //return medicineResponse;
        } catch (Exception e) {
            e.printStackTrace();
            //return medicineResponse; //When the returns are uncommented here, the medicineResponse object isn't correct in the actual return
        }

        return medicineResponse;
    }

    @Override
    protected void onPostExecute(MedicineResponse response) {
        super.onPostExecute(response);//verify here??
        this.medicineCallbackListener.onMedicineCallback(response); //vrify here too
    }

    public void setMedicineCallbackListener(MedicineCallbackListener listener) {
        this.medicineCallbackListener = listener;
    }

}