package com.onepilltest.nearby;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.Poi;
import com.baidu.mapapi.search.core.PoiInfo;
import com.onepilltest.R;
import com.onepilltest.entity.Address;
import com.onepilltest.index.CommentAdapter;

import java.util.List;

public class LocationAdapter extends BaseAdapter {

    //原始数据
    List<PoiInfo> poiList = null;
    //上下文环境
    private Context context = null;
    //item对应的布局文件
    TextView name = null;
    TextView line = null;
    private int item_layout_id;
    private LayoutInflater mInflater;

    public LocationAdapter(List<PoiInfo> poiList, Context context, int item_layout_id) {
        this.poiList = poiList;
        this.context = context;
        this.item_layout_id = item_layout_id;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (null != poiList) {
            return poiList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != poiList) {
            return poiList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoiInfo poi = poiList.get(position);//获取当前项 Base 实例
        View view = mInflater.inflate(item_layout_id,null);

        name = (TextView) view.findViewById(R.id.locationitem_name);
        name.setText(poi.getName());
        String add = poi.getName();
        if (poi.getAddress().length()>=18){
            add = add.substring(0,13)+"  ...";
        }
        line = view.findViewById(R.id.locationitem_line);
        line.setText(add);
        return view;
    }
}
