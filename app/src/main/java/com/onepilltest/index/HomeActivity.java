package com.onepilltest.index;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onepilltest.BaseActivity;
import com.onepilltest.PermissionsManager;
import com.onepilltest.PermissionsResultAction;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.onepilltest.R;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.message.MessageFragment;
import com.onepilltest.nearby.NearFragment;
import com.onepilltest.personal.PersonalFragment;
import com.onepilltest.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends BaseActivity {

    private class MyTabSpec {
        private ImageView imageView = null;
        private TextView textView = null;
        private int normalImage;
        private int selectImage;
        private Fragment fragment = null;

        // 设置是否被选中
        private void setSelect(boolean b) {
            if (b) {
                imageView.setImageResource(selectImage);
                textView.setTextColor(
                        Color.parseColor("#C71585"));
            } else {
                imageView.setImageResource(normalImage);
                textView.setTextColor(
                        Color.parseColor("#000000"));
            }
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public int getNormalImage() {
            return normalImage;
        }

        public void setNormalImage(int normalImage) {
            this.normalImage = normalImage;
        }

        public int getSelectImage() {
            return selectImage;
        }

        public void setSelectImage(int selectImage) {
            this.selectImage = selectImage;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }

    com.onepilltest.others.CustomScrollBar bar = null;
    private Map<String, MyTabSpec> map = new HashMap<>();
    private String[] tabStrId = {"首页", "附近", "消息", "个人"};
    // 用于记录当前正在显示的Fragment
    private Fragment curFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xff56ced4);
//        }
        setContentView(R.layout.activity_main);

        initStatusBar();
        requestPermissions();
        initData();
        setListener();
        // 设置默认显示的TabSpec
        changeTab(tabStrId[0]);
    }

    private void initStatusBar() {
//        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    // 自定义的监听器类，完成Tab页面切换及图表转化
    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EventMessage msg = new EventMessage();
            msg.setCode("bar");
            switch (v.getId()) {
                case R.id.tab_spec_home:
                    Log.e("bar","首页\n");
                    changeTab(tabStrId[0]);
                    msg.setJson("0");
                    EventBus.getDefault().post(msg);
                    //设置状态栏透明
//                    StatusBarUtil.setTranslucentStatus(HomeActivity.this);
                    //设置状态栏paddingTop
                    StatusBarUtil.setRootViewFitsSystemWindows(HomeActivity.this, false);
                    //设置状态栏颜色
                    StatusBarUtil.setStatusBarColor(HomeActivity.this, 0xff56ced4);
                    //设置状态栏神色浅色切换
                    StatusBarUtil.setStatusBarDarkTheme(HomeActivity.this, false);
                    break;
                case R.id.tab_spec_near:
                    Log.e("bar","附近\n");
                    changeTab(tabStrId[1]);
                    msg.setJson("1");
                    EventBus.getDefault().post(msg);
                    //设置状态栏透明
//                    StatusBarUtil.setTranslucentStatus(HomeActivity.this);
                    //设置状态栏paddingTop
                    StatusBarUtil.setRootViewFitsSystemWindows(HomeActivity.this, false);
                    //设置状态栏颜色
                    StatusBarUtil.setStatusBarColor(HomeActivity.this, 0x0056ced4);
                    //设置状态栏神色浅色切换
                    StatusBarUtil.setStatusBarDarkTheme(HomeActivity.this, true);

                    break;
                case R.id.tab_spec_msg:
                    Log.e("bar","消息\n");
                    changeTab(tabStrId[2]);
                    msg.setJson("1");
                    EventBus.getDefault().post(msg);
                    //设置状态栏透明
//                    StatusBarUtil.setTranslucentStatus(HomeActivity.this);
                    //设置状态栏paddingTop
                    StatusBarUtil.setRootViewFitsSystemWindows(HomeActivity.this, false);
                    //设置状态栏颜色
                    StatusBarUtil.setStatusBarColor(HomeActivity.this, 0xff56ced4);
                    //设置状态栏神色浅色切换
                    StatusBarUtil.setStatusBarDarkTheme(HomeActivity.this, false);
                    break;
                case R.id.tab_spec_me:
                    Log.e("bar","个人\n");
                    changeTab(tabStrId[3]);
                    msg.setJson("1");
                    EventBus.getDefault().post(msg);
                    //设置状态栏透明
//                    StatusBarUtil.setTranslucentStatus(HomeActivity.this);
                    //设置状态栏paddingTop
                    StatusBarUtil.setRootViewFitsSystemWindows(HomeActivity.this, false);
                    //设置状态栏颜色
                    StatusBarUtil.setStatusBarColor(HomeActivity.this, 0x0056ced4);
                    //设置状态栏神色浅色切换
                    StatusBarUtil.setStatusBarDarkTheme(HomeActivity.this, false);
                    break;
            }
        }
    }

    // 根据Tab ID 切换Tab
    private void changeTab(String s) {
        // 1 切换Fragment
        changeFragment(s);
        // 2 切换图标及字体颜色
        changeImage(s);
    }

    // 根据Tab ID 切换 Tab显示的图片及字体颜色
    private void changeImage(String s) {
        // 1 所有Tab的图片和字体颜色恢复默认
        for (String key : map.keySet()) {
            map.get(key).setSelect(false);
        }
        // 2 设置选中的Tab的图片和字体颜色
        map.get(s).setSelect(true);
    }

    // 根据Tab ID 切换 Tab显示的Fragment
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
            transaction.add(R.id.tab_content, fragment);
        }
        // 显示对应Fragment
        transaction.show(fragment);
        curFragment = fragment;
        transaction.commit();
    }

    //设置监听器
    private void setListener() {
        LinearLayout layoutHome = findViewById(R.id.tab_spec_home);
        LinearLayout layoutNear = findViewById(R.id.tab_spec_near);
        LinearLayout layoutMsg = findViewById(R.id.tab_spec_msg);
        LinearLayout layoutMe = findViewById(R.id.tab_spec_me);
        MyListener listener = new MyListener();
        layoutHome.setOnClickListener(listener);
        layoutNear.setOnClickListener(listener);
        layoutMsg.setOnClickListener(listener);
        layoutMe.setOnClickListener(listener);
    }

    // 初始化，初始化MyTabSpec对象
    private void initData() {
        // 1 创建MyTabSpec对象
        map.put(tabStrId[0], new MyTabSpec());
        map.put(tabStrId[1], new MyTabSpec());
        map.put(tabStrId[2], new MyTabSpec());
        map.put(tabStrId[3], new MyTabSpec());
        // 2 设置Fragment
        setFragment();
        // 3 设置ImageView和TextView
        findView();
        // 4 设置图片资源
        setImage();
    }

    // 创建Fragment对象并放入map的MyTabSpec对象中
    private void setFragment() {
        map.get(tabStrId[0]).setFragment(new HomeFragment());
        map.get(tabStrId[1]).setFragment(new NearFragment());
        map.get(tabStrId[2]).setFragment(new MessageFragment());
        map.get(tabStrId[3]).setFragment(new PersonalFragment());
    }

    // 将ImageView和TextView放入map中的MyTabSpec对象
    private void findView() {
        ImageView iv1 = findViewById(R.id.iv_home);
        ImageView iv2 = findViewById(R.id.iv_near);
        ImageView iv3 = findViewById(R.id.iv_msg);
        ImageView iv4 = findViewById(R.id.iv_me);
        TextView tv1 = findViewById(R.id.tv_home);
        TextView tv2 = findViewById(R.id.tv_near);
        TextView tv3 = findViewById(R.id.tv_msg);
        TextView tv4 = findViewById(R.id.tv_me);
        map.get(tabStrId[0]).setImageView(iv1);
        map.get(tabStrId[0]).setTextView(tv1);
        map.get(tabStrId[1]).setImageView(iv2);
        map.get(tabStrId[1]).setTextView(tv2);
        map.get(tabStrId[2]).setImageView(iv3);
        map.get(tabStrId[2]).setTextView(tv3);
        map.get(tabStrId[3]).setImageView(iv4);
        map.get(tabStrId[3]).setTextView(tv4);

        bar = findViewById(R.id.fragement_home_bar);
    }

    // 将图片资源放入map的MyTabSpec对象中
    private void setImage() {
        map.get(tabStrId[0]).setNormalImage(R.drawable.home_normal);
        map.get(tabStrId[0]).setSelectImage(R.drawable.home_select);
        map.get(tabStrId[1]).setNormalImage(R.drawable.near_normal);
        map.get(tabStrId[1]).setSelectImage(R.drawable.near_select);
        map.get(tabStrId[2]).setNormalImage(R.drawable.message_normal);
        map.get(tabStrId[2]).setSelectImage(R.drawable.message_select);
        map.get(tabStrId[3]).setNormalImage(R.drawable.personal_normal);
        map.get(tabStrId[3]).setSelectImage(R.drawable.personal_select);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("扫码结果分析", "Cancelled");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "扫描结果为空", Toast.LENGTH_LONG).show();
            } else {
                String str = result.getContents();
                Log.e("扫描结构", str);
//                Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @TargetApi(28)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
