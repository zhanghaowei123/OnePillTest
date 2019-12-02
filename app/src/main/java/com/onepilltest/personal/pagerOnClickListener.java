package com.onepilltest.personal;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.onepilltest.R;


public class pagerOnClickListener implements View.OnClickListener {
    //新建pagerOnClickListener类实现View.OnClickListener接口

    Context Context;
    public pagerOnClickListener(Context Context){
        this.Context = Context;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pager_img1:
                Toast.makeText(Context, "图片1被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img2:
                Toast.makeText(Context, "图片2被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img3:
                Toast.makeText(Context, "图片3被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img4:
                Toast.makeText(Context, "图片4被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img5:
                Toast.makeText(Context, "图片5被点击", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
