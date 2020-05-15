package com.onepilltest.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.onepilltest.R;


public class changedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //取消顶部导航栏
//        if (getSupportActionBar()!=null){
//            getSupportActionBar().hide();
//        }
        setContentView(R.layout.activity_changed);

        Handler handler = new Handler();
        handler.postDelayed(new Thread(){
            @Override
            public void run() {
                Intent intent = new Intent(changedActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        },3000);
    }
}
