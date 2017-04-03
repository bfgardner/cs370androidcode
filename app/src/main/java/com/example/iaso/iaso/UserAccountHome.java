package com.example.iaso.iaso;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class UserAccountHome extends AppCompatActivity {

    //private RecyclerView recyclerView;
    //private LinearLayoutManager

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

        ///recyclerView = (RecyclerView)findViewById(R.);
        //--layoutManager = new LinearLayoutManager(getBaseContext());
        //recyclerView.setLayoutManager(--layoutManager);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
    }

}
