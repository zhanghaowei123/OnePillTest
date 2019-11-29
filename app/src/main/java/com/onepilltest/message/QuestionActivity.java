package com.onepilltest.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.onepilltest.R;
import com.onepilltest.index.HomeFragment;

/**
 * 首页_快速问诊
 */
public class QuestionActivity extends AppCompatActivity {

    MyListener myListener = null;
    Button back = null;
    Button finish = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout.question);
        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.question_back);
        back.setOnClickListener(myListener);
        finish = findViewById(R.id.question_finish);
        finish.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.question_back:
                    finish();
                    break;
                case R.id.question_finish:
                    Intent finish = new Intent(QuestionActivity.this, HomeFragment.class);
                    break;
            }
        }
    }
}
