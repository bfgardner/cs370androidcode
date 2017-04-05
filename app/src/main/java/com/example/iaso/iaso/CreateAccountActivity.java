package com.example.iaso.iaso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccountActivity extends AppCompatActivity {

    private Button createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAccountButton = (Button)findViewById(R.id.create_account_button);
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
