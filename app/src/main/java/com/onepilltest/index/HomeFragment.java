package com.onepilltest.index;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.message.QuestionActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private List<Article> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private IndexAdapter indexAdapter;
    ImageView Question;
    MyListener myListener;
    private SharedPreferences sharedPreferences;
    private OkHttpClient okHttpClient;
    private Article article;
    private ImageView imgFoundDoctor;
    private ImageView imgFoundMedicine;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        myListener = new MyListener();
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);
        okHttpClient = new OkHttpClient();
        find(view);
        initView(view);
        setArticles();
        return view;
    }

    private void find(View view) {
        Question = view.findViewById(R.id.iv_inquiry);
        Question.setOnClickListener(myListener);
        imgFoundDoctor = view.findViewById(R.id.iv_find_doctor);
        imgFoundDoctor.setOnClickListener(myListener);
        imgFoundMedicine = view.findViewById(R.id.iv_find_medicine);
        imgFoundMedicine.setOnClickListener(myListener);
    }

    private void setArticles() {
        Request request = new Request.Builder()
                .url(Connect.BASE_URL + "ArticleServlet")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String articleListStr = response.body().string();
                //定义他的派生类调用getType，真实对象
                Type type = new TypeToken<List<Article>>() {
                }.getType();
                articles.addAll((List<Article>) new Gson().fromJson(articleListStr, type));

                //在onResponse里面不能直接更新界面
                //接收到之后发送消息  通知给主线程
                EventBus.getDefault().post("文章");
            }
        });
    }

    //根据参数类型调用
    //消息的处理方法，形参类型同消息一致
    @Subscribe(threadMode = ThreadMode.MAIN)    //设置线程模式为主线程
    public void updateUI(String msg) {
        if (msg.equals("文章")) {
            //更新视图
            indexAdapter.notifyDataSetChanged();
        }
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);
        indexAdapter = new IndexAdapter(getContext(), articles, R.layout.recycle_home_item);
        //设置适配器
        recyclerView.setAdapter(indexAdapter);
        //必须调用，设置布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_inquiry:
                    Intent question_intent = new Intent(getContext(), QuestionActivity.class);
                    startActivity(question_intent);
                    break;
                case R.id.iv_commentImg:
                    Intent intent = new Intent(getContext(), CommentActivity.class);
                    startActivity(intent);
                    break;
                case R.id.iv_find_doctor:
                    Intent inent_findoctor = new Intent();
                    inent_findoctor.setClass(getContext(), FoundDoctorActivity.class);
                    startActivity(inent_findoctor);
                case R.id.iv_find_medicine:
                    Intent intent_findpatient = new Intent();
                    intent_findpatient.setClass(getContext(), FoundPatientActivity.class);
                    startActivity(intent_findpatient);

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}


