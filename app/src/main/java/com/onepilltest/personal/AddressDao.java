package com.onepilltest.personal;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.HeFeng;
import com.onepilltest.util.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddressDao {

    /**
     * 保存
     * @param addaddress
     */
    public void save(Address addaddress){

        Gson gson = new Gson();
        String url = Connect.BASE_URL+"address/add";
        RequestBody body = new FormBody.Builder()
                .add("json",gson.toJson(addaddress))
                .build();
        OkhttpUtil.post(body,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();
                String re = response.body().string();
                Log.e("添加地址","发送成功："+re);
                msg.setCode("AddressDao_save");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });

//        int UserId = UserBook.NowUser.getId();
//        String name = addaddress.getName();
//        String phoneNumber = addaddress.getPhoneNumber();
//        String address = addaddress.getAddress();
//        String more = addaddress.getMore();
//        String postalCode = addaddress.getPostalCode();
//        String code = "add";
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?name="+name+"&phoneNumber="+phoneNumber
//                +"&address="+address+"&more="+more+"&postalCode="+postalCode+"&UserId="+UserId+"&Code="+code).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                EventMessage msg = new EventMessage();
//                Log.e("返回结果","发送成功");
//                String re = response.body().string();
//                Log.e("save返回结果",re+"");
//                msg.setCode("AddressDao_save");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });
    }


    /**
     *搜索全部地址
     * @param UserId
     */
    public void searchAll(int UserId){

        String url = Connect.BASE_URL+"address/findByUserId?userId="+UserId;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("根据UserId查询地址:",""+re);
                EventMessage msg = new EventMessage();
                msg.setCode("AddressDao_searchAll");
                msg.setJson(re);
                EventBus.getDefault().post(msg);

            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "searchAll";
//        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?UserId="+UserId+"&Code="+code).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String re = response.body().string();
//                EventMessage msg = new EventMessage();
//                msg.setCode("AddressDao_searchAll");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//                Log.e("查询全部",""+msg.getJson());
//
//            }
//        });


    }


    //????????????????????????????????????????
    public void searchDoctorAll(int UserId){
//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "searchDoctorAll";
//        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?UserId="+UserId+"&Code="+code).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String re = response.body().string();
//                EventMessage msg = new EventMessage();
//                msg.setCode("AddressDao_searchDoctorAll");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//                Log.e("查询全部",""+msg.getJson());
//
//            }
//        });


    }


    /**
     * 查询
     */
    public void update(Address updateaddress){

        Gson gson = new Gson();
        String url = Connect.BASE_URL+"address/update";
        RequestBody body = new FormBody.Builder()
                .add("json",gson.toJson(updateaddress))
                .build();
        OkhttpUtil.post(body,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();
                String re = response.body().string();
                Log.e("更新地址信息","发送成功："+re);
                msg.setCode("AddressDao_update");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });


//        int Id = updateaddress.getId();
//        int UserId = updateaddress.getUserId();
//        String name = updateaddress.getName();
//        String phoneNumber = updateaddress.getPhoneNumber();
//        String address = updateaddress.getAddress();
//        String more = updateaddress.getMore();
//        String postalCode = updateaddress.getPostalCode();
//        String code = "update";
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?name="+name+"&phoneNumber="+phoneNumber
//                +"&address="+address+"&more="+more+"&postalCode="+postalCode+"&UserId="+UserId+"&Id="+Id+"&Code="+code).build();
//        Call call = okHttpClient.newCall(request);
//        /*Log.e("火车","Id"+Id);*/
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
//                msg.setCode("AddressDao_update");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });
    }


    public void delet(int id){

        String url = Connect.BASE_URL+"address/delete?id="+id;
        Log.e("删除Id:",""+id);
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();

                String re = response.body().string();
                Log.e("删除地址:",""+re);
                msg.setCode("AddressDao_delete");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });


//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "delete";
//        Request request = new Request.Builder().url(Connect.BASE_URL+"AddressServlet?Id="+id+"&Code="+code).build();
//        Call call = okHttpClient.newCall(request);
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
//                Log.e("delete返回结果",re+"");
//                msg.setCode("AddressDao_delete");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });
    }
}
