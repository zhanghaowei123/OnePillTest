package com.onepilltest.welcome;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onepilltest.R;

public class DoctorSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_success);
        new Handler().postDelayed(r, 2000);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(DoctorSuccessActivity.this, LoginDoctorActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
