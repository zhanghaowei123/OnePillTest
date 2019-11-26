package com.onepilltest.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterPatient extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPassword;
    private EditText editCheckwords;
    private ImageView imgNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.register_patient_layout );
        editPhone = findViewById ( R.id.edit_register_patient_phone );
        editPassword = findViewById ( R.id.edit_register_patient_password );
        editCheckwords = findViewById (R.id.edit_register_patient_getcheckwords);
        imgNext = findViewById ( R.id.patient_next );
        imgNext.setOnClickListener (
                new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (  );
                        intent.setClass ( RegisterPatient.this,PerfectInforPatientActivity.class );
                        startActivity ( intent );
                    }
                }
        );
    }
}
