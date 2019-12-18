package com.onepilltest.personal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.Orders;
import com.onepilltest.entity.focus;
import com.onepilltest.index.MedicineDao;

import java.util.ArrayList;
import java.util.List;

public class orderAdapter extends ArrayAdapter<Orders> {
    private int itemId;

    public orderAdapter(@NonNull Context context, int resource, @NonNull List<Orders> objects) {
        super(context, resource, objects);
        itemId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Orders base = getItem(position);//获取当前项 Base 实例

        View view = LayoutInflater.from(getContext()).inflate(itemId,parent,false);

        ImageView img = (ImageView) view.findViewById(R.id.setting_order_list_item_img1);
        TextView money = (TextView) view.findViewById(R.id.setting_order_list_item_money);
        TextView isPay = (TextView) view.findViewById(R.id.setting_order_list_isPay);


        //img.setImageResource(R.drawable.user);
        Glide.with(getContext())
                .load(Connect.BASE_URL+base.getImg())
                .into(img);
        money.setText("￥"+base.getCount()*base.getPrice());
        if (base.getStatus() == 0)
            isPay.setText("已付款");

        else if (base.getStatus() == 1)
            isPay.setText("未付款");
        return view;
    }
}
