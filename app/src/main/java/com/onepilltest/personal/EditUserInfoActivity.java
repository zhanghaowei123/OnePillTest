package com.onepilltest.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onepilltest.R;

public class EditUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout._edit_user_info);
    }
}
