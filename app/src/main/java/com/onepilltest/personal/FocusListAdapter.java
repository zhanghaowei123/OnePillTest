package com.onepilltest.personal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.focus;

import java.util.List;

public class FocusListAdapter extends ArrayAdapter<focus> {
    private int itemId;

    public FocusListAdapter(@NonNull Context context, int resource, @NonNull List<focus> objects) {
        super(context, resource, objects);
        itemId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        focus f = getItem(position);//获取当前项 Base 实例

        View view = LayoutInflater.from(getContext()).inflate(itemId,parent,false);

        ImageView img = (ImageView) view.findViewById(R.id.focus_list_item_img);
        TextView name = (TextView) view.findViewById(R.id.focus_list_item_name);
        TextView tag = (TextView) view.findViewById(R.id.focus_list_item_tag);

        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(getContext())
                .load(Connect.BASE_URL+f.getImg())
                .apply(requestOptions)
                .into(img);
        name.setText(f.getName());
        tag.setText(f.getMore());
        return view;
    }
}
