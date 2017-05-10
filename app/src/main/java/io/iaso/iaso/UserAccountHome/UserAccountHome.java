package io.iaso.iaso.UserAccountHome;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.iaso.iaso.AccountSettingsActivity;
import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.MedicineDetailActivity;
import io.iaso.iaso.NotificationPublisher.NotificationPublisher;
import io.iaso.iaso.NotificationSettingsActivity;
import io.iaso.iaso.Prescription.AddPrescriptionActivity;
import io.iaso.iaso.R;
import io.iaso.iaso.adapter.RecyclerViewAdapter;
import io.iaso.iaso.auth.*;
import io.iaso.iaso.core.model.Medicine;
import io.iaso.iaso.core.model.MedicineResponse;
import io.iaso.iaso.network.async.MedicineCallbackListener;
import io.iaso.iaso.network.async.MedicineListTask;

public class UserAccountHome extends AppCompatActivity {


    private RecyclerView UserAccountRecycler;
    private LinearLayoutManager UserAccountlayoutManager;


    private Button settingsButton;
    private CardView individualCard;
    private Button notificationButton;
    //private ArrayList<Medicine> medicineItems;
    private MedicineCallbackListener medicineCallbackListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_home2);

        Context context = ApplicationInstance.getInstance();

        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("io.iaso.iaso.auth");
        // No account, do not even try to authenticate
        if (accounts.length == 0) {
            Intent intent = new Intent(this, AuthenticatorActivity.class);
            startActivityForResult(intent, 1);
        } else {
            afterAuth();
        }

        /*ArrayList<Medicine> items = new ArrayList<>();
        Random randomNumGen = new Random (1000);
        int randomVal = 0;
        for (int i = 0; i < 100; i++) {
            randomVal = randomNumGen.nextInt();
            randomVal = Math.abs(randomVal);
            items.add(new Medicine.Builder()
                    .name("Medicine  name" + " " + String.valueOf(i))
                    .nextDose("Next Dose at " + String.valueOf((i + randomVal) % 12) + ":" + String.valueOf(randomVal % 6) + String.valueOf(i % 9))
                    .description("Here are some details about Medicine name" + " " + String.valueOf(i))
                    .build());
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
        UserAccountRecycler.setAdapter(adapter);*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        settingsButton = (Button) findViewById(R.id.settings_button);
        notificationButton = (Button) findViewById(R.id.notifications_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String home_success = "Coming from User Account Home";
                Intent homeToAdd = new Intent(UserAccountHome.this, AddPrescriptionActivity.class);
                homeToAdd.putExtra("Success", home_success);
                startActivity(homeToAdd);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSettingsSuccess = "Going to account settings";
                Intent toAccountSettings = new Intent(UserAccountHome.this, AccountSettingsActivity.class);
                toAccountSettings.putExtra("Success", toSettingsSuccess);
                startActivity(toAccountSettings);
            }
        });
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toNotifSettingsSuccess = "Going to notification settings";
                Intent toNotifSettings = new Intent(UserAccountHome.this, NotificationSettingsActivity.class);
                toNotifSettings.putExtra("Success", toNotifSettingsSuccess);
                startActivity(toNotifSettings);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String magicalTokenOfDestiny = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I";

        scheduleNotification(this, "2030", 1);

        UserAccountRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        UserAccountlayoutManager = new LinearLayoutManager(getBaseContext());
        UserAccountRecycler.setLayoutManager(UserAccountlayoutManager);
        //call to API, get medicine repsonse object
        medicineCallbackListener = new MedicineCallbackListener() {
            @Override
            public void onMedicineCallback(MedicineResponse response) {
                //medicineItems = response.getMedicines();
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(response.getMedicines());
                UserAccountRecycler.setAdapter(adapter);
            }
        };
        MedicineListTask task = new MedicineListTask();

        task.setMedicineCallbackListener(medicineCallbackListener);
        task.execute(magicalTokenOfDestiny); //magic token??
    }

    public void scheduleNotification(Context context, String delay, int notificationId) {

        String contentText =  "Stuff";//"Take " + medicineItems.get(0).getMed_name() + ", " + medicineItems.get(0).getNextDose();
        Intent resIntent = new Intent(context, UserAccountHome.class); // intent to take you to once you click on the notification, I think.
        PendingIntent pIntent = PendingIntent.getActivity(context, notificationId, resIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.iaso_bottle)
                .setContentTitle("Reminder, Take your Meds")
                .setContentText(contentText)
                .setContentIntent(pIntent);
        //.setAutoCancel(true); // sets up params for notification

        Notification notification = nBuilder.build(); // builds notification

        Intent notificationIntent = new Intent(context, NotificationPublisher.class); // potentially misleading variable name.. not actually the intent for the notification
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //calendar.add(Calendar.MINUTE, shpref.getInt("timeoutint", 30));

        long notificationDelay = hoursToMillis(delay);
        //long notificationDelay = hoursToMillis(delay); I'm Crazy lol
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, 0 , pendingIntent);

    }

    public ArrayList parseDosageTimes(Medicine medicine) {
        String dosageTimes = medicine.getDosage_times(); // times each dose gets taken
        Integer doseFrequency = medicine.getDoses_per_day(); // amount of doses per day
        ArrayList<String> times = new ArrayList<>();
        String s = " ";
        for (int i = 0; i < dosageTimes.length(); i++)
        {
            s += dosageTimes.charAt(i);

            if (i % 4 == 3)
            {
                times.add(s);
                s = " ";
            }
        }

        return times;
    }

    public long hoursToMillis(String time) {
        //long intTime = Integer.parseInt(time);
        Log.d("Everything is fine", time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis()); // may not use at all
        //calendar.setTime(); // may use for later
        long hours = calendar.get(Calendar.HOUR_OF_DAY);
        long minutes = calendar.get(Calendar.MINUTE);
        long delayHours = Integer.parseInt(time.substring(0, 2)) - hours;
        long delayMinutes = Integer.parseInt(time.substring(2, 4)) - minutes;

        long difference = TimeUnit.HOURS.toMillis(delayHours) + TimeUnit.MINUTES.toMillis(delayMinutes);
        Log.d("current diff in millis", String.valueOf(difference));
        return difference;
    }


    protected void afterAuth() {
            UserAccountRecycler = (RecyclerView) findViewById(R.id.recycler_view);
            UserAccountlayoutManager = new LinearLayoutManager(getBaseContext());
            UserAccountRecycler.setLayoutManager(UserAccountlayoutManager);
            //call to API, get medicine repsonse object
            medicineCallbackListener = new MedicineCallbackListener() {
                @Override
                public void onMedicineCallback(MedicineResponse response) {
                    //medicineItems = response.getMedicines();
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(response.getMedicines());
                    UserAccountRecycler.setAdapter(adapter);
                }
            };
            MedicineListTask task = new MedicineListTask();

            task.setMedicineCallbackListener(medicineCallbackListener);
            task.execute();
    }

}

