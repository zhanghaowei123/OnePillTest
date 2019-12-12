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
        Request request = new Request.Builder().url(Connect.BASE_URL+"EditUserServlet?UserId="+DoctorId+"&Code="+code+"&"+code+"="+str).build();
        Call call = okHttpClient.newCall(request);
        Log.e("医生","Code"+code+"DoctorId"+DoctorId+"str"+str);
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
}
