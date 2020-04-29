package com.onepilltest.personal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hacknife.carouselbanner.interfaces.CarouselImageFactory;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.Cart;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.medicine_;
import com.onepilltest.entity.service_cart;
import com.onepilltest.index.DoctorDetailsActivity;
import com.onepilltest.index.MedicineDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.hacknife.carouselbanner.Banner;
import com.hacknife.carouselbanner.CoolCarouselBanner;
import com.hacknife.carouselbanner.interfaces.OnCarouselItemChangeListener;
import com.hacknife.carouselbanner.interfaces.OnCarouselItemClickListener;
import com.onepilltest.personal.cart.CartActivity;
import com.onepilltest.personal.cart.ShoppingCartActivity;
import com.onepilltest.util.OkhttpUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductActivity extends Activity {
    private ViewPager viewPager;  //轮播图模块
    private int[] mImg;
    private int[] mImg_id;
    private ArrayList<ImageView> mImgList;
    private LinearLayout smallpoint;
    private Button btnAddCart;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private medicine_ med = new medicine_();
    private Button btn2;
    private Button addCurt;
    private SharedPreferences sharedPreferences;
    TextView product_name;
    TextView product_type;
    TextView tabHost1, tabHost2, tabHost3, tabHost4;
    String img1, img2, img3;
    Button btn = null;
    MyListener myListener = null;
    boolean isFocus = false;
    focusDao dao = new focusDao();
    CoolCarouselBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffffffff);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        sharedPreferences = getSharedPreferences("addToCart", MODE_PRIVATE);
        myListener = new MyListener();
        find();

        initMed();

        btnAddCart = findViewById(R.id.btn_addcart);
        EventBus.getDefault().register(this);

