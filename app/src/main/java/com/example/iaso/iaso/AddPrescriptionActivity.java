package com.example.iaso.iaso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class AddPrescriptionActivity extends AppCompatActivity {

    private Button createButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        Intent addIntent = getIntent();
        String message = addIntent.getExtras().getString("Success");
        //debug lines :)
        if (message != null)
            Log.d("ADDPRESCRIP", "It worked. Everything is fine. Message is: " + message);
        else
            Log.d("ADDPRESCRIP", "It didn't work. Nothing is fine.");

        createButton = (Button)findViewById(R.id.create_button);
        backButton = (Button)findViewById(R.id.back_to_home);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click the create button -> sends you back to home
                //add whatever data entered into text fields to db
                Log.d("ADDPRESCRIP", "Button has been clicked");
                String backToHome = "Going back to home!";
                Intent toHomeFromAdd = new Intent(AddPrescriptionActivity.this, UserAccountHome.class);
                toHomeFromAdd.putExtra("Success", backToHome);
                startActivity(toHomeFromAdd);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //back button takes you back to home without saving any data
                //possible pop up warning message for unsaved data?
                String noSave = "Going back, no save!";
                Intent toHomeNoSave = new Intent(AddPrescriptionActivity.this, UserAccountHome.class);
                toHomeNoSave.putExtra("Success", toHomeNoSave);
                startActivity(toHomeNoSave);
            }
        });


    }
}
