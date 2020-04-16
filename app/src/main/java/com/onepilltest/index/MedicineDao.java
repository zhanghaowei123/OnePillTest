package com.onepilltest.index;

import android.util.Log;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.medicine_;
import com.onepilltest.util.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MedicineDao {
    /**
     * 通过Name查询药品
     * @param name
     */
    public void searchMedicineByName(String name){

        String url = Connect.BASE_URL+"medicine/findByName?name="+name;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("根据name查找药品：",json);
                EventMessage msg = new EventMessage();
                msg.setJson(json);
                msg.setCode("MedicineDao_searchMedicineByName");
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Connect.BASE_URL+"MedicineServlet?name="+name+"&Code=searchMedicineByName").build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                Log.e("根据name查找药品：",json);
//                EventMessage msg = new EventMessage();
//                msg.setJson(json);
//                msg.setCode("MedicineDao_searchMedicine");
//                EventBus.getDefault().post(msg);
//            }
//        });
    }

    //根据Id查询
    public void searchMedicineById(int id){

        String url = Connect.BASE_URL+"medicine/findById?id="+id;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("根据Id查找药品：",json);
                EventMessage msg = new EventMessage();
                msg.setJson(json);
                msg.setCode("MedicineDao_searchMedicineById");
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Connect.BASE_URL+"MedicineServlet?name="+name+"&Code=searchMedicineByName").build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                Log.e("根据name查找药品：",json);
//                EventMessage msg = new EventMessage();
//                msg.setJson(json);
//                msg.setCode("MedicineDao_searchMedicine");
//                EventBus.getDefault().post(msg);
//            }
//        });
    }

}
