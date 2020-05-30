package com.onepilltest.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InfoList {
    private List<UserPatient> userPatientList = new ArrayList<>();
    private List<UserDoctor> userDoctorList = new ArrayList<>();

    public List<UserPatient> patientsInfoList() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Connect.BASE_URL + "user/list")
                .build();
        Log.e("LoginInfo", request.toString());
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String userListStr = response.body().string();
                EventMessage msg = new EventMessage();
                msg.setCode("patientsInfoList");
                msg.setJson(userListStr);
                EventBus.getDefault().post(msg);
                Log.e("userList", userListStr);
                //定义他的派生类调用getType，真实对象
                Type type = new TypeToken<List<UserPatient>>() {
                }.getType();
                userPatientList.addAll(new Gson().fromJson(userListStr, type));
            }
        });
        return userPatientList;
    }

    public List<UserDoctor> doctorsInfoList() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Connect.BASE_URL + "doctor/getList")
                .build();
        Log.e("LoginInfo", request.toString());
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("youwenti", "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String doctorListStr = response.body().string();
                Log.e("doctorList", doctorListStr);
                Type type = new TypeToken<List<UserDoctor>>() {
                }.getType();
                userDoctorList.addAll(new Gson().fromJson(doctorListStr, type));
            }
        });
        Log.e("ff", userDoctorList.toString());
        return userDoctorList;
    }
}
