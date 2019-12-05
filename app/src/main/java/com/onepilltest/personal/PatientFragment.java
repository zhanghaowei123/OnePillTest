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

public class PatientFragment extends Fragment{

    private List<SavePatient> patients = new ArrayList<>();
    private RecyclerView recyclerView;
    private FASPatientAdapter patientAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.focusandsave_patient, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        SavePatient savePatient = new SavePatient("阿莫西林胶囊","20g");
        SavePatient savePatient1 = new SavePatient("藿香正气丸","30g");
        patients.add(savePatient);
        patients.add(savePatient1);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.fas_rv_patient);
        patientAdapter = new FASPatientAdapter(getContext(), patients, R.layout.listview_faspatient);
        recyclerView.setAdapter(patientAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
}
