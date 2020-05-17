package com.onepilltest.message;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Comment;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Inquiry;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.util.StatusBarUtil;

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

public class QuestionListActivity extends BaseActivity {

    private Response response;
    private int i;
    private ListView listView;
    private QuestionAdapter questionAdapter;
    private List<Inquiry> inquiries = new ArrayList<Inquiry>();
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xff56ced4);
//        }
        setContentView(R.layout.activity_question_list);

        //注册成主线程
        EventBus.getDefault().register(this);
        okHttpClient = new OkHttpClient();
        find();
        initView();
        pull();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //处理点击事件
                Toast.makeText(getApplicationContext(),"点什么点...没实现",Toast.LENGTH_SHORT).show();
            }
        });

        initBar(this);

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_question_list;
    }

    private void initBar(Activity activity) {
        //设置状态栏透明
//        StatusBarUtil.setTranslucentStatus(activity);
        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }

    private void initView() {
        listView = findViewById(R.id.list_question);
        questionAdapter = new QuestionAdapter(inquiries, this, R.layout.listview_question);
        listView.setAdapter(questionAdapter);
    }

    public void pull() {

        //获取数据
        inquiryDao inquiry = new inquiryDao();
        inquiry.findAll();

//        Request request = new Request.Builder().url(Connect.BASE_URL + "PullServlet").build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("拉取失败，", e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String questionListStr = response.body().string();
//                //定义他的派生类调用getType，真实对象
//                Type type = new TypeToken<List<Inquiry>>() {
//                }.getType();
//                inquiries.addAll(new Gson().fromJson(questionListStr, type));
//                //在onResponse里面不能直接更新界面
//                //接收到之后发送消息  通知给主线程
//                EventBus.getDefault().post("问诊");
//            }
//        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage msg) {
        if (msg.getCode().equals("all_inquiry_list")) {
            Type type = new TypeToken<List<Inquiry>>() {}.getType();
            inquiries.clear();
            inquiries.addAll(new Gson().fromJson(msg.getJson(),type));
            //更新视图
            questionAdapter.notifyDataSetChanged();
        }
    }

    private void find() {
        listView = findViewById(R.id.list_question);
    }
}
