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
import io.iaso.iaso.core.model.NotificationPreferences;
import io.iaso.iaso.network.async.MedicineCallbackListener;
import io.iaso.iaso.network.async.MedicineListTask;
import io.iaso.iaso.network.async.NotificationResponseTask;

public class UserAccountHome extends AppCompatActivity {


    private RecyclerView UserAccountRecycler;
    private LinearLayoutManager UserAccountlayoutManager;


    private Button settingsButton;
    private CardView individualCard;
    private Button notificationButton;
    private MedicineCallbackListener medicineCallbackListener;
    private String initialNotification;


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
            // Pass this to the function after Auth because the notifications need a context.
            afterAuth(this);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        settingsButton = (Button) findViewById(R.id.settings_button);
        notificationButton = (Button) findViewById(R.id.notifications_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void scheduleNotification(Context context, String delay, String initialNotificationSetting, int notificationId, Medicine medicine) {

        String contentText =  medicine.getDescription();
        String title = medicine.getMed_name();
        Intent resIntent = new Intent(context, UserAccountHome.class); // intent to take you to once you click on the notification, I think.
        PendingIntent pIntent = PendingIntent.getActivity(context, notificationId, resIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.iaso_bottle)
                .setContentTitle(title)
                .setContentText(contentText)
                .setContentIntent(pIntent);

        Notification notification = nBuilder.build(); // builds notification

        Intent notificationIntent = new Intent(context, NotificationPublisher.class); // potentially misleading variable name. Not actually the intent for the notification
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long startTime = 0;
        if (initialNotificationSetting == null)
        {
            startTime = 0;
        }
        else if (initialNotificationSetting.equals(".25"))
        {
            startTime = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        }
        else if (initialNotificationSetting.equals(".50"))
        {
            startTime = AlarmManager.INTERVAL_HALF_HOUR;
        }
        else if (initialNotificationSetting.equals("1.0"))
        {
            startTime = AlarmManager.INTERVAL_HOUR;
        }
        else if (initialNotificationSetting.equals("2.0"))
        {
            startTime = AlarmManager.INTERVAL_HOUR + AlarmManager.INTERVAL_HOUR;
        }

        long notificationDelay = hoursToMillis(delay);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notificationDelay - startTime, AlarmManager.INTERVAL_DAY , pendingIntent);

    }

    /*
      This function takes a string as a parameter. This string should be a multiple of
      4, where each segment represents a time in a 24 hour clock. This function will parse
      that string and return an ArrayList that contains each time. This result will eventually get
      used by the notification scheduler in the function: afterAuth().
     */
    public ArrayList parseDosageTimes(Medicine medicine) {
        String dosageTimes = medicine.getDosage_times(); // times each dose gets taken
        Integer doseFrequency = medicine.getDoses_per_day(); // amount of doses per day
        ArrayList<String> times = new ArrayList<>();
        String s = "";
        for (int i = 0; i < dosageTimes.length(); i++)
        {
            s += dosageTimes.charAt(i);

            if (i % 4 == 3)
            {
                times.add(s);
                s = "";
            }
        }

        return times;
    }

    /*
       This function will take a string time. This parameter should be a string
       of length 4 that is a time on the 24 hour clock. This function will read this time
       and return a long that represents the the delay before the passed in time, after the
       current time that this function is called.
     */
    public long hoursToMillis(String time) {

        if (time.length() != 4)
        {   // If time is not a valid input, simply return the current time so things don't break.
            // This will NOT handle other special cases such as characters or spaces in the time string.
            return System.currentTimeMillis();
        }

        Log.d("Everything is fine", time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis()); // may not use at all
        //calendar.setTime(); // may use for later
        long hours = calendar.get(Calendar.HOUR_OF_DAY);
        long minutes = calendar.get(Calendar.MINUTE);
        long delayHours = Integer.valueOf(time.substring(0, 2)) - hours;
        long delayMinutes = Integer.valueOf(time.substring(2, 4)) - minutes;

        long difference = TimeUnit.HOURS.toMillis(delayHours) + TimeUnit.MINUTES.toMillis(delayMinutes);
        Log.d("current diff in millis", String.valueOf(difference));
        return difference;
    }


    protected void afterAuth(final Context context) {

            UserAccountRecycler = (RecyclerView) findViewById(R.id.recycler_view);
            UserAccountlayoutManager = new LinearLayoutManager(getBaseContext());
            UserAccountRecycler.setLayoutManager(UserAccountlayoutManager);
            //call to API, get medicine repsonse object

            NotificationResponseTask nTask = new NotificationResponseTask();
            nTask.setOnNotificationCallbackListener(new NotificationResponseTask.OnNotificationCallbackListener() {
                @Override
                public void onCallBack(NotificationPreferences response) {
                    if (response == null)
                    {
                        initialNotification = "0";
                    }
                    else
                    {
                        initialNotification += response.getInitial_notif();
                    }
                }
            });

            medicineCallbackListener = new MedicineCallbackListener() {
                @Override
                public void onMedicineCallback(MedicineResponse response) {
                    //medicineItems = response.getMedicines();
                    if (response != null ) {
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(response.getMedicines());
                        UserAccountRecycler.setAdapter(adapter);
                        // loop to schedule notifications
                        for (int i = 0; i < response.getMedicines().size(); i++)
                        {
                            Integer medFrequency = response.getMedicines().get(i).getDoses_per_day();
                            ArrayList dosageTimes = parseDosageTimes(response.getMedicines().get(i));
                            // loop to schedule notifications for medicines that have more than 1 reminder per today
                            for (int j = 0; j < medFrequency; j++)
                            {
                                if (dosageTimes.size() != 0) {
                                    // Context passed in from main activity onCreate().
                                    scheduleNotification(context, dosageTimes.get(j).toString(), initialNotification, i, response.getMedicines().get(i));
                                }
                            }
                        }
                    }
                }
            };
            MedicineListTask task = new MedicineListTask();

            task.setMedicineCallbackListener(medicineCallbackListener);
            task.execute();
    }

}

