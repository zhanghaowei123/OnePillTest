package com.onepilltest.personal.cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.ConUtil;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Cart;
import com.onepilltest.entity.Medicine;
import com.onepilltest.entity.medicine_;
import com.onepilltest.personal.PersonalFragment;
import com.onepilltest.personal.ProductActivity;
import com.onepilltest.personal.UserBook;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.mob.MobSDK.getContext;

public class ShoppingCartActivity extends AppCompatActivity {
    private LinearLayout llEmptyCart = null;
    private ImageView imgCartBack= null;
    private ListView lvCart = null;
    private CheckBox cbChooseAll = null;
    private Button btnSettlement = null;
    private Button btnDelete = null;
    private View view;
    private TextView tvCartManage = null;
    private TextView tvCartFinish = null;
    private TextView tvSettlementPrice = null;
    private LinearLayout llTotalPrice = null;
    private List<Cart> carts = new ArrayList<>();
    public String conGetCartIp = Connect.BASE_URL + "buyer/cart/list";
    private SharedPreferences sharedPreferences;
    private ShoppingCartAdapter adapter = null;
    public static final String FROM_CART = "fromCart";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);
        initViews();
        // 2. 获取cart数据
        GetCartListTask task = new GetCartListTask();
        task.execute();
    }
    private void initViews() {
        // 1. 寻找控件
        imgCartBack=findViewById(R.id.cart_back);
        llEmptyCart = findViewById(R.id.ll_cake_empty);
        lvCart = findViewById(R.id.lv_cart);
        tvCartManage = findViewById(R.id.tv_cart_manage);
        tvCartFinish = findViewById(R.id.tv_cart_finish);
        btnDelete = findViewById(R.id.btn_delete);
        Log.e("box","box初始化");
        cbChooseAll = findViewById(R.id.cb_choose_all);
        btnSettlement = findViewById(R.id.btn_settlement);
        tvSettlementPrice = findViewById(R.id.tv_settlement_price);
        llTotalPrice = findViewById(R.id.ll_cart_total_price);
    }

    private void setViewListener() {
        UseListener listener = new UseListener();
        imgCartBack.setOnClickListener(listener);
        tvCartFinish.setOnClickListener(listener);
        tvCartManage.setOnClickListener(listener);
    }


    //获取medicine
    public medicine_ getMedicine(){
        medicine_ med = new Gson().fromJson(getIntent().getStringExtra("info"),medicine_.class) ;
        return med;
    }

    private class UseListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cart_back:
                   finish();
                   break;
                case R.id.tv_cart_manage:   // 当管理按钮被点下时，可进行删除操作
                    tvCartManage.setVisibility(View.GONE);
                    tvCartFinish.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.VISIBLE);
                    btnSettlement.setVisibility(View.GONE);
                    llTotalPrice.setVisibility(View.INVISIBLE);
                    break;
                case R.id.tv_cart_finish:   // 当修改完成时，可进行下单操作
                    tvCartFinish.setVisibility(View.GONE);
                    tvCartManage.setVisibility(View.VISIBLE);
                    btnSettlement.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.GONE);
                    llTotalPrice.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private class GetCartListTask extends AsyncTask {
        @Override
        protected void onPostExecute(Object o) {
//            sharedPreferences = getBaseContext().getSharedPreferences("NowUser",MODE_PRIVATE);
            initViews();
            if (carts.size() > 0) {
                // 若收到购物车数据，则显示购物车列表
                llEmptyCart.setVisibility(View.GONE);
                Log.e("View,GONE",View.GONE+"");
                lvCart.setVisibility(View.VISIBLE);
            }
            // 3. 配置 listview
            adapter = new ShoppingCartAdapter(carts, getContext(), R.layout.recycler_item,
                    btnSettlement,
                    btnDelete,
                    cbChooseAll,
                    tvSettlementPrice,
                    llEmptyCart,
                    lvCart);
            lvCart.setAdapter(adapter);
            adapter.setSelectCartItems();
            adapter.setCbChooseAllListener();
            adapter.setButtonListener();
            // 4. 设置listViewItemOnClick监听器
            lvCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ProductActivity.class);
                    //Intent利用Bundle.putSerializable传递参数
                    //实现Serializable接口
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("medicine", carts.get(position).getMedicine());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            // 5. 改变状态的监听器
            setViewListener();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                carts.clear();
                URL url = new URL(conGetCartIp);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                // 先发送 用户信息，用于查询登录用户对应的cart
                JSONObject send = new JSONObject();
//                int buyerId = sharedPreferences.getInt("NowUser", 0);
                int buyerId= UserBook.NowUser.getUserId();
                send.put("buyerId", buyerId);
                ConUtil.setOutputStream(con, send.toString());
                // 获取 返回的用户所对应的 cart列表
                String get = ConUtil.getInputStream(con);
                JSONArray jsonArray = new JSONArray(get);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Cart cart = new Cart();
                    cart.setBuyerId(buyerId);
                    cart.setCount(jsonObject.getInt("count"));
                    cart.setMedicineId(jsonObject.getInt("medicineId"));
                    cart.setId(jsonObject.getInt("id"));
                    cart.setType(jsonObject.getString("status"));
                    Medicine medicine = new Medicine();
                    medicine.setId(jsonObject.getInt("medicineId"));
                    medicine.setGeneraName(jsonObject.getString("generaname"));
                    medicine.setMedicineName(jsonObject.getString("medicineName"));
                    medicine.setPrice(jsonObject.getInt("price"));
                    Log.e("price",""+jsonObject.getInt("price"));
                    medicine.setOverView(jsonObject.getString("overView"));
                    medicine.setIntrodution(jsonObject.getString("introdution"));
                    medicine.setForbiddancet(jsonObject.getString("forbiddancet"));
                    medicine.setSide_effect(jsonObject.getString("sideeffect"));
                    medicine.setStandard(jsonObject.getString("size"));
                    medicine.setStocks(jsonObject.getInt("stock"));
                    medicine.setImg1(jsonObject.getString("img1"));
                    cart.setMedicine(medicine);
                    carts.add(cart);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return carts;
        }
    }

    @Override
    public void onResume() {
        Log.e("box","onResume方法执行");
        super.onResume();
        if (cbChooseAll!=null)
        cbChooseAll.setChecked(false);
        GetCartListTask task = new GetCartListTask();
        task.execute();
    }

}
