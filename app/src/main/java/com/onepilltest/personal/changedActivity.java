package com.onepilltest.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.util.StatusBarUtil;


public class changedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //取消顶部导航栏
//        if (getSupportActionBar()!=null){
//            getSupportActionBar().hide();
//        }
        setContentView(R.layout.activity_changed);

        Handler handler = new Handler();
        handler.postDelayed(new Thread(){
            @Override
            public void run() {
                Intent intent = new Intent(changedActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        },3000);

        initBar(this);

    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }


    @Override
    public int intiLayout() {
        return R.layout.activity_changed;
    }
}
