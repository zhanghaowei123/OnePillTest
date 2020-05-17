package com.onepilltest.entity.Dao;

import android.util.Log;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Cart;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.MyCart;
import com.onepilltest.entity.service_cart;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CartDao {

    //获取购物车信息
    public void getCarts(){
        String url = Connect.BASE_URL + "cart/findByUserId?userId=" + UserBook.NowUser.getId();
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("根据userId获取购物车信息：", "获取失败。");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("根据userId获取购物车信息：", re);
                EventMessage message = new EventMessage();
                message.setCode("CartDao_getCarts");
                message.setJson(re);
                EventBus.getDefault().post(message);
            }
        });
    }

    //删除购物车
    public void del(int id){
        Log.e("删除购物车",""+id+"\n");
        String url = Connect.BASE_URL+"cart/delete?id="+id;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //删除失败
                Log.e("删除失败",""+id);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //删除成功
                String str = response.body().string();
                Log.e("删除成功",""+str);
            }
        });
    }

    //添加购物车
    public void add(service_cart cart){
        String url = Connect.BASE_URL+"cart/add";
        RequestBody requestBody = new FormBody.Builder()
                .add("json",new Gson().toJson(cart))
                .build();
        OkhttpUtil.post(requestBody,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //添加失败
                Log.e("CartDao","添加购物车失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //添加成功
                String str = response.body().string();
                Log.e("CartDao","添加成功"+str);
                EventMessage msg = new EventMessage();
                msg.setCode("CartDao_add");
                msg.setJson(str);
                EventBus.getDefault().post(msg);
            }
        });
    }


    //更新购物车
    public void update(MyCart cart){
        String url = Connect.BASE_URL+"cart/update";
        RequestBody requestBody = new FormBody.Builder()
                .add("json",new Gson().toJson(cart))
                .build();
        OkhttpUtil.post(requestBody,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //更新失败
                Log.e("CartDao","更新购物车失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //更新成功
                Log.e("CartDao","更新购物车成功");
            }
        });
    }
}
