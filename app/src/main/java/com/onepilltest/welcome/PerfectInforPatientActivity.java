package com.onepilltest.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.util.OkhttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PerfectInforPatientActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText edituserName;
    private EditText edituserNum;
    private EditText edituserAddress;
    private Button btnSucceed;
    private OkHttpClient okHttpClient;
    private SharedPreferences sharedPreferences;
    private UserPatient userPatient;
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xff56ced4);
//        }
        setContentView(R.layout.patientinfo_layout);
        findView();
        sharedPreferences = getSharedPreferences("patientRegister", MODE_PRIVATE);
        imgBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Toast.makeText(getApplicationContext(), "请完善个人信息", Toast.LENGTH_LONG).show();
                    }
                }
        );
        btnSucceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.创建OkHttpClient对象  入口
                okHttpClient = new OkHttpClient();
                registerInfo();
                if (flag) {
                    signup();
                    postUserPatient();
                } else {
                    Toast.makeText(getApplicationContext(), "请完善个人信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(Connect.BASE_URL + "auth/register?userName="+
                                    userPatient.getPhone()+"&password="+
                            userPatient.getPassword()+"&nickName="+userPatient.getNickName())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().toString();
                            Log.e("ECtest,注册成功",result);
                        }
                    });
                } catch (Exception e) {
                    Log.e("ECtest","注册失败");
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void postUserPatient() {
        String jsonStr = null;
        jsonStr = new Gson().toJson(userPatient);
        Log.e("准备注册用户:", jsonStr.toString());
        RequestBody requestBody = new FormBody.Builder()
                .add("json", jsonStr)
                .build();
        String url = Connect.BASE_URL + "user/add";
        OkhttpUtil.post(requestBody, url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("注册用户失败",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功时回调
                String isSuccessful = response.body().string();
                if (isSuccessful.equals("yes")) {
                    Log.e("注册用户成功：", isSuccessful);
                    Intent intent = new Intent(PerfectInforPatientActivity.this,
                            UserSuccessActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void registerInfo() {
        userPatient = new UserPatient();
        userPatient.setPhone(sharedPreferences.getString("phone", ""));
        userPatient.setPassword(sharedPreferences.getString("password", ""));
        userPatient.setNickName(edituserName.getText().toString());
        userPatient.setPID(edituserNum.getText().toString());
        userPatient.setAddress(edituserAddress.getText().toString());
        if (!userPatient.getNickName().equals("")
                && !userPatient.getPID().equals("")
                && !userPatient.getAddress().equals("")) {
            flag = true;
        }
    }

    private void findView() {
        imgBack = findViewById(R.id.img_left_user);
        edituserName = findViewById(R.id.user_name);
        edituserNum = findViewById(R.id.user_num);
        edituserAddress = findViewById(R.id.user_address);
        btnSucceed = findViewById(R.id.user_succeed);
    }
}
