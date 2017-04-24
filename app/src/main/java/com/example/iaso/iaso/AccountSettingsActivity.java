package com.example.iaso.iaso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountSettingsActivity extends AppCompatActivity {

    private Button saveButton;
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        saveButton = (Button)findViewById(R.id.save_button);
        logoutButton = (Button)findViewById(R.id.logout_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click save, go back to Home
                String saveSettings = "Saved settings, back to home";
                Intent settingsToHome = new Intent(AccountSettingsActivity.this, UserAccountHome.class);
                settingsToHome.putExtra("Success", saveSettings);
                startActivity(settingsToHome);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click logout, go to login page
                String logoutSuccess = "Logged out, back to login";
                Intent logout =  new Intent(AccountSettingsActivity.this, LoginActivity.class);
                logout.putExtra("Success", logoutSuccess);
                startActivity(logout);
            }
        });


    }
}
