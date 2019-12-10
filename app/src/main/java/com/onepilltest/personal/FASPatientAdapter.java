package com.onepilltest.personal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onepilltest.R;
import com.onepilltest.entity.SavePatient;

import java.util.ArrayList;
import java.util.List;

public class FASPatientAdapter extends RecyclerView.Adapter {

    private List<SavePatient> patients = new ArrayList<>();
    private Context context;
    private int itemId;

    public FASPatientAdapter(Context context, List<SavePatient> patients, int itemId) {
        this.context = context;
        this.patients = patients;
        this.itemId = itemId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(itemId,viewGroup,false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyItemViewHolder itemViewHolder = (MyItemViewHolder) viewHolder;
        itemViewHolder.tvpatient.setText(patients.get(i).getName());
        itemViewHolder.tvtype.setText(patients.get(i).getType());
    }

    @Override
    public int getItemCount() {
        if (patients != null)
            return patients.size();
        return 0;
    }

    private class MyItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvpatient;
        public TextView tvtype;

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpatient = itemView.findViewById(R.id.fas_tv_patient);
            tvtype = itemView.findViewById(R.id.fas_tv_type);
        }
    }
}
