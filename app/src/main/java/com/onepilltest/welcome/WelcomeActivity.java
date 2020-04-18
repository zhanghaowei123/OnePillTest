package com.onepilltest.welcome;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.onepilltest.R;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.index.HomeFragment;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.SharedPreferencesUtil;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearDoctor;
    private LinearLayout linearPatient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff56ced4 );
        }
        setContentView(R.layout.welcome_login);

        //初始化极光推送
        Log.e("Jpush:","初始化");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        autoLogin();

        initView();

        /*if (UserBook.NowUser != null ){
            Log.e("是否存在用户",UserBook.NowUser.getNickName());
            startActivity(new Intent(WelcomeActivity.this, HomeFragment.class));

        }*/
    }

    //自动登陆
    private void autoLogin() {
        if (SharedPreferencesUtil.userExist(getApplicationContext())){
            SharedPreferencesUtil.initUserBook(getApplicationContext());
            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }else if (SharedPreferencesUtil.doctorExist(getApplicationContext())){
            SharedPreferencesUtil.initUserBook(getApplicationContext());
            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void initView(){
        linearDoctor = findViewById(R.id.linear_doctor);
        linearPatient = findViewById(R.id.linear_patient);
        linearDoctor.setOnClickListener(this);
        linearPatient.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_doctor:
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,LoginDoctorActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_patient:
                Intent intent1 = new Intent();
                intent1.setClass(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
