package com.hyphenate.easeui.demofile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.ui.VideoCallActivity;
import com.hyphenate.easeui.ui.VoiceCallActivity;
import com.hyphenate.util.EMLog;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!DemoHelper.getInstance().isLoggedIn())
            return;
        //username
        String from = intent.getStringExtra("from");
        //call type
        String type = intent.getStringExtra("type");
        if ("video".equals(type)) {//video call
            context.startActivity(new Intent(context, VideoCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            Log.e("videoCall", "这是视频电话");
        } else {//voice call
            context.startActivity(new Intent(context, VoiceCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            Log.e("voiceCall", "这是语音电话");
        }
        EMLog.d("CallReceiver", "app received a incoming call");
    }
}
