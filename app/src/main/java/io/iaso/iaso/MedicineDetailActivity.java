package io.iaso.iaso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.core.model.MedicineResponse;
import io.iaso.iaso.network.async.DeleteMedicineTask;

public class MedicineDetailActivity extends AppCompatActivity {

    private Button editButton;
    private TextView medicineName;
    private TextView medicineDetails;
    private TextView dosage;
    private TextView dosageTimes;
    private TextView instructions;
    private TextView nextDose;
    private TextView mainUsage;
    private TextView dosesPerDay;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);
        final Intent information = getIntent();
        String med_name = information.getExtras().getString("Name");
        String med_details = information.getExtras().getString("Details");
        String next_dose = information.getExtras().getString("Next");
        String purpose = information.getExtras().getString("MainUse");
        String doseAmount = information.getExtras().getString("DosageAmount");
        String doseTimes = information.getExtras().getString("DoseTimes");
        String instruct = information.getExtras().getString("Instructions");
        Integer numDoses = information.getExtras().getInt("NumDoses");
        editButton = (Button)findViewById(R.id.edit_medicine);
        deleteButton = (Button)findViewById(R.id.delete_medicine);
        medicineName = (TextView) findViewById(R.id.medicine_name);
        medicineName.setText(med_name);
        medicineDetails = (TextView)findViewById(R.id.medicine_detail);
        medicineDetails.setText(med_details);
        dosage = (TextView)findViewById(R.id.medicine_dosage);
        dosage.setText(doseAmount);
        dosageTimes = (TextView)findViewById(R.id.medicine_times);
        //parse string and get better version...
        //doseTimes = pretty_dose_times(doseTimes);
        dosageTimes.setText(doseTimes);
        instructions = (TextView)findViewById(R.id.medicine_instructions);
        instructions.setText(instruct);
        dosesPerDay = (TextView)findViewById(R.id.medicine_per_day);
        dosesPerDay.setText(Integer.toString(numDoses));
        nextDose = (TextView)findViewById(R.id.next_dose);
        nextDose.setText(next_dose);
        mainUsage = (TextView)findViewById(R.id.main_use);
        mainUsage.setText(purpose);




        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //pass same object that came in 
                Intent editMedicine = new Intent(MedicineDetailActivity.this, EditMedicineActivity.class);
                editMedicine.putExtra("Name", medicineName.getText());
                editMedicine.putExtra("Details", medicineDetails.getText());
                editMedicine.putExtra("Next", nextDose.getText());
                editMedicine.putExtra("MainUse", mainUsage.getText());
                editMedicine.putExtra("DosageAmount", dosage.getText());
                editMedicine.putExtra("Instructions", instructions.getText());
                editMedicine.putExtra("ID", information.getExtras().getString("ID"));
                startActivity(editMedicine);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteMedicineTask task = new DeleteMedicineTask();
                task.setOnDeleteCallbackListener(new DeleteMedicineTask.OnDeleteCallbackListener() {
                    @Override
                    public void onCallBack(MedicineResponse response) {
                        //do nothing?
                    }
                });
                task.execute(information.getExtras().getString("ID"));
                Toast.makeText(MedicineDetailActivity.this, "Medicine Deleted", Toast.LENGTH_LONG).show();
                Intent home = new Intent(MedicineDetailActivity.this, UserAccountHome.class);
                home.putExtra("success!", "more success!");
                startActivity(home);
            }
        });
    }
}

/*public String pretty_dose_times(String times){
    String temp = "";
    String subString;
    Integer hour;
    for (int i = 0; i < times.length(); i+=4){
        subString = times.substring(i,i+4);
        hour = Integer.parseInt(subString.substring(0,2));
        if (hour > 12){
            hour = hour - 12;
            temp = Integer.toString(hour);
            temp += ":";
            temp += times.substring(i+2,i+4);
            temp += " PM";
        }
        else if (hour == 0){
            hour = 12;
            temp = Integer.toString(hour);
            temp += ":";
            temp += times.substring(i+2,i+4);
            temp += " AM";
        }
        else{
            temp = Integer.toString(hour);
            temp += ":";
            temp += times.substring(i+2,i+4);
            temp += " AM";
        }
        temp += "   ";
    }
    return temp;
}}*/
