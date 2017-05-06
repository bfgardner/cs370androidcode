package io.iaso.iaso.UserAccountHome;

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
        String magicalTokenOfDestiny = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I";

        Intent intent = new Intent(this, AuthenticatorActivity.class);
        startActivity(intent);



    }

}


