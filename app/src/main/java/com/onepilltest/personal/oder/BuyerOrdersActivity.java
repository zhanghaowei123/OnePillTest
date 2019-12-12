package com.onepilltest.personal.oder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.personal.PersonalFragment;

import java.util.HashMap;
import java.util.Map;

public class BuyerOrdersActivity extends AppCompatActivity{

    private ImageView ivback;
    private MyListener myListener;
    public static final String ORDER_LIST_CON_IP= Connect.BASE_URL+"buyer/order/show";
    private Button btnOrderAll = null;
    private Button btnOrderUnPay = null;
    private Button btnOrderUnsend = null;
    private Button btnOrderWaitGet = null;
    private Button btnOrderFinish = null;
    private Fragment curFragment ;
    private String enterFragmentId = null;
    private Map<String, OrderTabSpec> map = new HashMap<>();
    public static final String SHOW_ALL = "all";
    public static final String SHOW_UNPAY = "unpay";
    public static final String SHOW_UNSEND = "unsend";
    public static final String SHOW_WAITGET = "waitget";
    public static final String SHOW_FINISH = "finish";
    public static final int DETAIL_TO_INDEX = 200000;
    private String[] tabStrId = {SHOW_ALL,SHOW_UNPAY,SHOW_UNSEND,SHOW_WAITGET,SHOW_FINISH};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_patient);

        myListener = new MyListener();
        init();
        initOtherData();
        //设置初始界面展示
        changeTab(enterFragmentId);
    }

    private void init() {
        ivback = findViewById(R.id.order_back);
        btnOrderAll = findViewById(R.id.btn_order_all);
        btnOrderUnPay = findViewById(R.id.btn_order_unpay);
        btnOrderUnsend = findViewById(R.id.btn_order_unsend);
        btnOrderWaitGet = findViewById(R.id.btn_order_waitget);
        btnOrderFinish = findViewById(R.id.btn_order_finish);
        enterFragmentId = getIntent().getStringExtra("enterFragmentId");
        ivback.setOnClickListener(myListener);
        btnOrderAll.setOnClickListener(myListener);
        btnOrderUnPay.setOnClickListener(myListener);
        btnOrderUnsend.setOnClickListener(myListener);
        btnOrderWaitGet.setOnClickListener(myListener);
        btnOrderFinish.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.order_back:
                    Intent intent = new Intent();
                    intent.setClass(BuyerOrdersActivity.this,PersonalFragment.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn_order_all:
                    changeTab(tabStrId[0]);
                    break;
                case R.id.btn_order_unpay:
                    changeTab(tabStrId[1]);
                    break;
                case R.id.btn_order_unsend:
                    changeTab(tabStrId[2]);
                    break;
                case R.id.btn_order_waitget:
                    changeTab(tabStrId[3]);
                    break;
                case R.id.btn_order_finish:
                    changeTab(tabStrId[4]);
                    break;
            }
        }
    }
    private void changeTab(String s){
        //切换成对应Fragment
        Fragment fragment = map.get(s).getFragment();
        if (fragment == curFragment) return;
        //通过Fragment事务更改为显示Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏当前Fragment
        if (curFragment != null){
            transaction.hide(curFragment);
        }
        //若没有被添加过，则添加至帧布局
        if (!fragment.isAdded()){
            transaction.add(R.id.order_tab_widget,fragment);
        }
        //显示选中的Fragment,修改相关属性
        transaction.show(fragment);
        curFragment =fragment;
        transaction.commit();
        //修改对应background信息
        for (String key: map.keySet())
            map.get(key).setSelected(false);
        map.get(s).setSelected(true);
    }
    private void initOtherData(){
        OrderTabSpec spec = new OrderTabSpec();
        spec.setBtnChoose(btnOrderAll);
        spec.setFragment(new AllFragment());
        map.put(tabStrId[0],spec);
        OrderTabSpec spec2 = new OrderTabSpec();
        spec2.setBtnChoose(btnOrderUnPay);
        spec2.setFragment(new UnpayFragment());
        map.put(tabStrId[1],spec2);
        OrderTabSpec spec3 = new OrderTabSpec();
        spec3.setFragment(new UnsendFragment());
        spec3.setBtnChoose(btnOrderUnsend);
        map.put(tabStrId[2],spec3);
        OrderTabSpec spec4 = new OrderTabSpec();
        spec4.setBtnChoose(btnOrderWaitGet);
        spec4.setFragment(new WaitGetFragment());
        map.put(tabStrId[3],spec4);
        OrderTabSpec spec5 = new OrderTabSpec();
        spec5.setBtnChoose(btnOrderFinish);
        spec5.setFragment(new FinishFragment());
        map.put(tabStrId[4],spec5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case DETAIL_TO_INDEX:
                enterFragmentId = data.getStringExtra("enterFragemntId");
                changeTab(enterFragmentId);
                break;
        }
    }
}
