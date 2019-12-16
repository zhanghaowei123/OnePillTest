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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Result;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.personal.UserBook;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginDoctorActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editPhone;
    private EditText editPassword;
    private Button btnLogin;
    private ImageView imgEye;
    private TextView textRegister;//注册
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff56ced4 );
        }
        setContentView(R.layout.login_doctor);
        findViews();
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
                login();
                break;
            case R.id.img_eye:
                //当当前密码为明文形式
                if (editPassword.getInputType()==128){
                    //1.将密码的输入框变为密码形式
                    editPassword.setInputType(129);
                    //将图片变为close
                    imgEye.setImageResource(R.drawable.eyeclose);
                }else if(editPassword.getInputType() == 129){
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
                .url(Connect.BASE_URL + "DoctorLoginServlet?phone=" + editPhone.getText().toString()
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
                Log.e("test", jsonStr.toString());
                Result msg = new Gson().fromJson(jsonStr, Result.class);
                //获取当前医生的信息
                if (msg.getCode() == 1) {//登录成功
                    UserDoctor u = msg.getDoctor();
                    Log.e("DoctorId", "" + u.getDoctorId()+ "|" + u.getAddress());
                    //把用户存入UserBook
                    UserBook.addUser(u,UserBook.Doctor);
                   save(u);//把u存进SharedPreferences
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

        SharedPreferences sharedPreferences = getSharedPreferences("NowDoctor",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userDoctor,UserDoctor.class);
        Log.e("json字符串",json);
        editor.putString("NowDoctor",json);
        editor.commit();
    }
}
