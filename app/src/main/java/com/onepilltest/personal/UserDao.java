package com.onepilltest.personal;

import android.util.Log;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.util.OkhttpUtil;
import com.onepilltest.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserDao {

    /**
     * 更新用户信息
     */
    public void update(UserPatient userPatient){

        Gson gson = new Gson();
        RequestBody requestBody = new FormBody.Builder()
                .add("json",gson.toJson(userPatient))
                .build();
        String url = Connect.BASE_URL+"user/update";
        OkhttpUtil.post(requestBody,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();
                String re = response.body().string();
                Log.e("更新用户信息：",re);
                msg.setCode("UserDao_update");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Connect.BASE_URL+"EditUserServlet?UserId="+UserId+"&Code="+code+"&"+code+"="+str).build();
//        Call call = okHttpClient.newCall(request);
//        Log.e("用户","Code"+code+"UserId"+UserId+"str"+str);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                EventMessage msg = new EventMessage();
//                Log.e("返回结果","发送成功");
//                String re = response.body().string();
//                Log.e("update返回结果",re+"");
//                msg.setCode("UserDao_update");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });
    }
}
