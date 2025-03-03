package com.onepilltest.personal.cart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.entity.Dao.CartDao;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.MyCart;
import com.onepilltest.entity.Orders;
import com.onepilltest.entity.medicine_;
import com.onepilltest.entity.Dao.OrdersDao;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class CartActivity extends BaseActivity implements View.OnClickListener {

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

    private List<MyCart> myCarts = null;
    private RecyclerView recyclerView = null;
    private CartNewAdapter cartNewAdapter = null;
    private TextView tvCartManage;
    private Button btnDelete;
    private CheckBox checkBoxAll;
    private TextView tvFinish;
    private List<Boolean> booleanList = new ArrayList<>();
    private CartDao cartDao = new CartDao();

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
        getDate();


        //全选监听器
        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectAll(isChecked);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y=0;
                for (int i=0;i<myCarts.size();i++){
                    if (booleanList.get(i)!=null && booleanList.get(i)){
                        cartDao.del(myCarts.get(i).getId());
                        myCarts.remove(i);
                        booleanList.remove(i);
                        y++;
                        i--;
                    }
                }

//                notify();
                if (y==0){
                    Toast.makeText(getApplicationContext(),"请选择要删除的药品",Toast.LENGTH_SHORT).show();
                }else {
                    cartNewAdapter.notifyDataSetChanged();
                }
            }
        });

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
        return R.layout.activity_cart;
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
        cartNewAdapter = new CartNewAdapter(myCarts, booleanList);
        recyclerView.setAdapter(cartNewAdapter);
//        cartNewAdapter.selectAll();
//        cartNewAdapter.deleteingData();

    }

//    private void requestData(int id) {
//        int medicineId = sharedPreferences.getInt("medicineId", 0);
//        Request request = new Request.Builder()
//                .url(Connect.BASE_URL + "CartServlet?medicineId=" + id)
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String medicineListStr = response.body().string();
//                //定义他的派生类调用getType，真实对象
//                Type type = new TypeToken<List<medicine_>>() {
//                }.getType();
//                medicines.addAll(new Gson().fromJson(medicineListStr, type));
//                //在onResponse里面不能直接更新界面
//                //接收到之后发送消息  通知给主线程
//                EventBus.getDefault().post("toCart");
//            }
//        });
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage message) {
        if (message.getCode().equals("CartDao_getCarts")) {
            //更新视图
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<MyCart>>() {
            }.getType();
            UserBook.myCartList = gson.fromJson(message.getJson(), userListType);
            myCarts = UserBook.myCartList;
            for (MyCart myCart : myCarts) {
                booleanList.add(false);
                price += myCart.getPrice();
            }
            Log.e("总金额：", price + "\n");
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
                for (int i = 0; i < myCarts.size(); i++) {
                    //将MyCart转换成Order对象
                    orders.setUserId(UserBook.NowUser.getId());
                    orders.setMedicineId(myCarts.get(i).getMedicineId());
                    orders.setImg(myCarts.get(i).getImg());
                    orders.setCount(myCarts.get(i).getCount());
                    orders.setStatus(0);
                    orders.setAddressId(0);
//                  cartDao添加deletByUserId

                    dao.add(orders);

                }
                cartDao.deleteByUserId(UserBook.NowUser.getId());
                Toast.makeText(getApplicationContext(), "订单结算成功", Toast.LENGTH_SHORT).show();
                EventMessage msg = new EventMessage();
                msg.setCode("update_wallet");
                msg.setJson("" + price);
                int all = UserBook.money;
                UserBook.money = all - price;
                EventBus.getDefault().post(msg);
                myCarts.clear();
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
    public void getDate() {
        cartDao.getCarts();
    }




    //更改集合内部存储的状态
    public void initCheck(boolean flag) {

        for (int i = 0; i < myCarts.size(); i++) {
            //更改指定位置的数据
            booleanList.set(i, flag);
        }
    }

    //全选
    public void selectAll(boolean f) {
        initCheck(f);
        cartNewAdapter.notifyDataSetChanged();
    }
}
