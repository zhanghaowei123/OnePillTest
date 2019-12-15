package com.onepilltest.personal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.medicine_;
import com.onepilltest.index.MedicineDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

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
    TextView product_name;
    TextView product_type;
    TextView tabHost1, tabHost2, tabHost3, tabHost4;
//    private String img1;
//    private String img2;
//    private String img3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        find();
        String product = getIntent().getStringExtra("product");

        btnAddCart = findViewById(R.id.btn_addcart);
        EventBus.getDefault().register(this);
        MedicineDao dao = new MedicineDao();
        String name = product;
        Log.e("搜索",""+name);
        dao.searchMedicineByName(name);
        initLoopView();  //实现轮播图
        //1.获取TabHost控件
        TabHost tabHost = findViewById(android.R.id.tabhost);
        //2.对TabHost控件进行初始化
        tabHost.setup();
        //3.添加选项卡
        tabHost.addTab(tabHost.newTabSpec("Summary").setIndicator("概述").setContent(R.id.tab_1));
        tabHost.addTab(tabHost.newTabSpec("function").setIndicator("功能主治").setContent(R.id.tab_2));
        tabHost.addTab(tabHost.newTabSpec("sideEffect").setIndicator("副作用").setContent(R.id.tab_3));
        tabHost.addTab(tabHost.newTabSpec("explain").setIndicator("使用说明").setContent(R.id.tab_4));

        //添加购物车


    }

    private void find() {
        product_name = findViewById(R.id.tv_productName);
        product_type = findViewById(R.id.tv_productType);
        tabHost1 = findViewById(R.id.tab_1);
        tabHost2 = findViewById(R.id.tab_2);
        tabHost3 = findViewById(R.id.tab_3);
        tabHost4 = findViewById(R.id.tab_4);

    }

    public void init() {
        Log.e("json搜索",""+med.getMedicine());
        product_name.setText(med.getMedicine());
        product_type.setText(med.getGeneralName());
        tabHost1.setText(med.getOverview());
        tabHost2.setText(med.getFunction());
        tabHost3.setText(med.getSide_effect());
        tabHost4.setText(med.getIntrodutions());



//        mImg = new int[]{
//                Integer.parseInt(med.getImg1()),
//                Integer.parseInt(med.getImg2()),
//                Integer.parseInt(med.getImg3())
////                R.drawable.text1,
//////                R.drawable.text2,
//////                R.drawable.text3,
////                /*R.drawable.text4,
//                R.drawable.text5*/
//        };
//        Glide.with(getApplicationContext())
//                .load(Connect.BASE_URL + med.getImg1())
//                .into(imageView);
//        Glide.with(getApplicationContext())
//                .load(Connect.BASE_URL + med.getImg2())
//                .into(imageView);
//        Glide.with(getApplicationContext())
//                .load(Connect.BASE_URL + med.getImg3())
//                .into(imageView);

    }


    public void initLoopView() {
        viewPager = findViewById(R.id.pc_product);
        smallpoint = findViewById(R.id.smallpoint);

        mImg = new int[]{
                R.drawable.text1,
                R.drawable.text2,
                R.drawable.text3
        };
        med.getImg1();
        mImg_id = new int[]{
                R.id.pager_img1,
                R.id.pager_img2,
                R.id.pager_img3,
                /*R.id.pager_img4,
                R.id.pager_img5*/
        };


        // 初始化要展示的5个ImageView
        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < mImg.length; i++) {
            //初始化要显示的图片
            imageView = new ImageView(this);
            Glide.with(this).load(mImg[i]).into(imageView);
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new pagerOnClickListener(getApplicationContext()));
            mImgList.add(imageView);
            //加引导点
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.dot);
            layoutParams = new LinearLayout.LayoutParams(50, 50);
            if (i != 0) {
                layoutParams.leftMargin = 10;
            }
            //设置默认所有都不可用
            pointView.setEnabled(false);
            smallpoint.addView(pointView, layoutParams);
        }
        smallpoint.getChildAt(0).setEnabled(true);
        previousSelectedPosition = 0;
        //设置配置器
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        //把viewPager设置为默认选中Integer.MAX_VALUE/T2,从十几亿此开始轮播图片，实现无限循环
        int m = (Integer.MAX_VALUE / 2) % mImgList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        viewPager.setCurrentItem(currentPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int newPosition = i % mImgList.size();
                smallpoint.getChildAt(previousSelectedPosition).setEnabled(false);
                smallpoint.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();

    }

    //获取药品对象
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMedicine(EventMessage msg) {
        if (msg.getCode().equals("MedicineDao_searchMedicine")) {
            Gson gson = new Gson();
            Log.e("搜索json",""+msg.getJson());
            medicine_ product = null;
            product = gson.fromJson(msg.getJson(), medicine_.class);
            med = product;

        }
        init();
    }

}
