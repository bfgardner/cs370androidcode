package com.example.iaso.iaso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditMedicineActivity extends AppCompatActivity {

    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        saveButton = (Button)findViewById(R.id.save_med_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save changes, return to medicine detail
                String savedChanges = "Saved changes, back to medicine detail";
                Intent save_go_back = new Intent (EditMedicineActivity.this, MedicineDetailActivity.class);
                save_go_back.putExtra("Success", savedChanges);
                startActivity(save_go_back);
            }
        });

    }
}
