package com.onepilltest.index;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.Comment;
import com.onepilltest.personal.UserBook;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivCommentLeft;
    private Button btnSendComment;
    private OkHttpClient okHttpClient;
    private List<Comment> comments = new ArrayList<>();
    private ListView commentListView;
    private CommentAdapter commentAdapter;
    private EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);
        okHttpClient = new OkHttpClient();
        findViews();
        initView();
        requestData();
    }

    private void requestData() {
        Request request = new Request.Builder()
                .url(Connect.BASE_URL + "CommentServlet")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String commentListStr = response.body().string();
                //定义他的派生类调用getType，真实对象
                Type type = new TypeToken<List<Comment>>() {
                }.getType();
                comments.addAll((List<Comment>) new Gson().fromJson(commentListStr, type));
                Log.e("comment", commentListStr.toString());
                //在onResponse里面不能直接更新界面
                //接收到之后发送消息  通知给主线程
                EventBus.getDefault().post("评论");
            }
        });
    }

    //根据参数类型调用
    //消息的处理方法，形参类型同消息一致
    @Subscribe(threadMode = ThreadMode.MAIN)    //设置线程模式为主线程
    public void updateUI(String msg) {
        if (msg.equals("评论")) {
            //更新视图
            commentAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        commentListView = findViewById(R.id.lv_comment);
        commentAdapter = new CommentAdapter(comments, this, R.layout.item_comment);
        commentListView.setAdapter(commentAdapter);
    }

    public void findViews() {
        etComment = findViewById(R.id.et_comment);
        ivCommentLeft = findViewById(R.id.iv_comment_left);
        btnSendComment = findViewById(R.id.btn_send_comment);
        ivCommentLeft.setOnClickListener(this);
        btnSendComment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_comment_left:
                Intent intent = new Intent(CommentActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_send_comment:
                Comment comment = new Comment();
                comment.setName(UserBook.NowUser.getNickName());
                comment.setCcomment(etComment.getText().toString());
                comments.addAll((List<Comment>) comment);
                commentAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
