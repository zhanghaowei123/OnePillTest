package com.onepilltest.personal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onepilltest.R;
import com.onepilltest.entity.SaveDoctor;

import java.util.ArrayList;
import java.util.List;

public class FASDoctorAdapter extends RecyclerView.Adapter {

    private List<SaveDoctor> doctors = new ArrayList<>();
    private Context context;
    private int itemId;

    public FASDoctorAdapter(Context context, List<SaveDoctor> doctors, int itemId) {
        this.context = context;
        this.doctors = doctors;
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
        itemViewHolder.tvdoctor.setText(doctors.get(i).getName());
        itemViewHolder.tvtype.setText(doctors.get(i).getType());
    }

    @Override
    public int getItemCount() {
        if (doctors != null)
            return doctors.size();
        return 0;
    }

    private class MyItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvdoctor;
        public TextView tvtype;

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdoctor = itemView.findViewById(R.id.fas_tv_doctor);
            tvtype = itemView.findViewById(R.id.fas_tv_type);
        }
    }
}
