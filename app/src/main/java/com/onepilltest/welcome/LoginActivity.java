package com.onepilltest.welcome;

import android.content.Intent;
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
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Result;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.index.HomeActivity;

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
    private TextView textRegister;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okHttpClient = new OkHttpClient();
                login();
            }
        });
    }

    private void login() {
        Request request = new Request.Builder()
                .url(Connect.BASE_URL + "PatientLoginServlet?phone=" + editPhone.getText().toString()
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
                Result msg = new Gson().fromJson(jsonStr, Result.class);
                if (msg.getCode() == 1) {//登录成功
                    UserPatient u = msg.getUser();
                   Log.e("success","登录成功");
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

    private void findViews() {
        editPhone = findViewById(R.id.edit_phone);
        editPassword = findViewById(R.id.edit_password);
        imgEye = findViewById(R.id.img_eye);
        btnLogin = findViewById(R.id.btn_login);
        textRegister = findViewById(R.id.text_register);
    }
}
