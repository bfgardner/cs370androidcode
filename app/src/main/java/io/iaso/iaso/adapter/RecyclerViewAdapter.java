package io.iaso.iaso.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.iaso.iaso.R;
import io.iaso.iaso.core.model.Medicine;
import io.iaso.iaso.viewholder.RecyclerViewHolder;

//import com.iaso.iaso.iaso.core.model.MedicineItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {


    private ArrayList<Medicine> medicines;

    public RecyclerViewAdapter(ArrayList<Medicine> collection) {
        this.medicines = collection;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_user_account_home, parent, false);

        return new RecyclerViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Medicine item = medicines.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return this.medicines.size();
    }

}
