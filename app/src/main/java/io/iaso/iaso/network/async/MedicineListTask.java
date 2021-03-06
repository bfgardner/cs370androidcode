package io.iaso.iaso.network.async;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.core.model.Medicine;
import io.iaso.iaso.core.model.MedicineResponse;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;

public class MedicineListTask extends AsyncTask<String,String,MedicineResponse> {
    private MedicineCallbackListener medicineCallbackListener;

    @Override
    protected MedicineResponse doInBackground(String... params) {
        OkHttpClient client = ApplicationInstance.getNetUtils().client;

        //compose a lookup url?
        HttpUrl urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host("api.iaso.io")
                .addPathSegment("medicines")
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
                    //String next_dose = getNextDose(jsonObject.getString("dosage_times"));
                    Medicine medicine = new Medicine.Builder()
                            .name(jsonObject.getString("med_name"))
                            .dosage(jsonObject.getString("dosage"))
                            .description(jsonObject.getString("description"))
                            .dosesPerDay(jsonObject.getString("doses_per_day"))
                            .mainUsage(jsonObject.getString("main_usage"))
                            .doseTimes(jsonObject.getString("dosage_times"))
                            .instruct(jsonObject.getString("instructions"))
                            .identify(jsonObject.getString("id"))
                            //.nextDose(next_dose)
                            .build();
                    meds.add(medicine);
                }
                medicineResponse.setMedicines(meds);

                String key = "random line that I can set a break point on to check out the values";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null; //When the returns are uncommented here, the medicineResponse object isn't correct in the actual return
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