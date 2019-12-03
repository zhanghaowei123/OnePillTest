package com.onepilltest.personal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.onepilltest.R;


public class switchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消顶部导航栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_switch);


    }

    public void back(View view){
        finish();
    }

}
