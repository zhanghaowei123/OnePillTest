package com.onepilltest.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onepilltest.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorFragment extends Fragment{

    private List<SaveDoctor> doctors = new ArrayList<>();
    private RecyclerView recyclerView;
    private FASDoctorAdapter doctorAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.focusandsave_docter, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        SaveDoctor saveDoctor = new SaveDoctor("医生1","主治：儿科");
        SaveDoctor saveDoctor1 = new SaveDoctor("医生2","主治：妇科");
        doctors.add(saveDoctor);
        doctors.add(saveDoctor1);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.fas_rv_doctor);
        doctorAdapter = new FASDoctorAdapter(getContext(),doctors,R.layout.listview_fasdoctor);
        recyclerView.setAdapter(doctorAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
}
