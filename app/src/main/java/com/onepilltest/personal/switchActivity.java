package com.onepilltest.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.util.StatusBarUtil;


public class switchActivity extends BaseActivity {
    MyListener myListener = new MyListener();
    ImageView headImg = null;
    TextView nickName = null;
    ImageButton button = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xfff8f8f8 );
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.activity_switch);
        find();
        init();

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
        return R.layout.activity_switch;
    }

    public void init(){
        if (UserBook.Code == 1){//医生
            initDoctor();
        }else if(UserBook.Code ==2){//用户
            initPatient();
        }
    }

    //初始化用户信息
    private void initPatient() {
        //头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(Connect.BASE_URL+UserBook.NowUser.getHeadImg())
                .apply(requestOptions)
                .into(headImg);
        nickName.setText(UserBook.NowUser.getPhone());
    }

    //初始化医生信息
    private void initDoctor() {
        //头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(Connect.BASE_URL+UserBook.NowDoctor.getHeadImg())
                .apply(requestOptions)
                .into(headImg);
        nickName.setText(UserBook.NowDoctor.getPhone());

    }

    private void find() {
        headImg = findViewById(R.id.activity_switch_headImg);
        headImg.setOnClickListener(myListener);
        nickName = findViewById(R.id.activity_switch_nickName);
        nickName.setOnClickListener(myListener);
        button = findViewById(R.id.activity_switch_button);
        button.setOnClickListener(myListener);
    }

    public void back(View view){
        finish();
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.activity_switch_headImg:
                    finish();
                    break;
                case R.id.activity_switch_button:
                    startActivity(new Intent(switchActivity.this,UserList.class));
                    finish();
                    break;
            }
        }
    }

}
