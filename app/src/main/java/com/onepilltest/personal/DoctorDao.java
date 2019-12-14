package com.onepilltest.personal;

import android.util.Log;

import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DoctorDao {
    /**
     * 更新用户信息
     * @param code 更新的项目（phone）
     * @param str 修改后的值
     * 返回结果 EventMessage：code:UserDao_update；json:yes\no;
     */
    public void update(String code,String str){

        int DoctorId = UserBook.NowDoctor.getDoctorId();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Connect.BASE_URL+"EditDoctorServlet?doctorId="+DoctorId+"&Code="+code+"&"+code+"="+str+"").build();
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
                Log.e("update返回结果",re+"");
                msg.setCode("DoctorDao_update");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });
    }

    /**
     * 通过doctorId查询医生
     */

    /*public void searchDoctorById(int doctorId){
        OkHttpClient okHttpClient1 = new OkHttpClient();
        Request request = new Request.Builder().url(Connect.BASE_URL+"GetUserServlet?doctorId="+doctorId+"&Code=searchDoctorById").build();
        Call call = okHttpClient1.newCall(request);
        Log.e("医生","DoctorId"+doctorId);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("testDoctor",","+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventMessage msg = new EventMessage();
                Log.e("返回结果","发送成功");
                String re = response.body().string();
                Log.e("update返回结果",re+"");
                msg.setCode("DoctorDao_searchDoctorById");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });
    }*/

    public void searchDoctorById(int doctorId){


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Connect.BASE_URL+"GetUserServlet?doctorId="+doctorId+"&Code=searchDoctorById").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("!!!!!!!!!!","yees");
                String json = response.body().string();
                EventMessage msg = new EventMessage();
                msg.setJson(json);
                msg.setCode("DoctorDao_searchDoctorById");
                EventBus.getDefault().post(msg);
            }
        });
    }
}
