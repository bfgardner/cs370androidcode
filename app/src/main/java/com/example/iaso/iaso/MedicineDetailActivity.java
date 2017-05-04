package com.example.iaso.iaso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MedicineDetailActivity extends AppCompatActivity {

    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        editButton = (Button)findViewById(R.id.edit_medicine);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String navSuccess = "Going to edit medicine";
                //pass same object that came in 
                Intent editMedicine = new Intent(MedicineDetailActivity.this, EditMedicineActivity.class);
                startActivity(editMedicine);
            }
        });
    }
}
