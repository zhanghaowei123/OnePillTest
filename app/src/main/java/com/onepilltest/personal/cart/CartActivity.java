package com.onepilltest.personal.cart;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Cart;
import com.onepilltest.entity.Comment;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.MyCart;
import com.onepilltest.entity.Orders;
import com.onepilltest.entity.medicine_;
import com.onepilltest.index.CommentAdapter;
import com.onepilltest.personal.OrdersDao;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.OkhttpUtil;

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
    private Button settlement;
    private List<medicine_> medicines = new ArrayList<>();
    private medicine_ medicine;
    private SharedPreferences sharedPreferences;
    private int price = 0;

    private List<MyCart> myCarts = new ArrayList<>();
    private RecyclerView recyclerView = null;
    private CartNewAdapter cartNewAdapter= null;
    private TextView tvCartManage;
    private Button btnDelete;
    private CheckBox checkBoxAll;
    private TextView tvFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xff56ced4);
//        }

        setContentView(R.layout.activity_cart);
        okHttpClient = new OkHttpClient();
        EventBus.getDefault().register(this);
        sharedPreferences = getSharedPreferences("addToCart", MODE_PRIVATE);
        findViews();
//        initView();
        getDate();
//        for (int i = 0; i < Cart.medicineList.size(); i++) {
//            requestData(Cart.medicineList.get(i));
//
//        }
    }
    private void findViews() {
        settlement = findViewById(R.id.btn_cart_settlement);
        ivCommentLeft = findViewById(R.id.cart_back);
        ivCommentLeft.setOnClickListener(this);
        tvSettlementPrice = findViewById(R.id.tv_settlement_price);
        btnCartSettlement = findViewById(R.id.btn_cart_settlement);
        btnCartSettlement.setOnClickListener(this);
        tvCartManage = findViewById(R.id.tv_cart_manage1);
        tvCartManage.setOnClickListener(this);
        btnDelete = findViewById(R.id.btn_delete1);
        btnDelete.setOnClickListener(this);
        checkBoxAll = findViewById(R.id.chbox_choose_all);
        checkBoxAll.setOnClickListener(this);
        tvFinish = findViewById(R.id.tv_cart_finish1);
        tvFinish.setOnClickListener(this);
    }

    private void initView() {
        Log.e("init数据:",myCarts.size()+"");
//        cartsListView = findViewById(R.id.lv_mcart);
//        cartAdapter = new CartAdapter(myCarts, this, R.layout.item_mcart,checkBoxAll
//        ,btnDelete);
//        cartsListView.setAdapter(cartAdapter);
//        cartAdapter.setBtnDeleteListener();
//        cartAdapter.setCheckBoxItemAll();
//        cartAdapter.setSelectCartItemMap();
        recyclerView = findViewById(R.id.lv_mcart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cartNewAdapter = new CartNewAdapter(myCarts);
        recyclerView.setAdapter(cartNewAdapter);
//        cartNewAdapter.selectAll();
//        cartNewAdapter.deleteingData();

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
    public void updateUI(EventMessage message) {
        if (message.getCode().equals("toCart")) {
            //更新视图
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<MyCart>>(){}.getType();
            myCarts = gson.fromJson(message.getJson(),userListType);
            for (MyCart myCart:myCarts){
                Log.e("数据：",myCart.toString());
            }
            for (MyCart myCart:myCarts){
                price += myCart.getPrice();
            }
            Log.e("总金额：", price+"\n");
            initView();
//            cartAdapter.notifyDataSetChanged();
            cartNewAdapter.notifyDataSetChanged();
            tvSettlementPrice.setText("￥" + price);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_back:
                finish();
                break;
            case R.id.btn_cart_settlement:
                OrdersDao dao = new OrdersDao();
                Orders orders = new Orders();
                for(int i=0;i<medicines.size();i++){
                    orders.setUserId(UserBook.NowUser.getId());
                    orders.setMedicineId(medicines.get(i).getId());
                    orders.setImg(medicines.get(i).getImg1());
                    orders.setCount(2);
                    orders.setStatus(1);
                    dao.add(orders);
                }
                Toast.makeText(getApplicationContext(),"订单结算成功",Toast.LENGTH_SHORT).show();
                EventMessage msg = new EventMessage();
                msg.setCode("update_wallet");
                msg.setJson(""+price);
                int all = UserBook.money;
                UserBook.money = all-price;
                EventBus.getDefault().post(msg);
                medicines.clear();
                cartNewAdapter.notifyDataSetChanged();
                break;
                //new
            case R.id.tv_cart_manage1:// 当管理按钮被点下时，可进行删除操作
                tvCartManage.setVisibility(View.GONE);
                tvFinish.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                btnCartSettlement.setVisibility(View.VISIBLE);
                tvSettlementPrice.setVisibility(View.INVISIBLE);
                break;
            case  R.id.tv_cart_finish1:// 当修改完成时，可进行下单操作
                tvFinish.setVisibility(View.GONE);
                tvCartManage.setVisibility(View.VISIBLE);
                btnCartSettlement.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);
                tvSettlementPrice.setVisibility(View.VISIBLE);
                break;
        }
    }

    //获取购物车
    public void getDate(){
        String url = Connect.BASE_URL+"cart/findByUserId?userId="+UserBook.NowUser.getId();
        OkhttpUtil.get(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("根据userId获取购物车信息：","获取失败。");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                Log.e("根据userId获取购物车信息：",re);
                EventMessage message = new EventMessage();
                message.setCode("toCart");
                message.setJson(re);
                EventBus.getDefault().post(message);
            }
        });
    }


}
