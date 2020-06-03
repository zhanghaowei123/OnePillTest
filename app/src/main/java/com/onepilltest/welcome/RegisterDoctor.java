package com.onepilltest.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.util.StatusBarUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterDoctor extends BaseActivity implements View.OnClickListener{
    private ImageView imgResponse;
    private SharedPreferences sharedPreferences;
    private CheckBox checkBox;
    private boolean flag = false;
    private EditText editPhone;         //电话号码
    private EditText editPassword;
    private EditText etVerificationCode;    //验证码
    private String phoneNumber;     // 电话号码
    private String verificationCode;  // 验证码
    private Button sendVerificationCode; // 发送验证码
    private ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff56ced4);
        }
        setContentView(R.layout.register_doctor_layout);
        sharedPreferences = getSharedPreferences("doctorRegister", MODE_PRIVATE);
        findViews();

        final Context context = RegisterDoctor.this;
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
                                Toast.makeText(RegisterDoctor.this,
                                        "已经发送验证码", Toast.LENGTH_SHORT).show();
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                Toast.makeText(RegisterDoctor.this,
                                        "验证成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(RegisterDoctor.this,
                                        PerfectInforDoctorActivity.class);
                                registerDoctor();
                                if (flag) {
                                    startActivity(intent);
                                    finish();
                                } else
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

        initBar(this);
    }


    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
        StatusBarUtil.setStatusBarColor(activity,0xF5FFFA);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }
    @Override
    public int intiLayout() {
        return R.layout.register_doctor_layout;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();//注销回调接口
    }

    private void submitPrivacyGrantResult(boolean granted){
        MobSDK.submitPolicyGrantResult(granted,new OperationCallback<Void>(){

            @Override
            public void onComplete(Void aVoid) {
               Log.e("TAG","yinsi成功");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("Tag","隐私失败");
            }
        });
    }

    private void registerDoctor() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", editPhone.getText().toString());
        editor.putString("password", editPassword.getText().toString());
        if (!editPhone.getText().toString().equals("")
                && !editPassword.getText().toString().equals("")
                && checkBox.isChecked()) {
            flag = true;
            editor.commit();
        }
    }

    private void findViews() {
        editPhone = findViewById(R.id.edit_register_doctor_phone);
        editPassword = findViewById(R.id.edit_register_doctor_password);
        etVerificationCode = findViewById(R.id.edit_register_doctor_checkword);
        imgResponse = findViewById(R.id.doctor_next);
        checkBox = findViewById(R.id.checkbox_doctor_agree);
        sendVerificationCode = findViewById(R.id.btn_send_verification_code_doctor);
        imgBack = findViewById(R.id.register_do_back);
        imgBack.setOnClickListener(this);
        imgResponse.setOnClickListener(this);
        sendVerificationCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_do_back:
//                Intent intent = new Intent();
//                intent.setClass(RegisterDoctor.this,LoginDoctorActivity.class);
//                startActivity(intent);
                finish();
                break;
            case R.id.btn_send_verification_code_doctor:
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
            case R.id.doctor_next:
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
