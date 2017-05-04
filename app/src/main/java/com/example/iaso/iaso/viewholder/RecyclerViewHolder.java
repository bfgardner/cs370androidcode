package com.example.iaso.iaso.viewholder;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.iaso.iaso.MedicineDetailActivity;
import com.example.iaso.iaso.R;
import com.example.iaso.iaso.UserAccountHome;
import com.example.iaso.iaso.core.model.MedicineItem;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView medicineNameTextView;
    private TextView medicineDosageTextView;
    private TextView medicineDetailTextView;
    private TextView medicineNextTimeTextView;


    public RecyclerViewHolder(final View itemView) {
        super(itemView);

        medicineNameTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_name);
        medicineDetailTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_details);
        medicineNextTimeTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_next_dose);

        medicineNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toMedinfoSuccess = "Going to medicine info";
                Intent toMedicineView   = new Intent(itemView.getContext(), MedicineDetailActivity.class);
                toMedicineView.putExtra("Success", toMedinfoSuccess);
                itemView.getContext().startActivity(toMedicineView);
            }
        });
    }

    public void bindView(MedicineItem item) {
        medicineNameTextView.setText(item.getMedicineName());
        medicineDetailTextView.setText(item.getMedicineDetails());
        medicineNextTimeTextView.setText(item.getNextDose());
    }


}
