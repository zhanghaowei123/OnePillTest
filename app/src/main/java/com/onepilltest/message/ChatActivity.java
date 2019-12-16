package com.onepilltest.message;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.onepilltest.R;

//会话详情页面
public class ChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
//    private void initData() {
//        //创建一个会话的fragment
//        EaseChatFragment easeChatFragment = new EaseChatFragment();
//        //     toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
//        easeChatFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.container, easeChatFragment).commit();
//    }

}
