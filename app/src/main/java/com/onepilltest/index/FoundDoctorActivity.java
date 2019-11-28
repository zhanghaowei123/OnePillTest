package com.onepilltest.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.onepilltest.R;

public class FoundDoctorActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText editSelect;
    private ImageView imgSelect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.found_doctor_layout );
        imgBack = findViewById ( R.id.findoctor_left );
        editSelect =findViewById ( R.id.findoctor_select );
        imgSelect = findViewById ( R.id.img_select );
        imgBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
            }
        } );
    }
    public void buttonClick(View v){
        switch (v.getId ()){
            case R.id.tb_one:
                break;
            case R.id.tb_two:
                break;
            case R.id.tb_three:
                break;
            case R.id.tb_four:
                break;
            case R.id.tb_five:
                break;
            case R.id.tb_six:
                break;
            case R.id.tb_seven:
                break;
            case R.id.tb_eight:
                break;
            case R.id.tb_nine:
                break;
            case R.id.tb_ten:
                break;
            case R.id.tb_eleven:
                break;
            case R.id.tb_twelve:
                break;
        }
    }
}
