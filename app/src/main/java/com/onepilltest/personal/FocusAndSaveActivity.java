package com.onepilltest.personal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onepilltest.R;

import java.util.HashMap;
import java.util.Map;

public class FocusAndSaveActivity extends AppCompatActivity{

    private class MyTabSpec {
        private TextView textView = null;
        private Fragment fragment = null;

        private void setSelect(boolean b) {
            if (b) {
                textView.setTextColor(
                        Color.parseColor("#C71585"));
            } else {
                textView.setTextColor(
                        Color.parseColor("#000000"));
            }
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }

    private Map<String, MyTabSpec> map = new HashMap<>();
    private String[] tabStrId = {"医生", "病人"};
    // 用于记录当前正在显示的Fragment
    private Fragment curFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focusandsave);

        initData();
        setListener();
        changeTab(tabStrId[0]);
    }

    private class MyListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.focus_back:
                    Intent intent = new Intent(FocusAndSaveActivity.this, PersonalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fas_doctor:
                    changeTab(tabStrId[0]);
                    break;
                case R.id.fas_patient:
                    changeTab(tabStrId[1]);
                    break;
            }
        }
    }

    private void changeTab(String s) {
        // 1 切换Fragment
        changeFragment(s);
        // 2 切换图标及字体颜色
        changeImage(s);
    }

    private void changeImage(String s) {
        // 1 所有Tab的图片和字体颜色恢复默认
        for (String key : map.keySet()) {
            map.get(key).setSelect(false);
        }
        // 2 设置选中的Tab的图片和字体颜色
        map.get(s).setSelect(true);
    }

    private void changeFragment(String s) {
        Fragment fragment = map.get(s).getFragment();
        if (curFragment == fragment) return;
        // Fragment事务 - Fragment事务管理器来获取
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        // 将之前你显示的Fragment隐藏掉
        if (curFragment != null)
            transaction.hide(curFragment);
        // 如果当前Fragment没有被添加过，则添加到
        // Activity的帧布局中
        if (!fragment.isAdded()) {
            transaction.add(R.id.fl_tab_content, fragment);
        }
        // 显示对应Fragment
        transaction.show(fragment);
        curFragment = fragment;
        transaction.commit();
    }

    private void setListener() {
        ImageView ivback = findViewById(R.id.focus_back);
        TextView tvdoctor = findViewById(R.id.fas_doctor);
        TextView tvpatient = findViewById(R.id.fas_patient);
        MyListner listner = new MyListner();
        ivback.setOnClickListener(listner);
        tvdoctor.setOnClickListener(listner);
        tvpatient.setOnClickListener(listner);
    }

    private void initData() {
        // 1 创建MyTabSpec对象
        map.put(tabStrId[0], new MyTabSpec());
        map.put(tabStrId[1], new MyTabSpec());
        // 2 设置Fragment
        setFragment();
        // 3 设置ImageView和TextView
        findView();
    }

    private void setFragment() {
        map.get(tabStrId[0]).setFragment(new DoctorFragment());
        map.get(tabStrId[1]).setFragment(new PatientFragment());
    }

    private void findView() {
        TextView tv1 = findViewById(R.id.fas_doctor);
        TextView tv2 = findViewById(R.id.fas_patient);
        map.get(tabStrId[0]).setTextView(tv1);
        map.get(tabStrId[1]).setTextView(tv2);
    }
}
