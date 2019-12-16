package com.onepilltest;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

import java.util.Iterator;
import java.util.List;

public class ECApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        EaseUI.getInstance().init(this, options);
//        EMClient.getInstance().setDebugMode(true);
        //EaseUI初始化
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(this, options);
        if (EaseUI.getInstance().init(this, options)) {
            Log.e("EaseUI", "初始化成功");
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
       //     EMClient.getInstance().setDebugMode(true);
            //EaseUI初始化成功之后再去调用注册消息监听的代码
        } else {
            Log.e("EaseUI", "初始化失败");
        }
    }
}
