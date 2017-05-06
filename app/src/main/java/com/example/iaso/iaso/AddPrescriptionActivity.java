package com.example.iaso.iaso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.iaso.iaso.core.model.MedicineResponse;
import com.example.iaso.iaso.network.async.AddPrescriptionTask;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class AddPrescriptionActivity extends AppCompatActivity {

    private Button createButton;
    private EditText enterName;
    private EditText enterDescrip;
    private EditText enterDosage;
    private EditText enterNumTimesTake;
    private EditText enterInstructions;
    private EditText enterTimes;
    private EditText enterMainUse;
    private Spinner dosages;


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
        enterName = (EditText)findViewById(R.id.enter_name);
        enterDescrip= (EditText)findViewById(R.id.enter_descrip);
        enterDosage = (EditText)findViewById(R.id.enter_dosage);
        enterNumTimesTake = (EditText)findViewById(R.id.num_take_day);
        enterInstructions = (EditText)findViewById(R.id.enter_instructions);
        enterTimes = (EditText)findViewById(R.id.enter_times);
        enterMainUse = (EditText)findViewById(R.id.enter_main_usage);
        createButton = (Button)findViewById(R.id.create_button);
        //dosages = (Spinner)findViewById(R.id.dosage_spinner);


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click the create button -> sends you back to home
                //add whatever data entered into text fields to db
                Log.d("ADDPRESCRIP", "Button has been clicked");
                String backToHome = "Going back to home!";
                AddPrescriptionTask task = new AddPrescriptionTask();
                task.setOnAddCallbackListener(new AddPrescriptionTask.OnAddCallbackListener() {
                    @Override
                    public void onCallBack(MedicineResponse response) {
                        //do nothing?
                    }
                });
                task.execute(enterName.getText().toString(), enterDescrip.getText().toString(), enterDosage.getText().toString(),
                        enterNumTimesTake.getText().toString(), enterInstructions.getText().toString(), enterTimes.getText().toString(),
                        enterMainUse.getText().toString());
                Intent toHomeFromAdd = new Intent(AddPrescriptionActivity.this, UserAccountHome.class);
                toHomeFromAdd.putExtra("Success", backToHome);
                startActivity(toHomeFromAdd);
            }
        });


    }
}
