package com.example.iaso.iaso.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.iaso.iaso.R;
import com.example.iaso.iaso.core.model.MedicineItem;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView medicineNameTextView;
    private TextView medicineDosageTextView;
    private TextView medicineDetailTextView;
    private TextView medicineNextTimeTextView;


    public RecyclerViewHolder(View itemView) {
        super(itemView);

        medicineNameTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_name);
    }

    public void bindView(MedicineItem item) {
        medicineNameTextView.setText(item.getMedicineName());
    }
}
