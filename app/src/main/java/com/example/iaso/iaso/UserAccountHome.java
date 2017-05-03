package com.example.iaso.iaso;

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

import com.example.iaso.iaso.adapter.RecyclerViewAdapter;
import com.example.iaso.iaso.core.model.Medicine;
import com.example.iaso.iaso.core.model.MedicineResponse;
import com.example.iaso.iaso.network.async.MedicineCallbackListener;
import com.example.iaso.iaso.network.async.MedicineListTask;

import java.util.ArrayList;

public class UserAccountHome extends AppCompatActivity {


    private RecyclerView UserAccountRecycler;
    private LinearLayoutManager UserAccountlayoutManager;


    private Button settingsButton;
    private CardView individualCard;
    private Button notificationButton;
    private ArrayList<Medicine> medicineItems;
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
        //call to API, get medicine repsonse object
        //deserialize into medicineResponse object ->access?
        medicineCallbackListener = new MedicineCallbackListener() {
            @Override
            public void onMedicineCallback(MedicineResponse response) {
                for (int i = 0; i < response.getMedicines().size(); i++) {
                    medicineItems.add(new Medicine.Builder()
                            .name("Name: " + response.getMedicines().get(i).getMed_name())
                            //etc etc for the rest of the medicine object?
                            .build());
                }
            }
        };
        MedicineListTask task = new MedicineListTask();
        // ^"GOOD" CODE UP HERE
            //task.execute(magicalTokenOfDestiny);
        /*task.setOnMedicineCallbackListener(new MedicineListTask.OnMedicineCallbackListener() {
            @Override
            public void onCallBack(MedicineResponse response)
            {
                for (int i = 0; i < response.getMedicines().size(); i++){
                   medicineItems.add(new Medicine.Builder()
                           .name("Name: "+ response.getMedicines().get(i).getMed_name())
                        //etc etc for the rest of the medicine object?
                            .build());
                }
            }
        });*/
        //BELOW IS NEEDED
        task.setMedicineCallbackListener(medicineCallbackListener);
        task.execute(magicalTokenOfDestiny); //magic token??

        UserAccountRecycler =(RecyclerView) findViewById(R.id.recycler_view);
        UserAccountlayoutManager =new LinearLayoutManager(getBaseContext());
        UserAccountRecycler.setLayoutManager(UserAccountlayoutManager);

            //don't need this part....
       /*ArrayList<Medicine> items = new ArrayList<>();

        //need number of medicines from the API, use that as a list indexer instead of 5000
        for(int i = 0; i < 5000; i++) {
            items.add(new Medicine.Builder() //make items based off of the medicine response object
                    .name("Medicine  name" + " " + String.valueOf(i))
                    //all the data we want to display...?
                    .build());
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);*/
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(medicineItems);
        UserAccountRecycler.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        settingsButton=(Button) findViewById(R.id.settings_button);
        notificationButton=(Button) findViewById(R.id.notifications_button);

        fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View view){
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String home_success = "Coming from User Account Home";
                Intent homeToAdd = new Intent(UserAccountHome.this, AddPrescriptionActivity.class);
                homeToAdd.putExtra("Success", home_success);
                startActivity(homeToAdd);
            }
            });
       settingsButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View view){
                String toSettingsSuccess = "Going to account settings";
                Intent toAccountSettings = new Intent(UserAccountHome.this, AccountSettingsActivity.class);
                toAccountSettings.putExtra("Success", toSettingsSuccess);
                startActivity(toAccountSettings);
            }
            });
        notificationButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View view){
                String toNotifSettingsSuccess = "Going to notification settings";
                Intent toNotifSettings = new Intent(UserAccountHome.this, NotificationSettingsActivity.class);
                toNotifSettings.putExtra("Success", toNotifSettingsSuccess);
                startActivity(toNotifSettings);
            }
            });
        }
    }

