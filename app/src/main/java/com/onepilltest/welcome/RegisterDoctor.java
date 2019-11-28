package com.onepilltest.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.onepilltest.R;

public class RegisterDoctor extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPassword;
    private EditText editCheckword;
    private ImageView imgResponse;
    private SharedPreferences sharedPreferences;
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_doctor_layout);
        sharedPreferences = getSharedPreferences("doctorRegister", MODE_PRIVATE);
        findViews();
        imgResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterDoctor.this, PerfectInforDoctorActivity.class);
                registerDoctor();
                if (flag) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "请完善个人信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerDoctor() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", editPhone.getText().toString());
        editor.putString("password", editPassword.getText().toString());
        if (!editPhone.getText().toString().equals("")
                && !editPassword.getText().toString().equals("")) {
            flag = true;
            editor.commit();
        }
    }

    private void findViews() {
        editPhone = findViewById(R.id.edit_register_doctor_phone);
        editPassword = findViewById(R.id.edit_register_doctor_password);
        editCheckword = findViewById(R.id.edit_register_doctor_checkword);
        imgResponse = findViewById(R.id.doctor_next);
    }
}
