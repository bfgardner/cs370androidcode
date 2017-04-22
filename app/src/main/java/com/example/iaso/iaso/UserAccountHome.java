package com.example.iaso.iaso;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.iaso.iaso.adapter.RecyclerViewAdapter;
import com.example.iaso.iaso.core.model.MedicineItem;

import java.util.ArrayList;

public class UserAccountHome extends AppCompatActivity {


    private RecyclerView UserAccountRecycler;
    private LinearLayoutManager UserAccountlayoutManager;


    //private RecyclerView recyclerView;
    //private LinearLayoutManager
    private Button settingsButton;
    private CardView individualCard;
   // private Button medicineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_home2);

        Intent homeIntent = getIntent();
        String email = homeIntent.getExtras().getString("Success");
        if (email != null)
            Log.d("USERACCOUNTHOME", "It worked. Everything is okay. Email is:" + email);
        else
            Log.d("USERACCOUNTHOME", "It didn't work. No email found");

        UserAccountRecycler = (RecyclerView)findViewById(R.id.recycler_view);
        UserAccountlayoutManager = new LinearLayoutManager(getBaseContext());
        UserAccountRecycler.setLayoutManager(UserAccountlayoutManager);

        ArrayList<MedicineItem> items = new ArrayList<>();

        for(int i = 0; i < 5000; i++){
            items.add(new MedicineItem.Builder()
                    .name("Medicine  name" + " " + String.valueOf(i))
                    .build());
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items); //make list of items
        UserAccountRecycler.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        settingsButton=(Button)findViewById(R.id.settings_button);
       // medicineButton=(Button)findViewById(R.id.medicine_button);
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
/*
        medicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toMedinfoSuccess = "Going to medicine info";
                Intent toMedicineView   = new Intent(UserAccountHome.this, MedicineDetailActivity.class);
                toMedicineView.putExtra("Success", toMedinfoSuccess);
                startActivity(toMedicineView);
            }
        });*/
    }

}
