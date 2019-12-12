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
import com.onepilltest.entity.UserPatient;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<UserPatient> {
    private int itemId;

    public UserListAdapter(@NonNull Context context, int resource, @NonNull List<UserPatient> objects) {
        super(context, resource, objects);
        itemId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserPatient base = getItem(position);//获取当前项 Base 实例

        View view = LayoutInflater.from(getContext()).inflate(itemId,parent,false);

        ImageView headImg =  view.findViewById(R.id.user_list_listview_item_headImg);
        TextView nickName = (TextView) view.findViewById(R.id.user_list_listview_item_nickName);
        TextView phone = (TextView) view.findViewById(R.id.user_list_listview_item_phone);
        ImageView btn = view.findViewById(R.id.user_list_listview_item_btn);

        //头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(getContext())
                .load(Connect.BASE_URL+base.getHeadImg())
                .apply(requestOptions)
                .into(headImg);
        nickName.setText(base.getNickName());
        phone.setText(base.getPhone());
        if (base.getUserId() == UserBook.NowUser.getUserId()){
            btn.setImageResource(R.drawable.ahh);
        }

        return view;
    }
}
