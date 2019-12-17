package com.onepilltest.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.index.IndexAdapter;
import com.onepilltest.message.QuestionActivity;
import com.onepilltest.personal.cart.ShoppingCartActivity;
import com.onepilltest.personal.oder.MyOrdersActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends Fragment {

    private List<Article> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private IndexAdapter indexAdapter;
    private ImageView setting;
    private LinearLayout order;
    private LinearLayout cart;
    private LinearLayout wallet;
    private LinearLayout ask;
    private LinearLayout help;
    private LinearLayout focus;
    private ImageView iv_personal = null;
    private TextView name = null;
    private TextView degree = null;
    MyListener myListener = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /**
     * 绑定ID，监听器
     *
     * @param view
     */
    private void find(View view) {
        name = view.findViewById(R.id.personal_name);
        degree = view.findViewById(R.id.personal_work);
        degree.setText(UserBook.getDegree());
        iv_personal = view.findViewById(R.id.iv_personal);
        setting = view.findViewById(R.id.iv_setting);
        setting.setOnClickListener(myListener);
        order = view.findViewById(R.id.ll_order);
        order.setOnClickListener(myListener);
        cart = view.findViewById(R.id.ll_cart);
        cart.setOnClickListener(myListener);
        wallet = view.findViewById(R.id.ll_wallet);
        wallet.setOnClickListener(myListener);
        /*ask = view.findViewById(R.id.ll_ask);
        ask.setOnClickListener(myListener);*/
        help = view.findViewById(R.id.ll_help);
        help.setOnClickListener(myListener);
        focus = view.findViewById(R.id.ll_sc);
        focus.setOnClickListener(myListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.personal, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        if (UserBook.Code == 1){//医生
            initDoctor();
        }else if(UserBook.Code ==2){//用户
            initPatient();
        }
    }

    private void initDoctor() {
        name.setText(UserBook.NowDoctor.getName());
    }

    private void initPatient() {
        name.setText(UserBook.NowUser.getNickName());
    }

    private void initView(View view) {

        myListener = new MyListener();
        find(view);

        if(UserBook.Code == 1){
            RequestOptions requestOptions = new RequestOptions().circleCrop();
            Glide.with(this)
                    .load(Connect.BASE_URL + UserBook.NowDoctor.getHeadImg())
                    .apply(requestOptions)
                    .into(iv_personal);
        }else if(UserBook.Code == 2){
            RequestOptions requestOptions = new RequestOptions().circleCrop();
            Glide.with(this)
                    .load(Connect.BASE_URL + UserBook.NowUser.getHeadImg())
                    .apply(requestOptions)
                    .into(iv_personal);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage msg) {

        if(UserBook.Code == 1){
            //Log.e("person更新", msg.getCode() + msg.getJson());
            if (msg.getCode() == "更新头像") {
                if (msg.getJson() == "yes") {
                    RequestOptions requestOptions = new RequestOptions().circleCrop();
                    Glide.with(this)
                            .load(Connect.BASE_URL + UserBook.NowDoctor.getHeadImg())
                            .apply(requestOptions)
                            .into(iv_personal);
                }
            }
        }else if(UserBook.Code == 2){
            //Log.e("person更新", msg.getCode() + msg.getJson());
            if (msg.getCode() == "更新头像") {
                if (msg.getJson() == "yes") {
                    RequestOptions requestOptions = new RequestOptions().circleCrop();
                    Glide.with(this)
                            .load(Connect.BASE_URL + UserBook.NowUser.getHeadImg())
                            .apply(requestOptions)
                            .into(iv_personal);
                }
            }
        }


    }

    /**
     * 自定义监听器
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_setting:
                    Intent intent = new Intent(getContext(), SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_order:
                    Intent intent1 = new Intent(getContext(), MyOrdersActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.ll_cart:
                    Intent intent2 = new Intent(getContext(), ShoppingCartActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.ll_wallet:
                    Intent intent3 = new Intent(getContext(), WalletActivity.class);
                    startActivity(intent3);
                    break;
                /*case R.id.ll_ask:
                    Intent intent4 = new Intent(getContext(), recordActivity.class);
                    startActivity(intent4);
                    break;*/
                case R.id.ll_help:
                    Intent intent5 = new Intent(getContext(), HelpAndFeedBackActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.ll_sc:
                    Intent intent6 = new Intent(getContext(), FocusListActivity.class);
                    startActivity(intent6);
                    break;
            }
        }
    }
}
