package com.onepilltest.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.onepilltest.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearDoctor;
    private LinearLayout linearPatient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_login);
        initView();
    }
    private void initView(){
        linearDoctor = findViewById(R.id.linear_doctor);
        linearPatient = findViewById(R.id.linear_patient);
        linearDoctor.setOnClickListener(this);
        linearPatient.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_doctor:
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,LoginDoctorActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_patient:
                Intent intent1 = new Intent();
                intent1.setClass(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}