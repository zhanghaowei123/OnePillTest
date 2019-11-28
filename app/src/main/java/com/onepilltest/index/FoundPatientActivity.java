package com.onepilltest.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.onepilltest.R;

public class FoundPatientActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText editSelect;
    private ImageView imgSelect;
    private LinearLayout linKid;
    private LinearLayout linOld;
    private LinearLayout linWomen;
    private LinearLayout linOther;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.found_patient_layout );
        imgBack = findViewById ( R.id.findpatient_left );
        imgSelect = findViewById ( R.id.img_findpa_select );
        editSelect = findViewById ( R.id.findpatient_select );
        imgBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
//                intent.setClass ( FoundPatientActivity.this, );
            }
        } );
        imgSelect.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

            }
        } );
    }
}
