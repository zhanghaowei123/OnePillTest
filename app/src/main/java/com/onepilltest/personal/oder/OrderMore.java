package com.onepilltest.personal.oder;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Dao.MedicineDao;
import com.onepilltest.entity.Dao.OrdersDao;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Orders;
import com.onepilltest.entity.medicine_;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OrderMore extends BaseActivity {

    private Orders orders = new Orders();
    private ImageView img = null;
    private TextView name = null;
    private TextView more = null;
    private Button btn = null;
    private Button back = null;
    private MyListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initBar(this);
        myListener = new MyListener();
        find();
        init();

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_order_more;
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        StatusBarUtil.setStatusBarColor(activity,0xffffffff);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }

    private void find() {
        img = findViewById(R.id.order_more_img);
        name = findViewById(R.id.order_more_name);
        more = findViewById(R.id.order_more_more);
        btn = findViewById(R.id.order_more_pay);
        back = findViewById(R.id.order_more_back);
        btn.setOnClickListener(myListener);
        back.setOnClickListener(myListener);
    }

    public void init(){
        String json = getIntent().getStringExtra("json");
        orders = new Gson().fromJson(json,Orders.class);
        Log.e("OrdersMore",json);

        Glide.with(getApplicationContext())
                .load(Connect.BASE_URL+orders.getImg())
                .into(img);
        new MedicineDao().searchMedicineById(orders.getMedicineId());
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.order_more_back:
                    finish();
                    break;
                case R.id.order_more_pay:
                    Toast.makeText(getApplicationContext(),"已支付"+orders.getPrice(),Toast.LENGTH_LONG).show();
                    orders.setStatus(1);
                    new OrdersDao().add(orders);
                    break;
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(EventMessage msg){
        if (msg.getCode().equals("MedicineDao_searchMedicineById")){
            if (!msg.getJson().equals("no")){
                medicine_ medicine = new medicine_();
                medicine = new Gson().fromJson(msg.getJson(),medicine_.class);
                name.setText(medicine.getGeneralName());
                more.setText(medicine.getOverview());
            }
        }
    }
}
