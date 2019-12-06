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
import com.onepilltest.entity.Address;

import java.util.List;

public class AddressAdapter extends ArrayAdapter<Address>{
    private int itemId;

    public AddressAdapter(@NonNull Context context, int resource, @NonNull List<Address> objects) {
        super(context, resource, objects);
        itemId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Address base = getItem(position);//获取当前项 Base 实例

        View view = LayoutInflater.from(getContext()).inflate(itemId,parent,false);

        ImageView img = (ImageView) view.findViewById(R.id.user_address_item_img);
        TextView name = (TextView) view.findViewById(R.id.user_address_item_name);
        TextView address = (TextView) view.findViewById(R.id.user_address_item_address);

        img.setImageResource(R.drawable.user);
        name.setText(base.getName());
        address.setText(base.getMore());
        return view;

    }
}
