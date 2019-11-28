package com.onepilltest.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.entity.UserPatient;

import okhttp3.OkHttpClient;

public class RegisterPatient extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPassword;
    private EditText editCheckwords;
    private ImageView imgNext;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_patient_layout);
        sharedPreferences = getSharedPreferences("patientRegister", MODE_PRIVATE);
        initView();

        imgNext.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(RegisterPatient.this, PerfectInforPatientActivity.class);
                        register();
                        startActivity(intent);
                    }
                }
        );
    }

    private void register() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", editPhone.getText().toString());
        editor.putString("password", editPassword.getText().toString());
        editor.commit();
    }

    private void initView() {
        editPhone = findViewById(R.id.edit_register_patient_phone);
        editPassword = findViewById(R.id.edit_register_patient_password);
        editCheckwords = findViewById(R.id.edit_register_patient_getcheckwords);
        imgNext = findViewById(R.id.patient_next);
    }

}
