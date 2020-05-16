package com.onepilltest.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.util.StatusBarUtil;

public class HelpAndFeedBackActivity extends BaseActivity {

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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xffffffff);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.helpandfeedback);

        myListener = new MyListener();
        find();


        initBar(this);
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }


    @Override
    public int intiLayout() {
        return R.layout.helpandfeedback;
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
                    finish();
                    break;
                case R.id.help_submit:
                    finish();
                    break;
            }
        }
    }
}
