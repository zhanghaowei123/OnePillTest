package com.onepilltest.personal;

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

import com.onepilltest.R;
import com.onepilltest.index.Article;
import com.onepilltest.index.IndexAdapter;
import com.onepilltest.message.QuestionActivity;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends Fragment{

    private List<Article> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private IndexAdapter indexAdapter;
    Button bQuestion = null;
    Button bSetting = null;
    Button bWallet = null;
    Button bAddress = null;
    MyListener myListener = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    /**
     * 绑定ID，监听器
     * @param view
     */
    private void find(View view) {
        bQuestion = view.findViewById(R.id.zanding_question);
        bQuestion.setOnClickListener(myListener);
        bSetting = view.findViewById(R.id.zanding_setting);
        bSetting.setOnClickListener(myListener);
        bWallet = view.findViewById(R.id.zanding_wallet);
        bWallet.setOnClickListener(myListener);
        bAddress = view.findViewById(R.id.zanding_user_address);
        bAddress.setOnClickListener(myListener);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.person_zanding, container, false);

        initData();
        initView(view);
        return view;
    }

    private void initData() {

    }

    private void initView(View view) {

        myListener = new MyListener();
        find(view);
    }

    /**
     * 自定义监听器
     */
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.zanding_question:
                    Intent question_intent = new Intent(getContext(), QuestionActivity.class);
                    startActivity(question_intent);
                    break;
                case R.id.zanding_setting:
                    Intent setting_intent = new Intent(getContext(), SettingActivity.class);
                    startActivity(setting_intent);
                    break;
                case R.id.zanding_wallet:
                    Intent wallet_intent = new Intent(getContext(), WalletActivity.class);
                    startActivity(wallet_intent);
                    break;
                case R.id.zanding_user_address:
                    Intent address_intent = new Intent(getContext(), AddressActivity.class);
                    startActivity(address_intent);
            }
        }
    }
}
