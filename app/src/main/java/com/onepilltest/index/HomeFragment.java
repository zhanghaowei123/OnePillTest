package com.onepilltest.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.onepilltest.R;
import com.onepilltest.message.QuestionActivity;
import com.onepilltest.personal.AddressActivity;
import com.onepilltest.personal.EditAddressActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Article> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private IndexAdapter indexAdapter;
    ImageView Question;
    MyListener myListener;

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
        find(view);
        initData();
        initView(view);
        return view;
    }

    private void find(View view) {
        Question = view.findViewById(R.id.iv_inquiry);
        Question.setOnClickListener(myListener);
    }

    private void initData() {
        Article article1 = new Article("张昊伟", "这是内容",
                "生活常识", "这是热门评论");
        Article article2 = new Article("lalal", "内容", "医疗", "pinglun");
        articles.add(article1);
        articles.add(article2);
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
            switch (v.getId()){
                case R.id.iv_inquiry:
                    Intent question_intent = new Intent(getContext(), QuestionActivity.class);
                    startActivity(question_intent);
                    break;
            }
        }
    }
}


