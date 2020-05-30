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
    public List<UserDoctor> userDoctorList = new ArrayList<>();

    public void patientsInfoList() {
        OkhttpUtil.get(Connect.BASE_URL + "user/list").enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String userListStr = response.body().string();
                EventMessage msg = new EventMessage();
                msg.setCode("patientsInfoList");
                msg.setJson(userListStr);
                EventBus.getDefault().post(msg);
                Log.e("userList", userListStr);
            }
        });
    }

    public void doctorsInfoList() {

        OkhttpUtil.get(Connect.BASE_URL + "doctor/getList").enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                EventMessage msg = new EventMessage();
                msg.setCode("doctorsInfoList");
                msg.setJson(str);
                EventBus.getDefault().post(msg);
                Log.e("userList", str);
            }
        });

        }

}
