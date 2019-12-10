package com.onepilltest.welcome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mob.MobSDK;
import com.onepilltest.R;
import com.onepilltest.entity.UserPatient;

import java.util.HashMap;
import java.util.Objects;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.OkHttpClient;

public class RegisterPatient extends AppCompatActivity implements View.OnClickListener {
    private EditText editPhone;         //电话号码
    private EditText editPassword;
    private EditText etVerificationCode;    //验证码

    private String phoneNumber;     // 电话号码
    private String verificationCode;  // 验证码

    private ImageView imgNext;          //下一步
    private Button sendVerificationCode; // 发送验证码
    private SharedPreferences sharedPreferences;
    private boolean newFlag = false;
    private CheckBox checkBox;
    private ImageView imgBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_patient_layout);
        sharedPreferences = getSharedPreferences("patientRegister", MODE_PRIVATE);

        initView();

        final Context context = RegisterPatient.this;
        final String appKey = "2d56f72063034";
        final String appSecret = "eca0694413ffa7ff41ab38445e675149";

        MobSDK.init(this, appKey, appSecret);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //如果操作成功
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                                Toast.makeText(RegisterPatient.this,
                                        "已经发送验证码", Toast.LENGTH_SHORT).show();
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                Toast.makeText(RegisterPatient.this,
                                        "验证成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(RegisterPatient.this,
                                        PerfectInforPatientActivity.class);
                                register();
                                if (newFlag)
                                    startActivity(intent);
                                else
                                    Toast.makeText(getApplicationContext(),
                                            "请完善个人信息并同意用户协议", Toast.LENGTH_SHORT).show();
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        }
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);/// 注册一个事件回调，用于处理SMSSDK接口请求的结果
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();//注销回调接口
    }

    private void register() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", editPhone.getText().toString());
        editor.putString("password", editPassword.getText().toString());
        if (!editPhone.getText().toString().equals("")
                && !editPassword.getText().toString().equals("")
                && checkBox.isChecked()) {
            newFlag = true;
            editor.commit();
        }
    }

    private void initView() {
        editPhone = findViewById(R.id.edit_register_patient_phone);
        editPassword = findViewById(R.id.edit_register_patient_password);
        etVerificationCode = findViewById(R.id.edit_register_patient_getcheckwords);
        sendVerificationCode = findViewById(R.id.btn_send_verification_code);
        imgNext = findViewById(R.id.patient_next);
        checkBox = findViewById(R.id.checkbox_patient_agree);
        imgBack = findViewById(R.id.register_pa_back);
        imgNext.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        sendVerificationCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_pa_back:
//                Intent intent = new Intent();
//                intent.setClass(RegisterPatient.this,LoginActivity.class);
//                startActivity(intent);
                finish();
                break;
            case R.id.btn_send_verification_code:
                if (!TextUtils.isEmpty(editPhone.getText())) {
                    if (editPhone.getText().length() == 11) {
                        phoneNumber = editPhone.getText().toString();
                        SMSSDK.getVerificationCode("86", phoneNumber); // 发送验证码给号码的 phoneNumber 的手机
                        etVerificationCode.requestFocus();
                    } else {
                        Toast.makeText(this, "请输入完整的电话号码", Toast.LENGTH_SHORT).show();
                        editPhone.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    editPhone.requestFocus();
                }
                break;
            case R.id.patient_next:
                if (!TextUtils.isEmpty(etVerificationCode.getText())) {
                    if (etVerificationCode.getText().length() == 4) {
                        verificationCode = etVerificationCode.getText().toString();
                        SMSSDK.submitVerificationCode("86", phoneNumber, verificationCode);
                    } else {
                        Toast.makeText(this, "请输入完整的验证码", Toast.LENGTH_SHORT).show();
                        etVerificationCode.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    etVerificationCode.requestFocus();
                }
                break;
            default:
                break;
        }
    }
}
