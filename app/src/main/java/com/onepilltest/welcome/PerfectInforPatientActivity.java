package com.onepilltest.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.UserPatient;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
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
        setContentView(R.layout.patientinfo_layout);
        findView();
        sharedPreferences = getSharedPreferences("patientRegister", MODE_PRIVATE);
        imgBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(PerfectInforPatientActivity.this, RegisterPatient.class);
                        startActivity(intent);
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
                    postUserPatient();
                } else {
                    Toast.makeText(getApplicationContext(), "请完善个人信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void postUserPatient() {
        String jsonStr = null;
        jsonStr = new Gson().toJson(userPatient);
        Log.e("test", jsonStr.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                jsonStr);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Connect.BASE_URL + "RegisterServlet")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败时回调
                e.printStackTrace();
                Log.e("false", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功时回调
                String isSuccessful = response.body().string();
                if (isSuccessful.equals("true")) {
                    Log.e("successful", isSuccessful);
                    Intent intent = new Intent(PerfectInforPatientActivity.this,
                            LoginActivity.class);
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
