package com.onepilltest.personal;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddressDao {

    /**
     * 保存
     * @param addaddress
     */
    public void save(Address addaddress){

        int UserId = UserBook.NowUser.getUserId();
        String name = addaddress.getName();
        String phoneNumber = addaddress.getPhoneNumber();
        String address = addaddress.getAddress();
        String more = addaddress.getMore();
        String postalCode = addaddress.getPostalCode();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Connect.BASE_URL+"AddAddressServlet?name="+name+"&phoneNumber="+phoneNumber
                +"&address="+address+"&more="+more+"&postalCode="+postalCode+"&UserId="+UserId+"&Code=").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();
                Log.e("返回结果","发送成功");
                Log.e("返回","onResponse: " + response.body().string());
                String re = response.body().string();

                msg.setCode("AddressDao_save");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });
    }


    /**
     *搜索全部地址
     * @param UserId
     */
    public void searchAll(int UserId){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Connect.BASE_URL+"AddAddressServlet?UserId="+UserId).build();
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

            }
        });
    }
}
