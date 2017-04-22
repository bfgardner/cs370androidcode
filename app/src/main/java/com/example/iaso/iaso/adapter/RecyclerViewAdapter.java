package com.example.iaso.iaso.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iaso.iaso.R;
import com.example.iaso.iaso.core.model.MedicineItem;
import com.example.iaso.iaso.viewholder.RecyclerViewHolder;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<MedicineItem> MedicineList;

    public RecyclerViewAdapter(ArrayList<MedicineItem> collection) {
        this.MedicineList = collection;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_user_account_home, parent, false);

        return new RecyclerViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        MedicineItem item = MedicineList.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return this.MedicineList.size();
    }

}