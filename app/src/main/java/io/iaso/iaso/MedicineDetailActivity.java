package io.iaso.iaso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.core.model.MedicineResponse;
import io.iaso.iaso.network.async.DeleteMedicineTask;

public class MedicineDetailActivity extends AppCompatActivity {

    private Button editButton;
    private TextView medicineName;
    private TextView medicineDetails;
    private TextView nextDose;
    private TextView mainUsage;
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
        editButton = (Button)findViewById(R.id.edit_medicine);
        deleteButton = (Button)findViewById(R.id.delete_medicine);
        medicineName = (TextView)findViewById(R.id.medicine_name);
        medicineName.setText(med_name);
        medicineDetails = (TextView)findViewById(R.id.medicine_detail);
        medicineDetails.setText(med_details);
        nextDose = (TextView)findViewById(R.id.next_dose);
        nextDose.setText(nextDose.getText().toString() + next_dose);
        mainUsage = (TextView)findViewById(R.id.main_use);
        mainUsage.setText(mainUsage.getText().toString() + purpose);



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String navSuccess = "Going to edit medicine";
                //pass same object that came in 
                Intent editMedicine = new Intent(MedicineDetailActivity.this, EditMedicineActivity.class);
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
                Intent home = new Intent(MedicineDetailActivity.this, UserAccountHome.class);
                home.putExtra("success!", "more success!");
                startActivity(home);
            }
        });
    }
}
