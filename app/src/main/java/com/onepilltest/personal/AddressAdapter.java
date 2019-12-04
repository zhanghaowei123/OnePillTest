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

import com.onepilltest.R;

import java.util.List;

public class AddressAdapter extends ArrayAdapter<AddressBase>{
    private int itemId;

    public AddressAdapter(@NonNull Context context, int resource, @NonNull List<AddressBase> objects) {
        super(context, resource, objects);
        itemId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AddressBase base = getItem(position);//获取当前项 Base 实例

        View view = LayoutInflater.from(getContext()).inflate(itemId,parent,false);

        ImageView img = (ImageView) view.findViewById(R.id.user_address_item_img);
        TextView name = (TextView) view.findViewById(R.id.user_address_item_name);
        TextView address = (TextView) view.findViewById(R.id.user_address_item_address);

        img.setImageResource(base.getImgId());
        name.setText(base.getAddress().getName());
        address.setText(base.getAddress().getMore());
        return view;

    }
}
