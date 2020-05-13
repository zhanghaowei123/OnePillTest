package com.onepilltest.index;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.Comment;
import com.onepilltest.entity.Dao.GoodDao;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.personal.UserBook;
import com.onepilltest.welcome.PerfectInforPatientActivity;
import com.onepilltest.welcome.UserSuccessActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ivCommentLeft;
    private Button btnSendComment;
    private OkHttpClient okHttpClient;
    private List<Comment> comments = new ArrayList<>();
    private EditText etComment;
    private Comment comment;
    private String id;

    private RecyclerView recyclerView = null;
    private CommentNewAdapter commentNewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffffffff);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_comment);

        GoodDao.goodMap.clear();
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);
        okHttpClient = new OkHttpClient();
        findViews();
        initView();
        requestData();


    }

    private void requestData() {
//        Log.e("获取评论:",Connect.BASE_URL + "comment/getComment?articleId=" + comment.getArticleId());
        int id = 0;
        if (UserBook.Code == 1){
            id = UserBook.NowDoctor.getId();
        }else {
            id = UserBook.NowUser.getId();
        }
        String url = Connect.BASE_URL + "comment/getComment?articleId=" + comment.getArticleId()+" &userId="+id+"&userType="+UserBook.Code;
        Request request = new Request.Builder()
                .url(url)
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
                Log.e("CommentList", commentListStr.toString());
                //定义他的派生类调用getType，真实对象
                Type type = new TypeToken<List<Comment>>() {
                }.getType();
                comments.addAll(new Gson().fromJson(commentListStr, type));
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
            commentNewAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
//        commentListView = findViewById(R.id.lv_comment);
//        commentAdapter = new CommentAdapter(comments, this, R.layout.item_comment);
//        commentListView.setAdapter(commentAdapter);
        recyclerView = findViewById(R.id.lv_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        commentNewAdapter = new CommentNewAdapter(comments);
        recyclerView.setAdapter(commentNewAdapter);
    }

    public void findViews() {
        etComment = findViewById(R.id.et_comment);
        ivCommentLeft = findViewById(R.id.iv_comment_left);
        btnSendComment = findViewById(R.id.btn_send_comment);
        Intent intent = getIntent();
        id = intent.getStringExtra("articleId");
        comment = new Comment();
        comment.setArticleId(Integer.parseInt(id));
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
                if (etComment.getText().toString().length() > 100) {
                    Toast.makeText(getApplicationContext(), "评论字数最多不超过100字符。", Toast.LENGTH_SHORT).show();
                }

                if (!etComment.getText().toString().equals("")) {
                    if (UserBook.Code == 2) {
                        Log.e("当前评论用户：", UserBook.NowUser.getNickName());
                        comment.setCcomment(etComment.getText().toString());
                        comment.setArticleId(Integer.parseInt(id));
                        comment.setUserId(UserBook.NowUser.getId());
                        comment.setUserType(2);
                        comments.add(comment);
                        //更新到数据库
                        insertComment();
                    } else if (UserBook.Code == 1) {
                        UserDoctor doctor = (UserDoctor) UserBook.getNowUser();
                        comment.setCcomment(etComment.getText().toString());
                        comment.setArticleId(Integer.parseInt(id));
                        comment.setUserType(1);
                        comment.setUserId(UserBook.NowDoctor.getId());
                        comments.add(comment);
                        //更新到数据库
                        insertComment();
                    } else {
                        Toast.makeText(getApplicationContext(), "错误!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    break;
                }
                Log.e("comment", comment.toString());
                etComment.clearFocus();
                etComment.setText("");
                comment = new Comment();
                comment.setArticleId(Integer.parseInt(id));
                commentNewAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void insertComment() {
        String jsonStr = null;
        jsonStr = new Gson().toJson(comment);
        Log.e("添加评论：\n", jsonStr.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                jsonStr);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Connect.BASE_URL + "comment/add")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Comment_false", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功时回调
                String isSuccessful = response.body().string();
                if (isSuccessful.equals("true")) {
                    Log.e("Comment_successful", isSuccessful);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

//        点赞事务
        GoodDao.post();
    }

}
