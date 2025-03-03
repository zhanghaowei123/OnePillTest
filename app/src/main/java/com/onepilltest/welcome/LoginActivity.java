package com.onepilltest.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.model.EaseGlobal;
import com.hyphenate.easeui.model.EaseMember;
import com.onepilltest.BaseActivity;
import com.onepilltest.Ease.MyUserProvider;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.ceshi.ceshiActivity;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Result;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.InfoList;
import com.onepilltest.util.OkhttpUtil;
import com.onepilltest.util.SharedPreferencesUtil;
import com.onepilltest.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private EditText editPhone;
    private EditText editPassword;
    private Button btnLogin;
    private ImageView imgEye;
    private TextView textRegister;//注册
    private OkHttpClient okHttpClient;
    MyListener myListener = new MyListener();
    private List<UserDoctor> userDoctorList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xff56ced4);
//        }
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(LoginActivity.this);

        findViews();
        int i = editPassword.getText().length();
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterPatient.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okHttpClient = OkhttpUtil.getClient();
                // TODO 环信登录SDK已失效
//                EMClient.getInstance().login(editPhone.getText().toString(),
//                        editPassword.getText().toString(), new EMCallBack() {
//                            @Override
//                            public void onSuccess() {
//                                Log.e("环信登录账号:", "成功");
//                                login();
//                            }
//
//                            @Override
//                            public void onError(int i, String s) {
//                                Log.e("环信登录账号:", "失败," + i + "" + s);
//                            }
//
//                            @Override
//                            public void onProgress(int i, String s) {
//
//                            }
//                        });

                login();
            }

        });
        //密码可视或不可视
        imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当当前密码为明文形式
                if (editPassword.getInputType() == 128) {
                    //1.将密码的输入框变为密码形式
                    editPassword.setInputType(129);
                    //将图片变为close
                    imgEye.setImageResource(R.drawable.eyeclose);
                } else if (editPassword.getInputType() == 129) {
                    //此时为密码形式
                    //1.变为文本格式
                    editPassword.setInputType(128);
                    //2.修改图片
                    imgEye.setImageResource(R.drawable.eye);
                }
            }
        });


        initBar(this);
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity, true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity, 0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity, true);

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }


    private void login() {
        OkhttpUtil.get(Connect.BASE_URL + "user/login?phone=" + editPhone.getText().toString()
                + "&password=" + editPassword.getText().toString()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("LoginFailed", e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                Log.e("登陆信息：", "\n" + jsonStr.toString());
                Result msg = new Gson().fromJson(jsonStr, Result.class);
                //获取当前用户的信息
                if (msg.getCode() == 1) {//登录成功
                    UserPatient u = msg.getUser();
                    UserBook.addUser(u);
                    save(u);
                    Log.e("登陆成功，当前用户:", "\n" + UserBook.NowUser.getId());
                    //设置自己的昵称和头像
                    MyUserProvider.getInstance().setUser(UserBook.NowUser.getPhone(),
                            UserBook.NowUser.getNickName(),
                            Connect.BASE_URL + UserBook.NowUser.getHeadImg());

                    new InfoList().patientsInfoList();
                    //跳转
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else if (msg.getCode() == 2) {//登录失败
                    Toast.makeText(getApplicationContext(), "电话不存在", Toast.LENGTH_SHORT).show();
                } else if (msg.getCode() == 3) {
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //用SharedPreferences存储
    private void save(UserPatient userPatient) {
        Log.e("自动登陆已生效，保存用户信息", userPatient.toString() + "\n");
        SharedPreferencesUtil.saveUser(getApplicationContext(), userPatient);
    }

    private void findViews() {

        editPhone = findViewById(R.id.edit_phone);
        editPassword = findViewById(R.id.edit_password);
        imgEye = findViewById(R.id.img_eye);
        btnLogin = findViewById(R.id.btn_login);
        textRegister = findViewById(R.id.text_register);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventMsg(EventMessage msg) {
        if (msg.getCode().equals("doctorsInfoList")) {
            String str = msg.getJson();
            Type type = new TypeToken<List<UserPatient>>() {
            }.getType();
            userDoctorList = new Gson().fromJson(str, type);
            //设置医生的昵称和头像
            List<EaseMember> memberList = new ArrayList<>();
            for (UserDoctor ud : userDoctorList) {
                EaseMember em = new EaseMember();
                em.member_hxid = ud.getPhone();
                em.member_nickname = ud.getName();
                em.member_headphoto = Connect.BASE_URL + ud.getHeadImg();
                em.code = 1;
                Log.e("医生头像", em.member_nickname + "," + em.member_hxid + "," + em.member_headphoto);
                memberList.add(em);
            }
            //设置自己的昵称和头像
            EaseMember easeMember = new EaseMember();
            easeMember.member_hxid = UserBook.NowUser.getPhone();
            easeMember.member_nickname = UserBook.NowUser.getNickName();
            easeMember.member_headphoto = Connect.BASE_URL + UserBook.NowUser.getHeadImg();
            easeMember.code = 2;
            memberList.add(easeMember);
            EaseGlobal.memberList = memberList;
        }
    }

}
