package com.onepilltest.personal;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepilltest.R;

public class SettingForUsActivity extends AppCompatActivity {

    Button back = null;
    MyListener myListener = null;
    /*Button ceshi = null;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffffffff );
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.setting__forus);

        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.setting_forUs_back);
        back.setOnClickListener(myListener);
        /*ceshi = findViewById(R.id.thisisceshi);
        ceshi.setOnClickListener(myListener);*/
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_forUs_back:
                    finish();
                    break;
                /*case R.id.thisisceshi:
                    Intent intent = new Intent(SettingForUsActivity.this, thisIsCeShiActivity.class);
                    startActivity(intent);*/

            }
        }
    }
}
