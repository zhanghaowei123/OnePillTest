package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepilltest.R;

public class QR_codeActivity extends AppCompatActivity {

    Button back = null;
    MyListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout.setting_qr_code);

        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.setting_qr_code_back);
        back.setOnClickListener(myListener);

    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_qr_code_back://用户地址
                    Intent address_intent = new Intent(QR_codeActivity.this, SettingActivity.class);
                    startActivity(address_intent);
                    break;

            }
        }
    }
}