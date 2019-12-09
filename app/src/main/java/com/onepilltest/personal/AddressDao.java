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
        String code = "add";

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?name="+name+"&phoneNumber="+phoneNumber
                +"&address="+address+"&more="+more+"&postalCode="+postalCode+"&UserId="+UserId+"&Code="+code).build();
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
                String re = response.body().string();
                Log.e("save返回结果",re+"");
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
        String code = "searchAll";
        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?UserId="+UserId+"&Code="+code).build();
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
                Log.e("查询全部",""+msg.getJson());

            }
        });


    }

    /**
     * 查询
     */
    public void update(Address updateaddress){
        int Id = updateaddress.getId();
        int UserId = updateaddress.getUserId();
        String name = updateaddress.getName();
        String phoneNumber = updateaddress.getPhoneNumber();
        String address = updateaddress.getAddress();
        String more = updateaddress.getMore();
        String postalCode = updateaddress.getPostalCode();
        String code = "update";

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?name="+name+"&phoneNumber="+phoneNumber
                +"&address="+address+"&more="+more+"&postalCode="+postalCode+"&UserId="+UserId+"&Id="+Id+"&Code="+code).build();
        Call call = okHttpClient.newCall(request);
        /*Log.e("火车","Id"+Id);*/
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();
                Log.e("返回结果","发送成功");
                String re = response.body().string();
                Log.e("update返回结果",re+"");
                msg.setCode("AddressDao_update");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });
    }


    public void delet(int id){
        OkHttpClient okHttpClient = new OkHttpClient();
        String code = "delete";
        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?Id="+id+"&Code="+code).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();
                Log.e("返回结果","发送成功");
                String re = response.body().string();
                Log.e("delete返回结果",re+"");
                msg.setCode("AddressDao_delete");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });
    }
}
