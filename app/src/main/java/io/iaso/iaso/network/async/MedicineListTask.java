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
    //who knows what's going to happen....
    private String baseApiUrl = "api.iaso.io";
    private String apiKey = "test_user_access_token"; //will be taken care of elsewhere...soon....
    private String appId;

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
    /*public String getNextDose(String times){
        String nextDose;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        char c;
        ArrayList<TimeObject> timeObjectArrayList = null;
        for (int i = 0; i < times.length(); i += 4){
            TimeObject timeObject = new TimeObject();
            c = times.charAt(i);
            if (c == '0'){
                    c = times.charAt(i + 1);
                    timeObject.setHours(Character.getNumericValue(c));
            }
            else{
               String s = Character.toString(c) + (Character.toString(times.charAt(i + 1)));
                timeObject.setHours(Integer.valueOf(s));
            }
            //now minutes
            c = times.charAt(i + 2);
            if (c == '0'){
                c = times.charAt(i + 3);
                timeObject.setMinutes(Character.getNumericValue(c));
            }
            else{
                String s = Character.toString(c) + (Character.toString(times.charAt(i + 3)));
                timeObject.setMinutes(Integer.valueOf(s));
            }
            timeObjectArrayList.add((i % 4), timeObject);
        }
        //NOW, look at each object in timeObjectArrayList, see which one is closest to current time WOW
        TimeObject nextDoseTimeObject = timeObjectArrayList.get(0);
        for (int i = 1; i < timeObjectArrayList.size(); i++){
            if (nextDoseTimeObject.getHours() < hour){ //in case the default wasn't good
                nextDoseTimeObject = timeObjectArrayList.get(i);
            }
            else if(timeObjectArrayList.get(i).getHours() > hour){ //see if this one or the nextDoseTimeObject is better
                if (timeObjectArrayList.get(i).getMinutes() > min){
                    if (nextDoseTimeObject.getHours() >= timeObjectArrayList.get(i).getHours()){
                        if (nextDoseTimeObject.getMinutes() > timeObjectArrayList.get(i).getMinutes()){
                            nextDoseTimeObject = timeObjectArrayList.get(i);
                        }
                    }
                }
            }
        }
        String nextDoseMinutes="";
        String nextDoseHours="";
        if (nextDoseTimeObject.getHours() < 10){
            nextDoseHours += "0";
        }
        if (nextDoseTimeObject.getMinutes() < 10) {
            nextDoseMinutes += "0";
        }
        nextDoseHours += Integer.toString(nextDoseTimeObject.getHours());
        nextDoseMinutes += Integer.toString(nextDoseTimeObject.getMinutes());
        nextDose = nextDoseHours + nextDoseMinutes;
        return nextDose;
    }*/

}