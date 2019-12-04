package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepilltest.R;

/**
 * 个人_钱包页面
 */
public class WalletActivity extends AppCompatActivity {

    MyListener myListener = null;
    Button back = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout.wallet);
        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.wallet_back);
        back.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.wallet_back:
                    finish();
                    break;
            }
        }
    }
}
