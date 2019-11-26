package com.onepilltest.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.onepilltest.R;

public class PerfectInforPatientActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText edituserName;
    private EditText edituserNum;
    private EditText edituserAddress;
    private Button btnSucceed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.patientinfo_layout );
        imgBack = findViewById ( R.id.img_left_user );
        edituserName =findViewById ( R.id.user_name );
        edituserNum = findViewById ( R.id.user_num );
        edituserAddress = findViewById ( R.id.user_address );
        btnSucceed = findViewById ( R.id.user_succeed );
        imgBack.setOnClickListener (
                new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (  );
                        intent.setClass ( PerfectInforPatientActivity.this,RegisterPatient.class);
                        startActivity ( intent );
                    }
                }
        );
    }
}
