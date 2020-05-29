package com.onepilltest.welcome;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.CustomVideoView;
import com.onepilltest.util.SharedPreferencesUtil;
import com.onepilltest.util.StatusBarUtil;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout linearDoctor;
    private LinearLayout linearPatient;
    private CustomVideoView videoview;
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

        init();
        initView();

        /*if (UserBook.NowUser != null ){
            Log.e("是否存在用户",UserBook.NowUser.getNickName());
            startActivity(new Intent(WelcomeActivity.this, HomeFragment.class));

        }*/

        initBar(this);
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
//        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,false);

    }

    @Override
    public int intiLayout() {
        return R.layout.welcome_login;
    }

    //自动登陆
    private void autoLogin() {
        String phone = null;
        String password = null;
        if (SharedPreferencesUtil.userExist(getApplicationContext())){
            SharedPreferencesUtil.initUserBook(getApplicationContext());
            Log.e("用户自动登陆：","phone:"+UserBook.NowUser.getPhone()+"pass:"+UserBook.NowUser.getPassword());
            phone = UserBook.NowUser.getPhone();
            password = UserBook.NowUser.getPassword();
            EMClient.getInstance().login(phone, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.e("Welcome","环信登陆成功");
                    Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("Welcome","环信登陆失败");
                }

                @Override
                public void onProgress(int i, String s) {
                    Log.e("Welcome","环信登陆失败");
                }
            });


        }else if (SharedPreferencesUtil.doctorExist(getApplicationContext())){
            SharedPreferencesUtil.initUserBook(getApplicationContext());
            Log.e("医生自动登陆：","phone:"+UserBook.NowDoctor.getPhone()+"pass:"+UserBook.NowDoctor.getPassword());
            phone = UserBook.NowDoctor.getPhone();
            password = UserBook.NowDoctor.getPassword();
            EMClient.getInstance().login(phone, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.e("Welcome","环信登陆成功");
                    Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("Welcome","环信登陆失败");
                }

                @Override
                public void onProgress(int i, String s) {
                    Log.e("Welcome","环信登陆失败");
                }
            });

        }


    }


    /**
     * 初始化
     */
    private void initView() {


        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video3));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

        //设置音量，左右声道
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0,0);
            }
        });

    }

    private void init(){

        videoview = findViewById(R.id.welcome_video);
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

    //返回重启加载
    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        videoview.stopPlayback();
        super.onStop();
    }
}
