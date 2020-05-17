package com.onepilltest.entity.Dao;

import android.util.Log;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Orders;
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

public class OrdersDao {

    public void searchAll(int UserId) {
        String url = Connect.BASE_URL + "order/findByUserId?userId=" + UserId;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("查询全部订单", re);
                EventMessage msg = new EventMessage();
                msg.setCode("OrdersDao_searchAll");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "searchAll";
//        Request request = new Request.Builder().url(Connect.BASE_URL + "OrdersServlet?UserId=" + UserId + "&Code=" + code).build();
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
//                msg.setCode("OrdersDao_searchAll");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//                Log.e("查询全部", "" + msg.getJson());
//
//            }
//        });
    }

    /**
     * add
     * @param orders
     */
        public void add(Orders orders){

            Gson gson = new Gson();
            RequestBody requestBody = new FormBody.Builder()
                    .add("json",gson.toJson(orders))
                    .build();
            String url = Connect.BASE_URL+"order/add";
            OkhttpUtil.post(requestBody,url).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    EventMessage msg = new EventMessage();
                    String re = response.body().string();
                    Log.e("添加订单：",re+"");
                    msg.setCode("OrdersServlet_add");
                    msg.setJson(re);
                    EventBus.getDefault().post(msg);
                }
            });

//            int UserId = UserBook.NowUser.getId();
//            int medicineId = orders.getMedicineId();
//            int count = orders.getCount();
//            String img = orders.getImg();
//            int number = 3;
//            int status = orders.getStatus();
//            String code = "add";
//
//            OkHttpClient okHttpClient = new OkHttpClient();
//            Request request = new Request.Builder().url(Connect.BASE_URL+"OrdersServlet?UserId="+UserId+"&medicineId="+medicineId
//                    +"&count="+count+"&number="+number+"&status="+status+"&img="+img+"&Code="+code).build();
//            Call call = okHttpClient.newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    EventMessage msg = new EventMessage();
//                    String re = response.body().string();
//                    Log.e("add返回结果",re+"");
//                    msg.setCode("OrdersServlet_add");
//                    msg.setJson(re);
//                    EventBus.getDefault().post(msg);
//                }
//            });
        }

    }