//        initLoopView();  //实现轮播图
        //1.获取TabHost控件
        TabHost tabHost = findViewById(android.R.id.tabhost);
        //2.对TabHost控件进行初始化
        tabHost.setup();
        //3.添加选项卡
        tabHost.addTab(tabHost.newTabSpec("Summary").setIndicator("概述").setContent(R.id.tab_1));
        tabHost.addTab(tabHost.newTabSpec("function").setIndicator("功能主治").setContent(R.id.tab_2));
        tabHost.addTab(tabHost.newTabSpec("sideEffect").setIndicator("副作用").setContent(R.id.tab_3));
        tabHost.addTab(tabHost.newTabSpec("explain").setIndicator("使用说明").setContent(R.id.tab_4));

    }

    private void initMed() {

        if (getIntent().getStringExtra("product")!= null){
            String product = getIntent().getStringExtra("product");
            MedicineDao dao = new MedicineDao();
            String name = product;
            Log.e("搜索", "" + name);
            dao.searchMedicineByName(name);
        }else{
            int id = getIntent().getIntExtra("id",0);
            MedicineDao dao = new MedicineDao();
            dao.searchMedicineById(id);
        }

    }

    private void find() {
        addCurt = findViewById(R.id.btn_addcart);
        addCurt.setOnClickListener(myListener);
        btn2 = findViewById(R.id.btn_cons);
        btn2.setOnClickListener(myListener);
        btn = findViewById(R.id.btn_coll);
        btn.setOnClickListener(myListener);
        product_name = findViewById(R.id.tv_productName);
        product_type = findViewById(R.id.tv_productType);
        tabHost1 = findViewById(R.id.tab_1);
        tabHost2 = findViewById(R.id.tab_2);
        tabHost3 = findViewById(R.id.tab_3);
        tabHost4 = findViewById(R.id.tab_4);

    }

    public void init() {

        //查询是否关注
        if (UserBook.Code == 1) {
            dao.isHave(UserBook.NowDoctor.getId(), 1, 2, med.getId());
        } else {
            dao.isHave(UserBook.NowUser.getId(), 2, 2, med.getId());
        }

        product_name.setText(med.getMedicine());
        product_type.setText(med.getGeneralName());
        tabHost1.setText(med.getOverview());
        tabHost2.setText(med.getFunction());
        tabHost3.setText(med.getSideEffect());
        tabHost4.setText(med.getIntrodutions());
        img1 = med.getImg1();
        img2 = med.getImg2();
        img3 = med.getImg3();

        banner = findViewById(R.id.pc_product);
        //添加轮播图：
        List<String> list = new ArrayList<>();
        Banner.init(new ImageFactory());
        list.add(Connect.BASE_URL + img1);
        Log.e("轮播图", "" + Connect.BASE_URL + img1);
        list.add(Connect.BASE_URL + img2);
        list.add(Connect.BASE_URL + img3);

        banner.setOnCarouselItemChangeListener(new OnCarouselItemChangeListener() {
            @Override
            public void onItemChange(int position) {
//                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });
        banner.setOnCarouselItemClickListener(new OnCarouselItemClickListener() {
            @Override
            public void onItemClick(int position, String url) {
                Toast.makeText(ProductActivity.this, url, Toast.LENGTH_LONG).show();
            }
        });
        banner.initBanner(list);
    }

    public class ImageFactory implements CarouselImageFactory {
        @Override
        public void onLoadFactory(String url, ImageView view) {
            Glide.with(view).load(url).into(view);
        }
    }


    //获取药品对象
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMedicine(EventMessage msg) {
        Log.e("focusCode", "" + msg.getCode());
        if (msg.getCode().equals("MedicineDao_searchMedicineByName")) {
            Gson gson = new Gson();
            medicine_ product = null;
            product = gson.fromJson(msg.getJson(), medicine_.class);
            med = product;
            //初始化
            init();
        } else if (msg.getCode().equals("focusDao_isHave")) {

            /*if (msg.getJson().equals("yes")) {
                Log.e("focus", "跳进来了!");
            } else if (msg.getCode().equals("focusDao_isHave")) {*/
            if (msg.getJson().equals("yes")) {
                isFocus = true;
                btn.setText("已关注");
            } else {
                isFocus = false;
                btn.setText("关注");
            }
        } else if (msg.getCode().equals("focusDao_del")) {
            if (msg.getJson().equals("yes")) {
                isFocus = false;
                Toast.makeText(getApplicationContext(), "已取消", Toast.LENGTH_SHORT).show();
                btn.setText("关注");
            } else {
                isFocus = true;
                Toast.makeText(getApplicationContext(), "请检查网络连接", Toast.LENGTH_SHORT).show();

            }
        } else if (msg.getCode().equals("focusDao_add")) {
            if (msg.getJson().equals("yes")) {
                isFocus = true;
                Toast.makeText(getApplicationContext(), "已关注", Toast.LENGTH_SHORT).show();
                btn.setText("已关注");
            } else {
                isFocus = false;
                Toast.makeText(getApplicationContext(), "请检查网络连接", Toast.LENGTH_SHORT).show();

            }
        }else if (msg.getCode().equals("MedicineDao_searchMedicineById")){
            Gson gson = new Gson();
            medicine_ product = null;
            product = gson.fromJson(msg.getJson(), medicine_.class);
            med = product;
            //初始化
            init();
        }
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_coll://收藏
                    if (isFocus) {
                        if (UserBook.Code == 1) {
                            dao.del(UserBook.NowDoctor.getId(), 1, 2, med.getId());
                        } else {
                            dao.del(UserBook.NowUser.getId(), 2, 2, med.getId());
                        }
                    } else {
                        if (UserBook.Code == 1) {
                            dao.add(UserBook.NowDoctor.getId(), 1, 2, med.getId());
                        } else {
                            dao.add(UserBook.NowUser.getId(), 2, 2, med.getId());
                        }
                    }
                    break;
                case R.id.btn_cons:
                    Intent intent = new Intent(ProductActivity.this, DoctorDetailsActivity.class);
                    intent.putExtra("id", med.getDoctorId());
                    startActivity(intent);
                    break;
                case R.id.btn_addcart:
                    Cart.medicineList.add(med.getId());
                    saveMedicines();
                    Toast.makeText(getApplicationContext(), "加入购物车成功", Toast.LENGTH_SHORT).show();
                    /*Gson gson = new Gson();
                    EventMessage msg = new EventMessage();
                    msg.setCode("addToCart");
                    msg.setJson(gson.toJson(med));
                    EventBus.getDefault().post(msg);*/
            }
        }
    }

    private void saveMedicines() {
        String url = Connect.BASE_URL+"cart/add";
        service_cart serviceCart = new service_cart();
        serviceCart.setCount(2);//暂定<--------------------------数量
        serviceCart.setMedicineId(med.getId());
        serviceCart.setPrice(Integer.valueOf(med.getPrice()));
        serviceCart.setStatus(1);//暂定<-------------------------状态码
        serviceCart.setUserId(UserBook.NowUser.getId());
        RequestBody requestBody = new FormBody.Builder()
                .add("json",new Gson().toJson(serviceCart))
                .build();
        OkhttpUtil.post(requestBody,url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("狗袁康", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("增加购物车：",str);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("medicineId", med.getId());
                Log.e("ceshi", "" + med.getId());
                editor.commit();
            }
        });

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(Connect.BASE_URL + "CurtServlet?userId=" + UserBook.NowUser.getId()
//                        + "&medicineId=" + med.getId() + "&price=" + med.getPrice() + "&Code=add")
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//                 e.printStackTrace();
//                Log.e("狗袁康", e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("medicineId", med.getId());
//                Log.e("ceshi", "" + med.getId());
//                editor.commit();
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
