package com.onepilltest.entity.Dao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.util.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ArticleDao {

    //获取全部文章
    public void getAllArticles(){
        String url = Connect.BASE_URL+"article/list";
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("ArticleDao","list:"+"\n"+str);
                EventMessage msg = new EventMessage();
                msg.setJson(str);
                msg.setCode("articleDao_list");
                EventBus.getDefault().post(msg);
            }
        });
    }
    //根据Tag获取文章
    //根据收藏的文章获取文章
    //新增文章
    //删除文章

}
