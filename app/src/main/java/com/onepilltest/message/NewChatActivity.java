package com.onepilltest.message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.onepilltest.R;

public class NewChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.newcontainer, chatFragment).commit();
    }
}
