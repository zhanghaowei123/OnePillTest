package com.onepilltest;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.demofile.CallReceiver;
import com.onepilltest.Ease.MyUserProvider;

public class ECApplication extends Application {
    private CallReceiver callReceiver;
    public static Context applicationContext;
    private static ECApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
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
            DemoHelper.getInstance().init(applicationContext);
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            //     EMClient.getInstance().setDebugMode(true);
            //EaseUI初始化成功之后再去调用注册消息监听的代码
            IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
            if (callReceiver == null) {
                callReceiver = new CallReceiver();
            }
            //register incoming call receiver
            getApplicationContext().registerReceiver(callReceiver, callFilter);
        } else {
            Log.e("EaseUI", "初始化失败");
        }

        //设置provider
        EaseUI.getInstance().setUserProfileProvider(MyUserProvider.getInstance());
    }
}
