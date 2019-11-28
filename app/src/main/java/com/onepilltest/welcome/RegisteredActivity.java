package com.onepilltest.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.onepilltest.R;

public class RegisteredActivity extends AppCompatActivity {
    private ImageView imgLeft;
    private ImageView imgDoctor;
    private ImageView imgPatient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        //获取值
        imgLeft = findViewById(R.id.img_left);
        imgDoctor = findViewById(R.id.register_doctor);
        imgPatient = findViewById(R.id.register_patient);

        //静态跳转
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisteredActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        imgDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisteredActivity.this, RegisterDoctor.class);
                startActivity(intent);
            }
        });
        imgPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisteredActivity.this, RegisterPatient.class);
                startActivity(intent);
            }
        });
    }
}
