package com.onepilltest.entity.Dao;

import android.util.Log;

import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//点赞
public class GoodDao {

    public static Map<Integer,Integer> goodMap = new HashMap<>();

    /**
     * 存储点赞操作
     * @param id 评论ID
     * @param f isGood
     */
    public static void addgoodList(int id,boolean f) {
        Log.e("goodList","Id:"+id+"f:"+f+"\n");
        if (!f){
            if (!goodMap.containsKey(id)){
                goodMap.put(id,-1);
            }else {
                int i = goodMap.get(id);
                goodMap.put(id,i--);
            }
        }else{
            if (!goodMap.containsKey(id)){
                goodMap.put(id,1);
            }else {
                int i = goodMap.get(id);
                goodMap.put(id,i++);
            }
        }
    }


    public static void post() {
        GoodDao goodDao = new GoodDao();
        Log.e("处理点赞事件","Begin...");
        for(int id:goodMap.keySet()) {
            Log.e("goodList","Comment:"+id+"num:"+goodMap.get(id)+"\n");
            if (goodMap.get(id) == 1){
                //点赞
                goodDao.add(UserBook.NowUser.getId(),id);
            }else if (goodMap.get(id) == -1){
                //取消点赞
                goodDao.del(UserBook.NowUser.getId(),id);
            }
        }
        Log.e("处理点赞事件","End.");
    }

    //查询是否已经点赞
    public void isGood(int userId, int commentId) {
        String url = Connect.BASE_URL + "good/isGood?userId=" + userId + "&commentId=" + commentId;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("查询是否点赞：", "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("查询是否点赞：", "" + str);
                EventMessage msg = new EventMessage();
                msg.setCode("GoodDao_isGood");
                msg.setJson(str);
                EventBus.getDefault().post(msg);
            }
        });
    }

    //点赞
    public void add(int userId, int commentId) {
        Log.e("点赞：", "" + userId + commentId);
        String url = Connect.BASE_URL + "good/add?userId=" + userId + "&commentId=" + commentId;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("点赞：", "" + str);
                EventMessage msg = new EventMessage();
                msg.setCode("GoodDao_add");
                msg.setJson(str);
                EventBus.getDefault().post(msg);
            }
        });
    }

    //取消点赞
    public void del(int userId, int commentId) {
        Log.e("取消点赞：", "" + userId +"To"+ commentId);
        String url = Connect.BASE_URL + "good/del?userId=" + userId + "&commentId=" + commentId;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("取消点赞：", "" + str);
                EventMessage msg = new EventMessage();
                msg.setCode("GoodDao_add");
                msg.setJson(str);
                EventBus.getDefault().post(msg);
            }
        });
    }
}
