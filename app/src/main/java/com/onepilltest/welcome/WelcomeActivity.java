package com.onepilltest.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.SharedPreferencesUtil;
import com.onepilltest.util.StatusBarUtil;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout linearDoctor;
    private LinearLayout linearPatient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xff56ced4 );
//        }
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

        initBar(this);
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,false);

    }

    @Override
    public int intiLayout() {
        return R.layout.welcome_login;
    }

    //自动登陆
    private void autoLogin() {
        if (SharedPreferencesUtil.userExist(getApplicationContext())){
            SharedPreferencesUtil.initUserBook(getApplicationContext());
            Log.e("用户自动登陆：",""+UserBook.NowUser.toString());
            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        }else if (SharedPreferencesUtil.doctorExist(getApplicationContext())){
            SharedPreferencesUtil.initUserBook(getApplicationContext());
            Log.e("医生自动登陆：",""+UserBook.NowDoctor.toString());
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
