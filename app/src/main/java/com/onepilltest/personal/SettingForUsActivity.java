package com.onepilltest.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepilltest.R;

public class SettingForUsActivity extends AppCompatActivity {

    Button back = null;
    MyListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting__forus);

        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.setting_forUs_back);
        back.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_forUs_back:
                    finish();
                    break;
            }
        }
    }
}
