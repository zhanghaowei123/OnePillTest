package com.onepilltest.personal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;


import com.onepilltest.R;

import java.util.ArrayList;

public class ProductActivity extends Activity {
    private ViewPager viewPager;  //轮播图模块
    private int[] mImg;
    private int[] mImg_id;
    private ArrayList<ImageView> mImgList;
    private LinearLayout smallpoint;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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

    }


    public void initLoopView(){
        viewPager = findViewById(R.id.pc_product);
        smallpoint = findViewById(R.id.smallpoint);

        mImg = new int[]{
                R.drawable.text1,
                R.drawable.text2,
                R.drawable.text3,
                R.drawable.text4,
                R.drawable.text5
        };

        mImg_id = new int[]{
                R.id.pager_img1,
                R.id.pager_img2,
                R.id.pager_img3,
                R.id.pager_img4,
                R.id.pager_img5
        };


        // 初始化要展示的5个ImageView
        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for(int i=0;i<mImg.length;i++){
            //初始化要显示的图片
            imageView = new ImageView(this);
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new pagerOnClickListener(getApplicationContext()));
            mImgList.add(imageView);
            //加引导点
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.dot);
            layoutParams = new LinearLayout.LayoutParams(50,50);
            if(i!=0){
                layoutParams.leftMargin=10;
            }
            //设置默认所有都不可用
            pointView.setEnabled(false);
            smallpoint.addView(pointView,layoutParams);
        }
        smallpoint.getChildAt(0).setEnabled(true);
        previousSelectedPosition=0;
        //设置配置器
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        //把viewPager设置为默认选中Integer.MAX_VALUE/T2,从十几亿此开始轮播图片，实现无限循环
        int m = (Integer.MAX_VALUE / 2)%mImgList.size();
        int currentPosition = Integer.MAX_VALUE /2 -m;
        viewPager.setCurrentItem(currentPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
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

        new Thread(){
            public void run(){
                isRunning = true;
                while(isRunning){
                    try{
                        Thread.sleep(4000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    //下一条
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        }
                    });
                }
            }
        }.start();

    }


}
