package io.iaso.iaso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.core.model.MedicineResponse;
import io.iaso.iaso.network.async.AddPrescriptionTask;
import io.iaso.iaso.network.async.UpdateMedicineTask;

public class EditMedicineActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText enterName;
    private EditText enterDetails;
    private EditText enterDosage;
    private EditText enterInstructions;
    private EditText enterPurpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        final Intent information = getIntent();
        final String med_name = information.getExtras().getString("Name");
        final String med_details = information.getExtras().getString("Details");
        final String next_dose = information.getExtras().getString("Next");
        final String purpose = information.getExtras().getString("MainUse");
        final String dosage = information.getExtras().getString("DosageAmount");
        final String instructions = information.getExtras().getString("Instructions");

        saveButton = (Button)findViewById(R.id.save_med_button);
        enterName = (EditText)findViewById(R.id.edit_name);
        enterDetails = (EditText)findViewById(R.id.edit_details);
        enterPurpose = (EditText)findViewById(R.id.edit_purpose);
        enterDosage = (EditText)findViewById(R.id.edit_dosage);
        enterInstructions = (EditText)findViewById(R.id.edit_instructions);

        enterName.setText(med_name);
        enterDetails.setText(med_details);
        enterPurpose.setText(purpose);
        enterDosage.setText(dosage);
        enterInstructions.setText(instructions);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save changes, return to medicine detail
                String savedChanges = "Saved changes, back to medicine detail";
                //send the entered information back to the API, return the information for this medicine again + pass through

                UpdateMedicineTask task = new UpdateMedicineTask();
                task.setOnUpdateCallbackListener(new UpdateMedicineTask.OnUpdateCallbackListener() {
                    @Override
                    public void onCallBack(MedicineResponse response) {
                        //do nothing?
                    }
                });
                task.execute(information.getExtras().getString("ID"), enterName.getText().toString(), med_name, enterDetails.getText().toString(),
                        med_details, enterPurpose.getText().toString(), purpose, enterDosage.getText().toString(), dosage, enterInstructions.getText().toString(), instructions);
                Intent save_go_back = new Intent (EditMedicineActivity.this, UserAccountHome.class);
                save_go_back.putExtra("Success", savedChanges);
                startActivity(save_go_back);
            }
        });

    }
}
