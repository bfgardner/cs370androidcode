package com.example.iaso.iaso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MedicineDetailActivity extends AppCompatActivity {

    private Button backButtonMed;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        backButtonMed = (Button) findViewById(R.id.medicine_detail_back);
        editButton = (Button)findViewById(R.id.edit_medicine);

        backButtonMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go back to home, replace this with hamburger menu navigation
                String backSuccess  = "Going back to home Wooooooo";
                Intent backToHome = new Intent(MedicineDetailActivity.this, UserAccountHome.class);
                backToHome.putExtra("Success", backSuccess);
                startActivity(backToHome);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String navSuccess = "Going to edit medicine";
                Intent editMedicine = new Intent(MedicineDetailActivity.this, EditMedicineActivity.class);
                startActivity(editMedicine);
            }
        });
    }
}
