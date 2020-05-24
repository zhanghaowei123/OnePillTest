package com.onepilltest.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
}
