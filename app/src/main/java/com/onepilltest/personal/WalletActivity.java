package com.onepilltest.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onepilltest.R;

/**
 * 个人_钱包页面
 */
public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();

        setContentView(R.layout.wallet);
    }
}
