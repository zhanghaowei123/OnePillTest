package com.onepilltest.personal;

import android.util.Log;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.UserDoctor;
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

public class DoctorDao {
    /**
     * 更新医生信息
     */
    public void update(UserDoctor doctor){

        RequestBody requestBody = new FormBody.Builder()
                .add("json",new Gson().toJson(doctor))
                .build();
        String url = Connect.BASE_URL+"doctor/update";
        OkhttpUtil.post(requestBody,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();
                String re = response.body().string();
                Log.e("更新医生信息:",re+"");
                msg.setCode("DoctorDao_update");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });

//        int DoctorId = UserBook.NowDoctor.getDoctorId();
//        RequestBody requestBody = new FormBody.Builder()
//                .add("code",code)
//                .add("str",str)
//                .build();
//        String url = Connect.BASE_URL+"doctor/update";
//        OkhttpUtil.post(requestBody,url).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("更新医生信息","未连接服务器");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                EventMessage msg = new EventMessage();
//                String re = response.body().string();
//                Log.e("更新医生信息",re+"");
//                msg.setCode("DoctorDao_update");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });


//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Connect.BASE_URL+"doctor/update?id="+DoctorId+"&code="+code+"&"+code+"="+str+"").build();
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
//                Log.e("update返回结果",re+"");
//                msg.setCode("DoctorDao_update");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });
    }

    /**
     * 通过doctorId查询医生
     */

    public void searchDoctorById(int doctorId){

        String url = Connect.BASE_URL+"doctor/findById?id="+doctorId;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("根据Id查询医生信息","查询成功："+json);
                EventMessage msg = new EventMessage();
                msg.setJson(json);
                msg.setCode("DoctorDao_searchDoctorById");
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Connect.BASE_URL+"GetUserServlet?doctorId="+doctorId+"&Code=searchDoctorById").build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.e("!!!!!!!!!!","yees");
//                String json = response.body().string();
//                EventMessage msg = new EventMessage();
//                msg.setJson(json);
//                msg.setCode("DoctorDao_searchDoctorById");
//                EventBus.getDefault().post(msg);
//            }
//        });
    }

    /**
     * 通过Name查询医生
     */

    public void searchDoctorByName(String name){

        String url = Connect.BASE_URL+"doctor/findByName?name="+name;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                EventMessage msg = new EventMessage();
                Log.e("通过name查询医生","查询成功："+json);
                msg.setJson(json);
                msg.setCode("DoctorDao_searchDoctorByName");
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Connect.BASE_URL+"GetUserServlet?name="+name+"&Code=searchDoctorByName").build();
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
//                EventMessage msg = new EventMessage();
//                Log.e("找医生","连接服务器");
//                msg.setJson(json);
//                msg.setCode("DoctorDao_searchDoctorByName");
//                EventBus.getDefault().post(msg);
//            }
//        });
    }
}
