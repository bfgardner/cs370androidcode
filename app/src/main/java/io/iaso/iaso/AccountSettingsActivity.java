package io.iaso.iaso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.auth.AuthenticatorActivity;

public class AccountSettingsActivity extends AppCompatActivity {

    private Button saveButton;
    private Button logoutButton;
    private TextView currentEmail;
    private TextView currentUsername;
    private TextView changeEmail;
    private TextView changePassword;
    private TextView changeUsername;
    private EditText enterEmail;
    private EditText enterUsername;
    private EditText enterOldPass;
    private EditText enterNewPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        currentEmail = (TextView)findViewById(R.id.current_email);
        changeEmail = (TextView)findViewById(R.id.change_email);
        enterEmail = (EditText)findViewById(R.id.enter_email);
        currentUsername = (TextView)findViewById(R.id.current_username);
        changeUsername = (TextView)findViewById(R.id.change_username);
        enterUsername = (EditText)findViewById(R.id.enter_username);
        changePassword = (TextView)findViewById(R.id.change_password);
        enterOldPass = (EditText)findViewById(R.id.enter_current_password);
        enterNewPass = (EditText)findViewById(R.id.enter_new_password);
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
                Intent logout =  new Intent(AccountSettingsActivity.this, AuthenticatorActivity.class);
                logout.putExtra("Success", logoutSuccess);
                startActivity(logout);
            }
        });


    }
}
