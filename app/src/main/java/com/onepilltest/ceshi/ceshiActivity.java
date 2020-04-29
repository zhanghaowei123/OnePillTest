package com.onepilltest.ceshi;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.onepilltest.R;
import com.onepilltest.nearby.NearMap;

public class ceshiActivity extends AppCompatActivity {

    private Button fileUpload;
    MyListener myListener = new MyListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //状态栏样式
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffffffff );
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.ceshi);
        find();
    }


    public void find(){
        fileUpload = findViewById(R.id.file_upload);
        fileUpload.setOnClickListener(myListener);

    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.file_upload:
                    Toast.makeText(getApplicationContext(),"文件正在上传...",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ceshiActivity.this,NearMap.class);
                    startActivity(intent);

            }
        }
    }
}
