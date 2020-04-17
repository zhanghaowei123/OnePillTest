package com.onepilltest.message;

import android.util.Log;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Inquiry;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class inquiryDao {
    Gson gson = new Gson();

    //保存问诊
    public void add(String json){
        String url = Connect.BASE_URL+"inquiry/add";
        RequestBody requestBody = new FormBody.Builder()
                .add("json",json)
                .build();
        OkhttpUtil.post(requestBody,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("问诊记录上传","上传失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("问诊记录上传",re);
            }
        });

    }

    //根据userId获取问诊记录
    public void findByUserId(int userId){
        String url = Connect.BASE_URL+"inquiry/findByUserId?userId="+userId;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("根据userId获取问诊记录：",re);
                EventMessage msg = new EventMessage();
                msg.setCode("inquiry_list");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });
    }

    //获取所有问诊记录
    public void findAll(){
        String url = Connect.BASE_URL+"inquiry/list";
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("获取所有问诊记录",re);
                EventMessage msg = new EventMessage();
                msg.setCode("all_inquiry_list");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });
    }
}
