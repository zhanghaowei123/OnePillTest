package com.onepilltest.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.onepilltest.R;

public class HelpAndFeedBackActivity extends AppCompatActivity{

    private ImageView ivback;
    private Button btnsubmit;
    private EditText etadd;
    private EditText ettel;
    private EditText etquestion;
    private EditText etqq;
    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpandfeedback);

        myListener = new MyListener();
        find();
    }

    private void find() {
        ivback = findViewById(R.id.hafb_back);
        ivback.setOnClickListener(myListener);
        etadd = findViewById(R.id.add_content);
        etadd.setOnClickListener(myListener);
        btnsubmit = findViewById(R.id.help_submit);
        btnsubmit.setOnClickListener(myListener);
        ettel = findViewById(R.id.help_tel);
        ettel.setOnClickListener(myListener);
        etquestion = findViewById(R.id.help_question);
        etquestion.setOnClickListener(myListener);
        etqq = findViewById(R.id.help_QQ);
        etqq.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hafb_back:
                    Intent intent = new Intent(HelpAndFeedBackActivity.this, PersonalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.help_submit:
                    Intent intent1 = new Intent(HelpAndFeedBackActivity.this, PersonalActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
