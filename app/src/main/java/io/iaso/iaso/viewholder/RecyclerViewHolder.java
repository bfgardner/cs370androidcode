package io.iaso.iaso.viewholder;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.iaso.iaso.MedicineDetailActivity;
import io.iaso.iaso.R;
import io.iaso.iaso.core.model.Medicine;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView medicineNameTextView;
    //private TextView medicineDosageTextView;
    private TextView medicineDetailTextView;
    private TextView medicineNextTimeTextView;
    private TextView medicineTitle;
    private LinearLayout medicineCard;


    private String mainUse;
    private String med_id;
    private String dosages;
    private String instructions;
    private Integer dosesPerDay;
    private String dosage_amount;


    public RecyclerViewHolder(final View itemView) {
        super(itemView);

        medicineNameTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_name);
        medicineCard = (LinearLayout) itemView.findViewById(R.id.medicine_card);
        medicineDetailTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_details);
        medicineNextTimeTextView = (TextView)itemView.findViewById(R.id.recycler_medicine_next_dose);
        medicineTitle = (TextView)itemView.findViewById(R.id.recycler_medicine_title);

        medicineCard.setOnClickListener(new View.OnClickListener() {
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
                toMedicineView.putExtra("DoseTimes", dosages);
                toMedicineView.putExtra("Instructions", instructions);
                toMedicineView.putExtra("DosageAmount", dosage_amount);
                toMedicineView.putExtra("NumDoses", dosesPerDay);
                itemView.getContext().startActivity(toMedicineView);
            }
        });
    }

    public void bindView(Medicine item) {
        medicineNameTextView.setText(item.getMed_name());
        medicineDetailTextView.setText(item.getDescription());
        medicineNextTimeTextView.setText(item.getNextDose());
        medicineTitle.setText(item.getMed_name().charAt(0));
        mainUse = item.getMain_usage();
        med_id = item.getMedicine_id();
        dosages = item.getDosage_times();
        instructions = item.getInstructions();
        dosage_amount = item.getDosage();
        dosesPerDay = item.getDoses_per_day();

    }


}
