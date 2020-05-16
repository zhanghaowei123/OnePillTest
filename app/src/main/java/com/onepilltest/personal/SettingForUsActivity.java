package com.onepilltest.personal;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.util.StatusBarUtil;

public class SettingForUsActivity extends BaseActivity {

    Button back = null;
    MyListener myListener = null;
    /*Button ceshi = null;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xffffffff );
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.setting__forus);

        myListener = new MyListener();
        find();
//        ((ImageView) findViewById(R.id.ToastImg)).setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ahh));

        // 传入 Application 对象表示设置成全局的
//        new XToast(SettingForUsActivity.this)
//                .setView(R.layout.setting__forus)
//                // 设置成可拖拽的
//                //.setDraggable()
//                // 设置显示时长
//                .setDuration(1000)
//                // 设置动画样式
//                .setAnimStyle(android.R.style.Animation_Translucent)
//                .setImageDrawable(R.id.ToastImg, R.drawable.ahh)
//                .setText(android.R.id.message, "点我消失")
//                .setOnClickListener(android.R.id.message, new OnClickListener<TextView>() {
//
//                    @Override
//                    public void onClick(XToast toast, TextView view) {
//                        // 点击这个 View 后消失
//                        toast.cancel();
//                        // 跳转到某个Activity
//                        // toast.startActivity(intent);
//                    }
//                })
//                .show();

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
        return R.layout.setting__forus;
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
