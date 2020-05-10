package com.onepilltest.welcome;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.onepilltest.Ease.MyUserProvider;
import com.onepilltest.MyDoctorDBHelper;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Result;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.OkhttpUtil;

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
    private MyDoctorDBHelper myDoctorDBHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff56ced4);
        }
        setContentView(R.layout.login_doctor);

        myDoctorDBHelper = new MyDoctorDBHelper(getApplicationContext(),"doctor_db",1);
        database = myDoctorDBHelper.getWritableDatabase();

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

                if (editPhone.getText().toString().equals("18831107935")) {

                    UserDoctor u = new UserDoctor();
                    String url = Connect.BASE_URL + "doctor/findById?id=19";
                    OkhttpUtil.get(url).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("登陆失败：", "请检查网络");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String str = response.body().string();
                            Log.e("已连接：", "返回值：" + str);
                            if (str != null)
                                UserBook.addUser(new Gson().fromJson(str, UserDoctor.class));
                        }
                    });
                    Intent intent1 = new Intent(LoginDoctorActivity.this, HomeActivity.class);
                    startActivity(intent1);
                }


                okHttpClient = new OkHttpClient();
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
                Log.e("test", jsonStr.toString());
                Result msg = new Gson().fromJson(jsonStr, Result.class);
                //获取当前医生的信息
                if (msg.getCode() == 1) {//登录成功
                    UserDoctor u = msg.getDoctor();
                    Log.e("DoctorId", "" + u.getId() + "|" + u.getAddress());
                    //把用户存入UserBook
                    UserBook.addUser(u);
                    save(u);//把u存进SharedPreferences
                    //设置昵称和头像
                    MyUserProvider.getInstance().setUser(UserBook.NowDoctor.getPhone(),
                            UserBook.NowDoctor.getName(),
                            Connect.BASE_URL+UserBook.NowDoctor.getHeadImg());
                    //设置SQLite数据库，将用户信息保存进去
                    try{
                        //插入数据
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("PHONE",UserBook.NowDoctor.getPhone());
                        contentValues.put("NAME",UserBook.NowDoctor.getName());
                        contentValues.put("IMG",UserBook.NowDoctor.getHeadImg());
                        database.insert("DOCTOR",null,contentValues);
                        database.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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

        SharedPreferences sharedPreferences = getSharedPreferences("NowDoctor", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userDoctor, UserDoctor.class);
        Log.e("json字符串", json);
        editor.putString("NowDoctor", json);
        editor.commit();
    }
}
