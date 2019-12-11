package com.onepilltest.personal;

import android.content.Intent;
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

import com.onepilltest.R;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.message.QuestionActivity;
import com.onepilltest.welcome.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 个人_设置页面
 */
public class SettingActivity extends AppCompatActivity {

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
    com.onepilltest.others.RoundImageView user_img = null;//头像
    TextView nickName = null;//昵称
    TextView degree = null;//身份
    ImageView QR_code = null;//二维码

    TextView tv_phone = null;
    TextView tv_nickName = null;
    TextView tv_address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        //getSupportActionBar().hide();
        setContentView(R.layout.setting);
        EventBus.getDefault().register(this);
        myListener = new MyListener();
        find();
    }

    private void find() {
        sign_out = findViewById(R.id.sign_out);
        sign_out.setOnClickListener(myListener);
        btn = findViewById(R.id.setting_lin_auto_btn);
        btn.setOnClickListener(myListener);
        tv_nickName = findViewById(R.id.setting_tv_nickName);
        tv_nickName.setText(UserBook.NowUser.getNickName());
        tv_address = findViewById(R.id.setting_tv_address);
        tv_address.setText(UserBook.NowUser.getAddress());
        tv_phone = findViewById(R.id.setting_tv_phone);
        tv_phone.setText(UserBook.NowUser.getPhone());
        user_img = findViewById(R.id.setting_user_img);
        user_img.setOnClickListener(myListener);
        nickName = findViewById(R.id.setting_user_nickname);
        nickName.setText(UserBook.NowUser.getNickName());
        degree = findViewById(R.id.setting_user_degress);
        if (UserBook.Code == UserBook.Patient){
            degree.setText("用户");
        }else if(UserBook.Code == UserBook.Doctor){
            degree.setText("医生");
        }

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
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_user_address://用户地址
                    Intent address_intent = new Intent(SettingActivity.this, AddressActivity.class);
                    startActivity(address_intent);
                    break;
                case R.id.setting_back://返回
                    finish();
                    break;
                case R.id.setting_user_img://用户头像
                    Intent user_img_intent = new Intent(SettingActivity.this, EditUserInfoActivity.class);
                    startActivity(user_img_intent);
                    break;
                case R.id.setting_user_nickname://昵称
                    Intent nickname_intent = new Intent(SettingActivity.this, changeNickActivity.class);
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
                    Intent nickName_intent = new Intent(SettingActivity.this,EditUserInfoActivity.class);
                    startActivity(nickName_intent);
                    break;
                case R.id.setting_lin_phone:
                    Intent phone_intent = new Intent(SettingActivity.this,EditUserInfoActivity.class);
                    startActivity(phone_intent);
                    break;
                case R.id.setting_lin_auto:
                    Intent auto_intent = new Intent(SettingActivity.this,EditUserInfoActivity.class);
                    startActivity(auto_intent);
                    break;
                case R.id.setting_lin_switchUser:
                    Log.e("UserList",UserBook.getList()+"");
                    Intent switch_intent = new Intent(SettingActivity.this, switchActivity.class);
                    /*intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                    startActivity(switch_intent);
                    /*Intent switch_intent = new Intent(SettingActivity.this,switchActivity.class);
                    startActivity();*/
                    break;
                case R.id.setting_lin_forUs:
                    Intent forUs_intent = new Intent(SettingActivity.this,SettingForUsActivity.class);
                    startActivity(forUs_intent);
                    break;
                case R.id.setting_lin_auto_btn:
                    if(state ==0){
                        btn.setImageResource(R.drawable.off);
                        state = 1;
                    }else{
                        btn.setImageResource(R.drawable.on);
                        state = 0;
                    }

                    break;
                case R.id.sign_out://退出登陆
                    UserBook.NowUser = null;
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;

            }
        }
    }

    public void JumpAccount(){
        Intent intent = new Intent(SettingActivity.this,switchActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage msg){

        if (msg.getCode() == "UserDao_update"){
            if(msg.getJson()=="yes"){
                Log.e("刷新",""+msg.getJson()+msg.getCode());
                onCreate(null);
            }else{
                Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT);
            }
        }

    }

}
