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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.SaveMedicine;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MedicineFragment extends Fragment{

    private List<SaveMedicine> medicines = new ArrayList<>();
    private OkHttpClient okHttpClient;
    private RecyclerView recyclerView;
    private FASMedicineAdapter medicineAdapter;

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
        SaveMedicine saveMedicine = new SaveMedicine("阿莫西林胶囊","20g");
        SaveMedicine saveMedicine1 = new SaveMedicine("藿香正气丸","30g");
        medicines.add(saveMedicine);
        medicines.add(saveMedicine1);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.fas_rv_patient);
        medicineAdapter = new FASMedicineAdapter(getContext(), medicines, R.layout.listview_faspatient);
        recyclerView.setAdapter(medicineAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
}
