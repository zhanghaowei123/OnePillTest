package com.onepilltest.personal;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.QR;
import com.onepilltest.entity.ZxingMessage;

public class QR_codeActivity extends AppCompatActivity {

    Button back = null;
    ImageView userImg = null;
    TextView name = null;
    TextView address = null;
    MyListener myListener = null;
    ImageView img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xfff8f8f8);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.setting_qr_code);

        myListener = new MyListener();
        find();
        init();
    }

    private void find() {
        userImg = findViewById(R.id.setting_qr_code_userImg);
        name = findViewById(R.id.setting_qr_code_userName);
        address = findViewById(R.id.setting_qr_code_address);
        back = findViewById(R.id.setting_qr_code_back);
        back.setOnClickListener(myListener);
        img = findViewById(R.id.setting_qr_code_img);
        img.setOnClickListener(myListener);

    }

    public void init() {
        String str = "想加好友";
        if (UserBook.Code == 1) {
            name.setText(UserBook.NowDoctor.getName());
            address.setText(UserBook.NowDoctor.getAddress());
            str = UserBook.NowDoctor.getName() + str;
            Glide.with(this)
                    .load(Connect.BASE_URL + UserBook.NowDoctor.getHeadImg())
                    .into(userImg);
            //生成二维码
            ZxingMessage zxingMessage = new ZxingMessage();
            zxingMessage.setCode(UserBook.Code);
            zxingMessage.setJson(new Gson().toJson(UserBook.NowDoctor));
            img.setImageBitmap(QR.getQR(300, 300, new Gson().toJson(zxingMessage)));
        } else {
            name.setText(UserBook.NowUser.getNickName());
            address.setText(UserBook.NowUser.getAddress());
            str = UserBook.NowUser.getNickName() + str;
            Glide.with(this)
                    .load(Connect.BASE_URL + UserBook.NowUser.getHeadImg())
                    .into(userImg);

            //生成二维码
            ZxingMessage zxingMessage = new ZxingMessage();
            zxingMessage.setCode(UserBook.Code);
            zxingMessage.setJson(new Gson().toJson(UserBook.NowUser));
            img.setImageBitmap(QR.getQR(300, 300, new Gson().toJson(zxingMessage)));
        }
        //img.setImageBitmap(QR.getQR(300,300,str));
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_qr_code_back://用户地址
                    Intent address_intent = new Intent(QR_codeActivity.this, SettingActivity.class);
                    startActivity(address_intent);
                    finish();
                    break;
                case R.id.setting_qr_code_img:
                    if (UserBook.Code == 1) {
                        Toast.makeText(getApplicationContext(), UserBook.NowDoctor.getName() + "想和你做朋友", Toast.LENGTH_SHORT).show();
                    } else if (UserBook.Code == 2) {
                        Toast.makeText(getApplicationContext(), UserBook.NowUser.getNickName() + "想和你做朋友", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}