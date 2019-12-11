package com.onepilltest.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.onepilltest.R;


public class switchActivity extends AppCompatActivity {
    MyListener myListener = new MyListener();
    com.onepilltest.others.RoundImageView headImg = null;
    TextView nickName = null;
    ImageButton button = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        find();

    }

    private void find() {
        headImg = findViewById(R.id.activity_switch_headImg);
        headImg.setOnClickListener(myListener);
        nickName = findViewById(R.id.activity_switch_nickName);
        nickName.setOnClickListener(myListener);
        nickName.setText(UserBook.NowUser.getNickName());
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
