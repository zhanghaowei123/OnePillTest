package com.onepilltest.personal.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onepilltest.R;
import com.onepilltest.URL.ConUtil;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.Cart;
import com.onepilltest.entity.Order;
import com.onepilltest.personal.UserBook;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {
    private ImageView imgBack;
    private RelativeLayout relativeLayout;
    private TextView tvOrderName;
    private TextView tvOrderPhone;
    private ImageView imgDefault;
    private TextView tvOrderAddress;
    private ListView lvPlaceOrder;
    private TextView tvOrderPrice;
    private Button btnSubmitOrder;
    private SharedPreferences sharedPreferences = null;
    private List<Cart> buyCarts = new ArrayList<>();
    private Address buyerAddress = new Address();

    public static final String FROM_PLACE_ORDER = "fromPlaceOrder";
    public static final int ADDRESS_BACK_TO_PLACE = 200;
    public static final int PLACE_TO_ADDRESS = 100;

    private PlaceOrderAdapter adapter = null;
    private String fromWhere;

    public static final String NEW_UNSEND_IP = Connect.BASE_IP + "buyer/order/new/unsend";
    public static final String NEW_UNPAY_IP = Connect.BASE_IP + "buyer/order/new/unpay";
    public static final String DELETE_CART_IP = Connect.BASE_IP + "CartDeleteServlet";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        //初始化控件，获得初始信息
        imgBack = findViewById(R.id.iv_place_order_exit);
        relativeLayout = findViewById(R.id.rl_change_address);
        tvOrderName = findViewById(R.id.tv_order_address_name);
        tvOrderPhone = findViewById(R.id.tv_order_address_phone);
        tvOrderAddress = findViewById(R.id.tv_order_address_pdad);
        tvOrderPrice = findViewById(R.id.tv_order_price);
        imgDefault = findViewById(R.id.iv_order_address_default);
        lvPlaceOrder = findViewById(R.id.order_medicine_buyer);
        btnSubmitOrder = findViewById(R.id.btn_submit_order);
        sharedPreferences = getSharedPreferences("用户登录", MODE_PRIVATE);
        buyCarts = (List<Cart>) getIntent().getSerializableExtra("buyCart");
        fromWhere = getIntent().getStringExtra("fromWhere");
        // 设置初始数据
//        setInitData();

//        //设置点击事件监听器
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (fromWhere.equals(CartActivity.FROM_CART)) {
//                    //若从购物车中启动该界面，则删除购物车中对应数据
//                    deleteChooseCartFromCartList();
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceOrderActivity.this);
//                builder.setTitle("确认付款");
//                builder.setPositiveButton("确认支付", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //选中确认按钮，生成一个代发货的Order
//                        for (Cart cart : buyCarts) {
//                            Order order = new Order();
//                            order.setMedicine(cart.getMedicine());
//                            order.setType(Order.TYPE_UNSEND);
//                            order.setDoctorId(cart.getMedicine().getDoctorID());
//                            order.setAddress(buyerAddress);
//                            order.setUserId(UserBook.NowUser.getId());
//                            sendNewOrderToServlet(NEW_UNSEND_IP, order);
//                        }
//                        Toast.makeText(PlaceOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                });
//                builder.setNegativeButton("取消支付", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //选中取消按钮，生成一个代付款的Order
//                        for (Cart cart : buyCarts) {
//                            Order order = new Order();
//                            order.setMedicine(cart.getMedicine());
//                            order.setType(Order.TYPE_UNPAY);
//                            order.setCount(cart.getMedicine().getDoctorID());
//                            order.setAddress(buyerAddress);
//                            order.setUserId(UserBook.NowUser.getId());
//                            sendNewOrderToServlet(NEW_UNPAY_IP, order);
//                        }
//                        Toast.makeText(PlaceOrderActivity.this, "支付失败，已放入未付款订单", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                });
//                builder.create().show();
//            }
//        });
//    }
//
//    private void sendNewOrderToServlet(final String CON_IP, final Order order) {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(CON_IP);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("POST");
//                    JSONObject send = new JSONObject();
//                    send.put("type", order.getType());
//                    send.put("buyerId", order.getUserId());
//                    send.put("doctorId", order.getDoctorId());
//                    send.put("count", order.getCount());
//                    send.put("medicineId", order.getMedicine().getId().get(0));
//                    send.put("addressId", order.getAddress().getId());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    private void deleteChooseCartFromCartList() {
//        for (Cart cart : buyCarts) {
//            final int id = cart.getId();
//            new Thread() {
//                @Override
//                public void run() {
//                    // 向服务器端发送消息，删除对应 Id 的cart信息
//                    try {
//                        URL url = new URL(DELETE_CART_IP);
//                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                        con.setRequestMethod("POST");
//                        JSONObject send = new JSONObject();
//                        send.put("id", id);
//                        ConUtil.setOutputStream(con, send.toString());
//                        ConUtil.getInputStream(con);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
//        }
//    }
//
//    private void setInitData() {
//        //配置药品列表
//        adapter = new PlaceOrderAdapter(PlaceOrderActivity.this, buyCarts, R.layout.listview_place_item);
//        lvPlaceOrder.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(lvPlaceOrder);
//        // 设置价格
//        int totalPrice = 0;
//        for (Cart cart : buyCarts) {
//            totalPrice += cart.getCount() * cart.getMedicine().getPrice().get(0);
//        }
//        Log.e("totalPrice", totalPrice + "");
//        tvOrderPrice.setText("￥" + totalPrice + "");
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode) {
//            case ADDRESS_BACK_TO_PLACE:
//                //在地址返回界面返回某一地址
//                buyerAddress = (Address) data.getSerializableExtra("address");
//        }
//    }
//
//    //设置listView高度
//    public void setListViewHeightBasedOnChildren(ListView listView) {
//        try {
//            //获取listview对应的adapter
//            if (adapter == null) {
//                return;
//            }
//            int totalHeight = 0;
//            for (int i = 0, len = adapter.getCount(); i < len; i++) {
//                // listAdapter.getCount()返回数据项的数目
//                View listItem = adapter.getView(i, null, listView);
//                // 计算子项View 的宽高
//                listItem.measure(0, 0);
//                // 统计所有子项的总高度
//                totalHeight += listItem.getMeasuredHeight();
//            }
//            ViewGroup.LayoutParams params = listView.getLayoutParams();
//            params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
//            // listView.getDividerHeight()获取子项间分隔符占用的高度
//            // params.height最后得到整个ListView完整显示需要的高度
//            listView.setLayoutParams(params);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    }
}
