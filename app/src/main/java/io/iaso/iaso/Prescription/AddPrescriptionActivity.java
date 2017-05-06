package io.iaso.iaso.Prescription;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.iaso.iaso.R;
import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.core.model.MedicineResponse;
import io.iaso.iaso.network.async.AddPrescriptionTask;

public class AddPrescriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button createButton;
    private EditText enterName;
    private EditText enterDescrip;
    private EditText enterDosage;
    private EditText enterInstructions;
    private EditText enterTimes;
    private EditText enterMainUse;
    private Spinner dosages;
    private Spinner dosageTimes;
    private Spinner am_pm;
    private Button addButton;
    private TextView numAdded;


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
        enterInstructions = (EditText)findViewById(R.id.enter_instructions);
        enterTimes = (EditText)findViewById(R.id.enter_times);
        enterMainUse = (EditText)findViewById(R.id.enter_main_usage);
        createButton = (Button)findViewById(R.id.create_button);
        dosages = (Spinner)findViewById(R.id.dosage_spinner);
        dosageTimes = (Spinner)findViewById(R.id.times_spinner);
        am_pm = (Spinner)findViewById(R.id.am_pm_spinner);
        numAdded = (TextView)findViewById(R.id.number_times_added);
        addButton = (Button) findViewById(R.id.add_time_button);
        //spinner click listener
        dosages.setOnItemSelectedListener(this);
        dosageTimes.setOnItemSelectedListener(this);
        am_pm.setOnItemSelectedListener(this);
        //spinner drop down
        List<String> items = new ArrayList<String>();
        for (Integer i = 1; i < 10; i++){
            items.add(i.toString());
        }
        List<String> times = new ArrayList<String>();
        String time;
        for (Integer i = 1; i < 13; i++){
            time = i.toString() + ":00";
            times.add(time);
        }
        List<String> time_of_day = new ArrayList<String>();
        time_of_day.add("AM");
        time_of_day.add("PM");
        //adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dosages.setAdapter(dataAdapter);

        ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, times);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dosageTimes.setAdapter(timesAdapter);

        ArrayAdapter<String> timeDayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time_of_day);
        timeDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        am_pm.setAdapter(timeDayAdapter);
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
                       dosages.getSelectedItem().toString(), enterInstructions.getText().toString(), enterTimes.getText().toString(),
                        enterMainUse.getText().toString());
                Toast.makeText(AddPrescriptionActivity.this, "Medicine Added", Toast.LENGTH_LONG).show();
                Intent toHomeFromAdd = new Intent(AddPrescriptionActivity.this, UserAccountHome.class);
                toHomeFromAdd.putExtra("Success", backToHome);
                startActivity(toHomeFromAdd);
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        //on selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        //showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0){
        //eh
    }
}
