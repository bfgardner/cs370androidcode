package io.iaso.iaso.auth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.iaso.iaso.Prescription.AddPrescriptionActivity;
import io.iaso.iaso.R;
import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.network.async.CreateAccountTask;
import okhttp3.Response;

public class CreateAccountActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText createEmail;
    private EditText createPassword;
    private EditText createUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAccountButton = (Button)findViewById(R.id.create_account_button);

        createEmail = (EditText) findViewById(R.id.email);
        createPassword = (EditText) findViewById(R.id.password);
        createUserName = (EditText) findViewById(R.id.username);

        final Context localContext = this;

        final CreateAccountTask.OnCreateAccountCallbackListener createStatus = new CreateAccountTask.OnCreateAccountCallbackListener() {
            @Override
            public void onCallBack(Response res) {
                if (!res.isSuccessful()) {
                    // Parse messages into something useful
                    String failureMessage = "";
                    if (res.message().equals("Conflict")) {
                        failureMessage = "Email or username is already in user.";
                    } else if (res.message().equals("Bad Request")) {
                        failureMessage = "Email or password was incorrectly formatted.";
                    }

                    // Let the user know we failed and why
                    new AlertDialog.Builder(localContext)
                            .setTitle("Failed to Create Account")
                            .setMessage(failureMessage)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    // Let the user know we succeeded and go back to login screen
                    Toast.makeText(localContext, "Account Creation Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CreateAccountTask newAccountTask = new CreateAccountTask();
                newAccountTask.setOnCreateAccountCallbackListener(createStatus);

                newAccountTask.execute(createEmail.getText().toString(),
                        createPassword.getText().toString(),
                        createUserName.getText().toString());
            }
        });
    }
}
