package io.iaso.iaso.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.iaso.iaso.R;
import io.iaso.iaso.UserAccountHome.UserAccountHome;

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

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click "Create Account Button", sends to User Account Home
                String create_success = "Created account, pass account info to home";
                Intent createToHome = new Intent(CreateAccountActivity.this, UserAccountHome.class);
                createToHome.putExtra("Success", create_success);
                startActivity(createToHome);
            }
        });
    }
}
