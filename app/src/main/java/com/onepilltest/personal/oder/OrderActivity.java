package com.onepilltest.personal.oder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.entity.Dao.OrdersDao;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Orders;
import com.onepilltest.personal.UserBook;
import com.onepilltest.personal.cart.CartNewAdapter;
import com.onepilltest.personal.orderAdapter;
import com.onepilltest.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

    Button back;
    RecyclerView orderList = null;
    MyListener myListener = null;
    OrdersAdapter ordersAdapter = null;
    Gson gson = new Gson();
    private List<Orders> baseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xffffffff);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.setting_order);
        myListener = new MyListener();
        find();
        init();

        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);
        initBar(this);
    }


    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }

    @Override
    public int intiLayout() {
        return R.layout.setting_order;
    }


    public void init() {
        if (UserBook.Code == 1) {//医生
            initDoctor();
        } else if (UserBook.Code == 2) {//用户
            initPatient();
        }
    }

    private void initDoctor() {
        //???


    }

    //初始化用户信息
    private void initPatient() {
        OrdersDao dao = new OrdersDao();
        dao.searchAll(UserBook.NowUser.getId());

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpdateUI(EventMessage msg) {
        Log.e("OrderActivity", "" + msg.getCode()+msg.getJson());
        if (msg.getCode().equals("OrdersDao_searchAll")) {
            List<Orders> list = gson.fromJson(msg.getJson(), new TypeToken<List<Orders>>() {
            }.getType());
            baseList.clear();
            baseList.addAll(list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            orderList.setLayoutManager(layoutManager);
            ordersAdapter = new OrdersAdapter(baseList);
            orderList.setAdapter(ordersAdapter);
            initItem();
        }
    }

    private void initItem() {
        ordersAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Orders orders, int position) {
                //跳转到详情页
                Toast.makeText(OrderActivity.this,"请付款",Toast.LENGTH_SHORT).show();
            }
        });
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
