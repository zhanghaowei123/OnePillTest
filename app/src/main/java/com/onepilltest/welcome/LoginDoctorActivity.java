package com.onepilltest.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Result;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.InfoList;
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

public class LoginDoctorActivity extends BaseActivity implements View.OnClickListener {
    private EditText editPhone;
    private EditText editPassword;
    private Button btnLogin;
    private ImageView imgEye;
    private TextView textRegister;//注册
    private OkHttpClient okHttpClient;
    List<UserPatient> userPatientList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xff56ced4);
//        }
        setContentView(R.layout.login_doctor);
        EventBus.getDefault().register(LoginDoctorActivity.this);
        findViews();

        initBar(this);
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity, true);
        //设置状态栏颜色0xff56ced4
        StatusBarUtil.setStatusBarColor(activity, 0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity, false);

    }

    @Override
    public int intiLayout() {
        return R.layout.login_doctor;
    }

    private void findViews() {
        editPhone = findViewById(R.id.edit_phone_doctor);
        editPassword = findViewById(R.id.edit_password_doctor);
        imgEye = findViewById(R.id.img_eye_doctor);
        btnLogin = findViewById(R.id.btn_login_doctor);
        textRegister = findViewById(R.id.text_register_doctor);
        textRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        imgEye.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_register_doctor:
                Intent intent = new Intent();
                intent.setClass(LoginDoctorActivity.this, RegisterDoctor.class);
                startActivity(intent);
                break;
            case R.id.btn_login_doctor:
                okHttpClient = new OkHttpClient();
                EMClient.getInstance().login(editPhone.getText().toString(),
                        editPassword.getText().toString(), new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                Log.e("环信登录账号:", "成功");
                                login();
                                finish();
                            }

                            @Override
                            public void onError(int i, String s) {
                                Log.e("环信登录账号:", "失败," + i + "" + s);
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                break;
            case R.id.img_eye_doctor:
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
                    //2.修改图片;
                    imgEye.setImageResource(R.drawable.eye);
                }

        }
    }

    private void login() {
        Request request = new Request.Builder()
                .url(Connect.BASE_URL + "doctor/login?phone=" + editPhone.getText().toString()
                        + "&password=" + editPassword.getText().toString())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                Log.e("登陆信息：", "\n" + jsonStr.toString());
                Result msg = new Gson().fromJson(jsonStr, Result.class);
                //获取当前医生的信息
                if (msg.getCode() == 1) {//登录成功
                    UserDoctor u = msg.getDoctor();
                    Log.e("DoctorId", "" + u.getId() + "|" + u.getAddress());
                    //把用户存入UserBook
                    UserBook.addDoctor(u);
                    save(u);//把u存进SharedPreferences

                    new InfoList().patientsInfoList();
                    //设置昵称和头像
                    MyUserProvider.getInstance().setUser(UserBook.NowDoctor.getPhone(),
                            UserBook.NowDoctor.getName(),
                            Connect.BASE_URL + UserBook.NowDoctor.getHeadImg());

                    Log.e("success", "登录成功");
                    Intent intent = new Intent(LoginDoctorActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (msg.getCode() == 2) {//登录失败
                    Toast.makeText(getApplicationContext(), "电话不存在", Toast.LENGTH_SHORT).show();
                } else if (msg.getCode() == 3) {
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //用SharedPreferences存储
    private void save(UserDoctor userDoctor) {
        SharedPreferencesUtil.saveDoctor(getApplicationContext(), userDoctor);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventMsg(EventMessage msg) {

        if (msg.getCode().equals("patientsInfoList")) {
            String str = msg.getJson();
            Type type = new TypeToken<List<UserPatient>>() {
            }.getType();
            userPatientList = new Gson().fromJson(str, type);
            //设置病人的昵称和头像
            List<EaseMember> memberList = new ArrayList<>();
            for (UserPatient up : userPatientList) {
                EaseMember em = new EaseMember();
//                        em.member_hxid = "15227552449";
//                        em.member_nickname = "张昊伟123";
//                        em.member_headphoto = Connect.BASE_URL + "/image/headImg/33_headImgc4b4a6ed-fe53-449e-85b3-0d6ef566eb90.png";
                em.member_hxid = up.getPhone();
                em.member_nickname = up.getNickName();
                em.member_headphoto = Connect.BASE_URL + up.getHeadImg();
                em.code = 2;
                Log.e("病人头像", em.member_nickname + "," + em.member_hxid + "," + em.member_headphoto);
                memberList.add(em);
            }
            //设置自己的昵称和头像
            EaseMember easeMember = new EaseMember();
            easeMember.member_hxid = UserBook.NowDoctor.getPhone();
            easeMember.member_nickname = UserBook.NowDoctor.getName();
            easeMember.member_headphoto = Connect.BASE_URL + UserBook.NowDoctor.getHeadImg();
            easeMember.code = 1;
            memberList.add(easeMember);
            EaseGlobal.memberList = memberList;
        }
    }
}
