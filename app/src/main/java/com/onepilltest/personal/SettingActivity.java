package com.onepilltest.personal;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.QR;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.message.QuestionActivity;
import com.onepilltest.util.SharedPreferencesUtil;
import com.onepilltest.util.StatusBarUtil;
import com.onepilltest.welcome.LoginActivity;
import com.onepilltest.welcome.WelcomeActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 个人_设置页面
 */
public class SettingActivity extends BaseActivity {

    int state = 0;//状态

    LinearLayout userAddress = null;//用户地址
    LinearLayout lin_nickName = null;//用户昵称
    LinearLayout lin_phone = null;//用户手机号
    LinearLayout lin_auto = null;//自动定位
    LinearLayout lin_switchUser = null;//切换账号
    LinearLayout lin_forUs = null;//关于我们
    Button sign_out = null;
    ImageButton btn = null;

    Button back = null;//返回键
    MyListener myListener = null;
    ImageView user_img = null;//头像
    TextView nickName = null;//昵称
    TextView degree = null;//身份
    ImageView QR_code = null;//二维码

    TextView tv_phone = null;
    TextView tv_nickName = null;
    TextView tv_address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xffffffff);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//        setContentView(R.layout.setting);

        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        EventBus.getDefault().register(this);
        myListener = new MyListener();
        find();
        init();
        BaseActivity.Help();

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
        return R.layout.setting;
    }

    private void find() {
        sign_out = findViewById(R.id.sign_out);
        sign_out.setOnClickListener(myListener);
        btn = findViewById(R.id.setting_lin_auto_btn);
        btn.setOnClickListener(myListener);
        /*QR_code.setOnClickListener(myListener);*/
        tv_nickName = findViewById(R.id.setting_tv_nickName);
        //tv_nickName.setText(UserBook.NowUser.getNickName());
        tv_address = findViewById(R.id.setting_tv_address);
        //tv_address.setText(UserBook.NowUser.getAddress());
        tv_phone = findViewById(R.id.setting_tv_phone);
        //tv_phone.setText(UserBook.NowUser.getPhone());
        user_img = findViewById(R.id.setting_user_img);
        user_img.setOnClickListener(myListener);
        nickName = findViewById(R.id.setting_user_nickname);
        //nickName.setText(UserBook.NowUser.getNickName());
        degree = findViewById(R.id.setting_user_degress);
        /*if (UserBook.Code == UserBook.Patient){
            degree.setText("用户");
        }else if(UserBook.Code == UserBook.Doctor){
            degree.setText("医生");
        }*/

        QR_code = findViewById(R.id.setting_user_QR_code);
        QR_code.setOnClickListener(myListener);
        back = findViewById(R.id.setting_back);
        back.setOnClickListener(myListener);
        userAddress = findViewById(R.id.setting_user_address);
        userAddress.setOnClickListener(myListener);
        lin_nickName = findViewById(R.id.setting_lin_nickName);
        lin_nickName.setOnClickListener(myListener);
        lin_phone = findViewById(R.id.setting_lin_phone);
        lin_phone.setOnClickListener(myListener);
        lin_auto = findViewById(R.id.setting_lin_auto);
        lin_auto.setOnClickListener(myListener);
        lin_switchUser = findViewById(R.id.setting_lin_switchUser);
        lin_switchUser.setOnClickListener(myListener);
        lin_forUs = findViewById(R.id.setting_lin_forUs);
        lin_forUs.setOnClickListener(myListener);

        /*RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(Connect.BASE_URL+UserBook.NowUser.getHeadImg())
                .apply(requestOptions)
                .into(user_img);*/
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_user_address://用户地址
                    if (UserBook.Code == 1) {//医生
                        Intent address_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                        startActivity(address_intent);
                        finish();
                    } else if (UserBook.Code == 2) {//用户
                        Intent address_intent = new Intent(SettingActivity.this, AddressActivity.class);
                        startActivity(address_intent);
                        finish();
                    }
                    break;
                case R.id.setting_back://返回
                    finish();
                    break;
                case R.id.setting_user_img://用户头像
                    Intent user_img_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(user_img_intent);
                    break;
                case R.id.setting_user_nickname://昵称
                    Intent nickname_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(nickname_intent);
                    break;
                case R.id.setting_user_degress://身份
                    Intent degree_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(degree_intent);
                    break;
                case R.id.setting_user_QR_code://二维码
                    Intent QR_code_intent = new Intent(SettingActivity.this, QR_codeActivity.class);
                    startActivity(QR_code_intent);
                    break;
                case R.id.setting_lin_nickName:
                    Intent nickName_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(nickName_intent);
                    break;
                case R.id.setting_lin_phone:
                    Intent phone_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(phone_intent);
                    break;
                case R.id.setting_lin_auto:
                    Intent auto_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(auto_intent);
                    break;
                case R.id.setting_lin_switchUser:
                    Log.e("UserList", UserBook.getList() + "");
                    Intent switch_intent = new Intent(SettingActivity.this, switchActivity.class);
                    startActivity(switch_intent);
                    break;
                case R.id.setting_lin_forUs:
                    Intent forUs_intent = new Intent(SettingActivity.this, SettingForUsActivity.class);
                    startActivity(forUs_intent);
                    break;
                case R.id.setting_lin_auto_btn:
                    if (state == 0) {
                        btn.setImageResource(R.drawable.off);
                        state = 1;
                    } else {
                        btn.setImageResource(R.drawable.on);
                        state = 0;
                    }

                    break;
                case R.id.sign_out://退出登陆
                    EMClient.getInstance().logout(false, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            if (UserBook.Code == 2){
                                try{
                                    SQLiteDatabase db = SQLiteDatabase
                                            .openOrCreateDatabase("/data/data/com.onepilltest/databases/user", null);
                                    db.delete("PATIENT",null,null);
                                    Log.e("SQLite","删除PATIENT表成功");
                                    db.close();
                                }catch (SQLException e){
                                    Log.e("SQLite",e.getMessage()+","+e.getCause());
                                }
                            }else if(UserBook.Code == 1){
                                try{
                                    SQLiteDatabase db = SQLiteDatabase
                                            .openOrCreateDatabase("/data/data/com.onepilltest/databases/doctor", null);
                                    db.delete("DOCTOR",null,null);
                                    Log.e("SQLite","删除DOCTOR表成功");
                                    db.close();
                                }catch (SQLException e){
                                    Log.e("SQLite",e.getMessage()+","+e.getCause());
                                }
                            }

                            SharedPreferencesUtil.delUser(getApplicationContext());
                            SharedPreferencesUtil.delDoctor(getApplicationContext());
                            Intent intent = new Intent(SettingActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Log.e("退出登录", "成功");
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.e("退出失败", i + "," + s);
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                    break;

            }
        }
    }

    public void init() {
        if (UserBook.Code == 1) {//医生
            initDoctor();
        } else if (UserBook.Code == 2) {//用户
            initPatient();
        }


    }

    //初始化用户信息
    private void initPatient() {
        //头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(Connect.BASE_URL + UserBook.NowUser.getHeadImg())
                .apply(requestOptions)
                .into(user_img);
        nickName.setText(UserBook.NowUser.getNickName());//昵称
        degree.setText("用户");//身份
        tv_nickName.setText(UserBook.NowUser.getNickName());//昵称
        tv_address.setText(UserBook.NowUser.getAddress());//地址
        tv_phone.setText(UserBook.NowUser.getPhone());//手机号
        QR_code.setImageBitmap(QR.getQR(100, 100, "想和你做朋友"));

    }

    //初始化医生信息
    private void initDoctor() {
        //头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(Connect.BASE_URL + UserBook.NowDoctor.getHeadImg())
                .apply(requestOptions)
                .into(user_img);
        nickName.setText(UserBook.NowDoctor.getName());//昵称
        degree.setText("医生");//身份
        tv_nickName.setText(UserBook.NowDoctor.getName());//昵称
        tv_address.setText(UserBook.NowDoctor.getAddress());//地址
        tv_phone.setText(UserBook.NowDoctor.getPhone());//手机号
        QR_code.setImageBitmap(QR.getQR(100, 100, "想和你做朋友"));
    }

    public void JumpAccount() {
        Intent intent = new Intent(SettingActivity.this, switchActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage msg) {

        if (UserBook.Code == 1) {//医生
            Log.e("Setting", msg.getCode() + msg.getJson());
            if (msg.getCode().equals("DoctorDao_update")) {
                Log.e("刷新", "" + msg.getJson() + msg.getCode());
                Toast.makeText(getApplicationContext(),"医生信息已更新",Toast.LENGTH_SHORT).show();
                SharedPreferencesUtil.saveDoctor(getApplicationContext(),new Gson().fromJson(msg.getJson(), UserDoctor.class));
                init();
            } else if (msg.getCode().equals("更新头像")) {
                if (msg.getJson().equals("yes")) {
                    RequestOptions requestOptions = new RequestOptions().circleCrop();
                    Glide.with(this)
                            .load(Connect.BASE_URL + UserBook.NowDoctor.getHeadImg())
                            .apply(requestOptions)
                            .into(user_img);
                }
            }
        } else if (UserBook.Code == 2) {//用户
            Log.e("SettingActivity", msg.getCode() + msg.getJson());
            if (msg.getCode().equals("UserDao_update")) {
                Log.e("刷新", "" + msg.getJson() + msg.getCode());
                Toast.makeText(getApplicationContext(),"用户信息已更新",Toast.LENGTH_SHORT).show();
                SharedPreferencesUtil.saveUser(getApplicationContext(),new Gson().fromJson(msg.getJson(), UserPatient.class));
                init();
            } else if (msg.getCode().equals("更新头像")) {
                if (msg.getJson().equals("yes")) {

                    RequestOptions requestOptions = new RequestOptions().circleCrop();
                    Glide.with(this)
                            .load(Connect.BASE_URL + UserBook.NowUser.getHeadImg())
                            .apply(requestOptions)
                            .into(user_img);
                }
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
