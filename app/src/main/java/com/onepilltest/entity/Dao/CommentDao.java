package com.onepilltest.entity.Dao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Comment;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentDao {

    Gson gson = new Gson();

    //获取文章所有评论
    public void getComment(int articleId,List<Comment> comments){
        int id = 0;
        if (UserBook.Code == 1) {
            id = UserBook.NowDoctor.getId();
        } else {
            id = UserBook.NowUser.getId();
        }
        String url = Connect.BASE_URL + "comment/getComment?articleId=" + articleId + " &userId=" + id + "&userType=" + UserBook.Code;
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String commentListStr = response.body().string();
                Log.e("CommentList", commentListStr.toString());



                EventMessage msg = new EventMessage();
                msg.setCode("评论");
                msg.setJson(commentListStr);

                //在onResponse里面不能直接更新界面
                //接收到之后发送消息  通知给主线程
                EventBus.getDefault().post(msg);
            }
        });
    }
    //插入评论
    public void add(Comment comment){
        Log.e("CommentDao","插入"+comment.toString());
        String url = Connect.BASE_URL + "comment/add";
        RequestBody body = new FormBody.Builder()
                .add("json",gson.toJson(comment))
                .build();

        OkhttpUtil.post(body,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Comment_false", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功时回调
                String isSuccessful = response.body().string();
                    Log.e("Comment_successful", isSuccessful);

            }
        });
    }
    //更新评论
    //根据UserId添加评论
}
