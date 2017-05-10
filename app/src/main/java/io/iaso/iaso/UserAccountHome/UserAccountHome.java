package io.iaso.iaso.UserAccountHome;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import io.iaso.iaso.AccountSettingsActivity;
import io.iaso.iaso.ApplicationInstance;
import io.iaso.iaso.MedicineDetailActivity;
import io.iaso.iaso.NotificationSettingsActivity;
import io.iaso.iaso.Prescription.AddPrescriptionActivity;
import io.iaso.iaso.R;
import io.iaso.iaso.adapter.RecyclerViewAdapter;
import io.iaso.iaso.auth.*;
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

        String contextText = "Stuff";//"Take " + medicineItems.get(0).getMed_name() + ", " + medicineItems.get(0).getNextDose();

        Intent resIntent = new Intent(this, MedicineDetailActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, resIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.iaso_bottle)
                .setContentTitle("Reminder, Take your Meds")
                .setContentText(contextText)
                .setContentIntent(pIntent)
                .build();


        NotificationManager notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notiManager.notify(0, notification);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        afterAuth();
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
                    if (response != null ) {
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(response.getMedicines());
                        UserAccountRecycler.setAdapter(adapter);
                    }
                }
            };
            MedicineListTask task = new MedicineListTask();

            task.setMedicineCallbackListener(medicineCallbackListener);
            task.execute();
    }
}

