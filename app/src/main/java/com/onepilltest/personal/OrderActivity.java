package com.onepilltest.personal;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Orders;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    Button back;
    ListView orderList = null;
    MyListener myListener = null;
    BaseAdapter adapter = null;
    Gson gson = new Gson();
    private List<Orders> baseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffffffff);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.setting_order);
        myListener = new MyListener();
        find();
        init();
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);
    }


    public void init() {
        if (UserBook.Code == 1) {//医生
            initDoctor();
        } else if (UserBook.Code == 2) {//用户
            initPatient();
        }
    }

    private void initDoctor() {


    }

    //初始化用户信息
    private void initPatient() {
        OrdersDao dao = new OrdersDao();
        dao.searchAll(UserBook.NowUser.getUserId());
        //创建ContentAdapter实例，传入上下文， 子布局id ,数据baseList
        adapter = new orderAdapter(OrderActivity.this, R.layout.order_list_item, baseList);

        //orderList = (ListView) findViewById(R.id.setting_order_list);
        orderList.setAdapter(adapter);//绑定适配器


        //item点击监听器
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("click", "点击第" + position + "个item");

                //点击事件监听器


            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpdateUI(EventMessage msg) {
        Log.e("OrderActivity", "" + msg.getCode()+msg.getJson());
        if (msg.getCode().equals("OrdersDao_searchAll")) {
            List<Orders> list = gson.fromJson(msg.getJson(), new TypeToken<List<Orders>>() {
            }.getType());
            baseList.clear();
            baseList.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }


    private void find() {
        back = findViewById(R.id.setting_order_back);
        back.setOnClickListener(myListener);
        orderList = findViewById(R.id.setting_order_list);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_order_back:
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


}
