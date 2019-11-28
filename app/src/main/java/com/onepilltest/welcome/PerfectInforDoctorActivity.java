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
import com.onepilltest.entity.UserDoctor;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PerfectInforDoctorActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText editName;
    private EditText editNum;
    private EditText editHosptal;
    private EditText editAddress;
    private ImageView imgPhoto;
    private ImageView imgPhotoback;
    private Button btnSucceed;
    private OkHttpClient okHttpClient;
    private SharedPreferences sharedPreferences;
    private UserDoctor userDoctor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorinfo_layout);

        findViews();
        sharedPreferences = getSharedPreferences("doctorRegister", MODE_PRIVATE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PerfectInforDoctorActivity.this, RegisterDoctor.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "请完善个人信息", Toast.LENGTH_LONG).show();
            }
        });
        btnSucceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.创建OkHttpClient对象  入口
                okHttpClient = new OkHttpClient();
                registerInfo();
                postUserDoctor();
            }
        });
    }

    private void postUserDoctor() {
        String jsonStr = null;
        jsonStr = new Gson().toJson(userDoctor);
        Log.e("test", jsonStr.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                jsonStr);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Connect.BASE_URL + "RegisterDoctorServlet")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("false","返回错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void registerInfo() {
        userDoctor = new UserDoctor();
        userDoctor.setPhone(sharedPreferences.getString("phone", ""));
        userDoctor.setPassword(sharedPreferences.getString("password", ""));
        userDoctor.setAddress(editAddress.getText().toString());
        userDoctor.setHospital(editHosptal.getText().toString());
        userDoctor.setName(editName.getText().toString());
        userDoctor.setPID(editNum.getText().toString());
    }

    private void findViews() {
        editName = findViewById(R.id.perfect_doctorname);
        editNum = findViewById(R.id.perfect_dnum);
        editHosptal = findViewById(R.id.perfect_hosptal);
        editAddress = findViewById(R.id.perfect_address);
        imgBack = findViewById(R.id.img_left_yd);
        imgPhoto = findViewById(R.id.img_photo);
        imgPhotoback = findViewById(R.id.img_photoback);
        btnSucceed = findViewById(R.id.btn_perfect_ds);
    }


}
