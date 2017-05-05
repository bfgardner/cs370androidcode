package com.example.iaso.iaso.viewholder;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.iaso.iaso.MedicineDetailActivity;
import com.example.iaso.iaso.R;
import com.example.iaso.iaso.UserAccountHome;
import com.example.iaso.iaso.core.model.Medicine;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView medicineNameTextView;
    private TextView medicineDosageTextView;
    private TextView medicineDetailTextView;
    private TextView medicineNextTimeTextView;
    String mainUse;
    String med_id;

    public RecyclerViewHolder(final View itemView) {
        super(itemView);

        medicineNameTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_name);
        medicineDetailTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_details);
        medicineNextTimeTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_next_dose);

        medicineNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medicineName = medicineNameTextView.getText().toString();
                String medicineDetail = medicineDetailTextView.getText().toString();
                String nextTime = medicineNextTimeTextView.getText().toString();
                Intent toMedicineView   = new Intent(itemView.getContext(), MedicineDetailActivity.class);
                toMedicineView.putExtra("Name", medicineName);
                toMedicineView.putExtra("Details", medicineDetail);
                toMedicineView.putExtra("Next", nextTime);
                toMedicineView.putExtra("MainUse", mainUse);
                toMedicineView.putExtra("ID", med_id);
                itemView.getContext().startActivity(toMedicineView);
            }
        });
    }

    public void bindView(Medicine item) {
        medicineNameTextView.setText(item.getMed_name());
        medicineDetailTextView.setText(item.getDescription());
        medicineNextTimeTextView.setText(item.getNextDose());
        mainUse = item.getMain_usage();
        med_id = item.getMedicine_id();

    }


}
