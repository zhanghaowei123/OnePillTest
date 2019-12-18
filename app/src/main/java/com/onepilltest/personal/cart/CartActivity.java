package com.onepilltest.personal.cart;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Cart;
import com.onepilltest.entity.Comment;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.medicine_;
import com.onepilltest.index.CommentAdapter;
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

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivCommentLeft;
    private TextView tvSettlementPrice;
    private Button btnCartSettlement;
    private OkHttpClient okHttpClient;
    private ListView cartsListView;
    private CartAdapter cartAdapter;
    private List<medicine_> medicines = new ArrayList<>();
    private medicine_ medicine;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        okHttpClient = new OkHttpClient();
        EventBus.getDefault().register(this);
        sharedPreferences = getSharedPreferences("addToCart", MODE_PRIVATE);
        findViews();
        initView();
        for (int i = 0; i < Cart.medicineList.size(); i++) {
            requestData(Cart.medicineList.get(i));
        }
    }

    private void findViews() {
        ivCommentLeft = findViewById(R.id.cart_back);
        ivCommentLeft.setOnClickListener(this);
        tvSettlementPrice = findViewById(R.id.tv_settlement_price);
        btnCartSettlement = findViewById(R.id.btn_cart_settlement);
        btnCartSettlement.setOnClickListener(this);
    }

    private void initView() {
        cartsListView = findViewById(R.id.lv_mcart);
        cartAdapter = new CartAdapter(medicines, this, R.layout.item_mcart);
        cartsListView.setAdapter(cartAdapter);
    }

    private void requestData(int id) {
        int medicineId = sharedPreferences.getInt("medicineId", 0);
        Request request = new Request.Builder()
                .url(Connect.BASE_URL + "CartServlet?medicineId=" + id)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String medicineListStr = response.body().string();
                //定义他的派生类调用getType，真实对象
                Type type = new TypeToken<List<medicine_>>() {
                }.getType();
                medicines.addAll(new Gson().fromJson(medicineListStr, type));
                //在onResponse里面不能直接更新界面
                //接收到之后发送消息  通知给主线程
                EventBus.getDefault().post("toCart");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(String msg) {
        if (msg.equals("toCart")) {
            //添加到数据库
            //更新视图
            cartAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_back:
                finish();
                break;
        }
    }


}
