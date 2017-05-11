package io.iaso.iaso;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.auth.AuthenticatorActivity;
import io.iaso.iaso.core.model.UserData;
import io.iaso.iaso.network.NetworkUtilities;
import io.iaso.iaso.network.async.UserDataTask;
import io.iaso.iaso.network.async.UserUpdateTask;

public class AccountSettingsActivity extends AppCompatActivity {

    private Button saveButton;
    private Button logoutButton;
    //private TextView currentEmail;
    //private TextView currentUsername;
    private TextView changeEmail;
    private TextView changeUsername;
    private EditText enterEmail;
    private EditText enterUsername;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //currentEmail = (TextView)findViewById(R.id.current_email);
        changeEmail = (TextView)findViewById(R.id.change_email);
        enterEmail = (EditText)findViewById(R.id.enter_email);
        //currentUsername = (TextView)findViewById(R.id.current_username);
        changeUsername = (TextView)findViewById(R.id.change_username);
        enterUsername = (EditText)findViewById(R.id.enter_username);
        saveButton = (Button)findViewById(R.id.save_button);
        logoutButton = (Button)findViewById(R.id.logout_button);

        String magicalTokenOfDestiny = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I";

        UserDataTask task = new UserDataTask();
        task.setOnUserDataCallbackListener(new UserDataTask.OnUserDataCallbackListener() {
            @Override
            public void onCallBack(UserData response) {
                enterEmail.setText(response.getEmail());
                enterUsername.setText(response.getUsername());
                id = response.getID();
            }
        });

        task.execute(magicalTokenOfDestiny);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click save, go back to Home
                UserUpdateTask task = new UserUpdateTask();
                task.setOnUserUpdateCallbackListener(new UserUpdateTask.OnUserUpdateCallbackListener() {
                    @Override
                    public void onCallBack(UserData response) {
                        //do nothing?
                    }
                });
                task.execute(id, enterEmail.getText().toString(), enterUsername.getText().toString());
                Toast.makeText(AccountSettingsActivity.this, "Changes Saved.", Toast.LENGTH_LONG).show();
                String saveSettings = "Saved settings, back to home";
                Intent settingsToHome = new Intent(AccountSettingsActivity.this, UserAccountHome.class);
                settingsToHome.putExtra("Success", saveSettings);
                startActivity(settingsToHome);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Doing the work to remove the account
                Context context = ApplicationInstance.getInstance();

                AccountManager accountManager = AccountManager.get(context);
                Account[] accounts = accountManager.getAccountsByType("io.iaso.iaso.auth");
                Account account = accounts[0];

                accountManager.removeAccountExplicitly(account);
                ApplicationInstance.getNetUtils().mCurrentToken = null;

                //click logout, go to login page
                String logoutSuccess = "Logged out, back to login";
                Intent logout =  new Intent(AccountSettingsActivity.this, UserAccountHome.class);
                logout.putExtra("Success", logoutSuccess);
                startActivity(logout);
            }
        });


    }
}
