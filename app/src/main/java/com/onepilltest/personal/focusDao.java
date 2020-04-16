package com.onepilltest.personal;

import android.util.Log;

import com.google.gson.Gson;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.focus;
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

public class focusDao {
    /**
     * 查询收藏的医生
     * @param userId
     * @param userType
     */
    public void searchDoctor(int userId,int userType){

        String url = Connect.BASE_URL+"focus/doctorList?userId="+userId+"&userType="+userType;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("获取关注医生列表：",""+re);
                EventMessage msg = new EventMessage();
                msg.setCode("focusDao_searchDoctor");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "searchDoctorList";
//        Request request = new Request.Builder().url(Connect.BASE_URL+"focusServlet?userId="+userId+"&userType="+userType+"&Code="+code).build();
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
//                Log.e("focusDao","获取关注医生列表!!\n"+re);
//                EventMessage msg = new EventMessage();
//                msg.setCode("focusDao_searchDoctor");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });


    }


    /**
     * 查询收藏的药品
     * @param userId
     * @param userType
     */
    public void searchMedicine(int userId,int userType){

        String url = Connect.BASE_URL+"focus/medicineList?userId="+userId+"&userType="+userType;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("查询收藏药品列表:",""+re);
                EventMessage msg = new EventMessage();
                msg.setCode("focusDao_searchMedicine");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });


//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "searchMedicineList";
//        Request request = new Request.Builder().url(Connect.BASE_URL+"focusServlet?userId="+userId+"&userType="+userType+"&Code="+code).build();
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
//                Log.e("focusDao","查询收藏药品列表!!\n"+re);
//                EventMessage msg = new EventMessage();
//                msg.setCode("focusDao_searchMedicine");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });


    }


    /**
     * 添加收藏
     * @param userId
     * @param userType
     * @param type
     * @param typeId
     */
    public void add(int userId,int userType,int type,int typeId){

        focus f = new focus();
        f.setTypeId(typeId);
        f.setType(type);
        f.setUserType(userType);
        f.setUserId(userId);
        Gson gson = new Gson();
        String url = Connect.BASE_URL+"focus/add";
        RequestBody body = new FormBody.Builder()
                .add("json",gson.toJson(f))
                .build();
        OkhttpUtil.post(body,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("添加收藏：",re);
                EventMessage msg = new EventMessage();
                msg.setCode("focusDao_add");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "add";
//        Request request = new Request.Builder().url(Connect.BASE_URL+"focusServlet?userId="+userId+"&userType="+userType+"&type="+type+"&typeId="+typeId+"&Code="+code).build();
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
//                Log.e("focusDao","添加收藏");
//                EventMessage msg = new EventMessage();
//                msg.setCode("focusDao_add");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });

    }



    public void del(int userId,int userType,int type,int typeId){

       String url = Connect.BASE_URL+"focus/delete?userId="+userId+"&userType="+userType+"&type="+type+"&typeId="+typeId;
       OkhttpUtil.get(url).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {

           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String re = response.body().string();
               Log.e("取消收藏：",re);
               EventMessage msg = new EventMessage();
               msg.setCode("focusDao_del");
               msg.setJson(re);
               EventBus.getDefault().post(msg);
           }
       });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "del";
//        Request request = new Request.Builder().url(Connect.BASE_URL+"focusServlet?userId="+userId+"&userType="+userType+"&type="+type+"&typeId="+typeId+"&Code="+code).build();
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
//                Log.e("focusDao","取消收藏");
//                EventMessage msg = new EventMessage();
//                msg.setCode("focusDao_del");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });


    }


    public void isHave(int userId,int userType,int type,int typeId){

        String url = Connect.BASE_URL+"focus/isHave?userId="+userId+"&userType="+userType+"&type="+type+"&typeId="+typeId;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("查询是否收藏：",re);
                EventMessage msg = new EventMessage();
                msg.setCode("focusDao_isHave");
                msg.setJson(re);
                EventBus.getDefault().post(msg);
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        String code = "isHave";
//        Request request = new Request.Builder().url(Connect.BASE_URL+"focusServlet?userId="+userId+"&userType="+userType+"&type="+type+"&typeId="+typeId+"&Code="+code).build();
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
//                Log.e("focusDao","查询是否收藏"+re);
//                EventMessage msg = new EventMessage();
//                msg.setCode("focusDao_isHave");
//                msg.setJson(re);
//                EventBus.getDefault().post(msg);
//            }
//        });


    }

}
