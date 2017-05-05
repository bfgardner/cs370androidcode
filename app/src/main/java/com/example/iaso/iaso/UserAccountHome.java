package com.example.iaso.iaso;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.iaso.iaso.adapter.RecyclerViewAdapter;
import com.example.iaso.iaso.core.model.Medicine;
import com.example.iaso.iaso.core.model.MedicineResponse;
import com.example.iaso.iaso.network.async.MedicineCallbackListener;
import com.example.iaso.iaso.network.async.MedicineListTask;

import java.util.ArrayList;
import java.util.Random;

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
        String magicalTokenOfDestiny = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I";
        Intent homeIntent = getIntent();
        String email = homeIntent.getExtras().getString("Success");
        if (email != null) {
            Log.d("USERACCOUNTHOME", "It worked. Everything is okay. Email is:" + email);
        } else {
            Log.d("USERACCOUNTHOME", "It didn't work. No email found");

        }

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

        String contextText =  "Stuff";//"Take " + medicineItems.get(0).getMed_name() + ", " + medicineItems.get(0).getNextDose();

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

}


