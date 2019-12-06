package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepilltest.R;

public class EditUserInfoActivity extends AppCompatActivity {

    MyListener myListener = null;
    Button back = null;
    Button save = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        //getSupportActionBar().hide();
        setContentView(R.layout._edit_user_info);
        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.edit_user_info_back);
        back.setOnClickListener(myListener);
        save = findViewById(R.id.edit_user_info_save);
        save.setOnClickListener(myListener);
    }


    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.edit_user_info_back:
                    Intent back_intent = new Intent(EditUserInfoActivity.this, SettingActivity.class);
                    startActivity(back_intent);
                    break;
                case R.id.edit_user_info_save://跳转到修改成功界面
                    Intent save_intent = new Intent(EditUserInfoActivity.this, SettingActivity.class);
                    startActivity(save_intent);
                    break;
            }
        }
    }
}
