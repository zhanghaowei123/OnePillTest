package com.onepilltest.message;

import android.util.Log;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Inquiry;
import com.onepilltest.personal.UserBook;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class inquiryDao {
    Gson gson = new Gson();


    /**
     * 搜索全部地址
     *
     * @param UserId
     */
    public void searchAll(int UserId) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String code = "searchAll";
        Request request = new Request.Builder().url(Connect.BASE_URL + "AddressServlet?UserId=" + UserId + "&Code=" + code).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                EventMessage msg = new EventMessage();
                msg.setCode("AddressDao_searchAll");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
                Log.e("查询全部", "" + msg.getJson());

            }
        });
    }
}
