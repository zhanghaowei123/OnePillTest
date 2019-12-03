package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.onepilltest.R;
import com.onepilltest.message.QuestionActivity;

/**
 * 个人_设置页面
 */
public class SettingActivity extends AppCompatActivity {

    Button userAddress = null;//用户地址
    Button back = null;//返回键
    MyListener myListener = null;
    com.onepilltest.others.RoundImageView user_img = null;//头像
    TextView nickName = null;//昵称
    TextView degree = null;//身份
    ImageView QR_code = null;//二维码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout.setting);
        myListener = new MyListener();
        find();
    }

    private void find() {
        user_img = findViewById(R.id.setting_user_img);
        user_img.setOnClickListener(myListener);
        nickName = findViewById(R.id.setting_user_nickname);
        nickName.setOnClickListener(myListener);
        degree = findViewById(R.id.setting_user_degress);
        degree.setOnClickListener(myListener);
        QR_code = findViewById(R.id.setting_user_QR_code);
        QR_code.setOnClickListener(myListener);
        back = findViewById(R.id.setting_back);
        back.setOnClickListener(myListener);
        userAddress = findViewById(R.id.setting_user_address);
        userAddress.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_user_address://用户地址
                    Intent address_intent = new Intent(SettingActivity.this, AddressActivity.class);
                    startActivity(address_intent);
                    break;
                case R.id.setting_back://返回
                    finish();
                    break;
                case R.id.setting_user_img://用户头像
                    Intent user_img_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(user_img_intent);
                    break;
                case R.id.setting_user_nickname://昵称
                    Intent nickname_intent = new Intent(SettingActivity.this, changeNickActivity.class);
                    startActivity(nickname_intent);
                    break;
                case R.id.setting_user_degress://身份
                    Intent degree_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(degree_intent);
                    break;
                case R.id.setting_user_QR_code://二维码
                    Intent QR_code_intent = new Intent(SettingActivity.this, QR_codeActivity.class);
                    startActivity(QR_code_intent);
                    break;
            }
        }
    }

    public void JumpAccount(){
        Intent intent = new Intent(SettingActivity.this,switchActivity.class);
        startActivity(intent);
    }

}
