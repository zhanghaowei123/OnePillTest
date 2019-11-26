package com.onepilltest.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PerfectInforDoctorActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText editName;
    private EditText editNum;
    private EditText editHosptal;
    private EditText editAddress;
    private ImageView imgPhoto;
    private ImageView imgPhotoback;
    private Button btnSucceed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.doctorinfo_layout );
        editName = findViewById ( R.id.perfect_doctor );
        editNum = findViewById ( R.id.perfect_dnum );
        editHosptal = findViewById ( R.id.perfect_hosptal );
        editAddress = findViewById ( R.id.perfect_address );
        imgBack = findViewById ( R.id.img_left_yd );
        imgPhoto = findViewById ( R.id.img_photo );
        imgPhotoback = findViewById ( R.id.img_photoback );;
        btnSucceed = findViewById ( R.id.btn_perfect_ds);

        imgBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                intent.setClass ( PerfectInforDoctorActivity.this,RegisterDoctor.class );
                startActivity ( intent );
            }
        } );
    }
}
