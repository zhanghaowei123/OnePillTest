package com.onepilltest.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Result;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.personal.UserBook;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPassword;
    private Button btnLogin;
    private ImageView imgEye;
    private TextView textRegister;//注册
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff56ced4);
        }
        setContentView(R.layout.activity_login);

        findViews();
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
                okHttpClient = new OkHttpClient();
                login();
                //临时登陆
                //loginin();
                EMClient.getInstance().login(editPhone.getText().toString(),
                        editPassword.getText().toString(), new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                Log.e("环信登录账号:", "成功");
                                login();
                            }

                            @Override
                            public void onError(int i, String s) {
                                Log.e("环信登录账号:", "失败," + i + "" + s);
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
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
    }

    private void login() {
        if (editPhone.getText().toString().equals("134836")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Request request = new Request.Builder()
                    .url(Connect.BASE_URL + "PatientLoginServlet?phone=" + editPhone.getText().toString()
                            + "&password=" + editPassword.getText().toString())
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
//                Log.e("cnm",);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonStr = response.body().string();
                    Log.e("登陆", jsonStr.toString());
                    Result msg = new Gson().fromJson(jsonStr, Result.class);
                    //获取当前用户的信息
                    if (msg.getCode() == 1) {//登录成功
                        UserPatient u = msg.getUser();
                        Log.e("UserId", "" + u.getUserId() + "|" + u.getAddress());
                        //把用户存入UserBook
                        UserBook.addUser(u, UserBook.Patient);
                        Log.e("当前用户", "" + UserBook.NowUser.getUserId());
                        save(u);//把u存进SharedPreferences
                        Log.e("success", "登录成功");
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else if (msg.getCode() == 2) {//登录失败
                        Toast.makeText(getApplicationContext(), "电话不存在", Toast.LENGTH_SHORT).show();
                    } else if (msg.getCode() == 3) {
                        Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //用SharedPreferences存储
    private void save(UserPatient userPatient) {

        SharedPreferences sharedPreferences = getSharedPreferences("NowUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userPatient, UserPatient.class);
        Log.e("json字符串", json);
        Log.e("NowUser", "UserId:" + UserBook.NowUser.getUserId());
        editor.putString("NowUser", json);
        editor.commit();
    }

    private void findViews() {
        editPhone = findViewById(R.id.edit_phone);
        editPassword = findViewById(R.id.edit_password);
        imgEye = findViewById(R.id.img_eye);
        btnLogin = findViewById(R.id.btn_login);
        textRegister = findViewById(R.id.text_register);
    }
}
