package com.onepilltest.personal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.focus;
import com.onepilltest.nearby.NearMap;

import java.util.List;

public class FocusListAdapter extends ArrayAdapter<focus> {
    private int itemId;
    ImageView add = null;
    ImageView img = null;
    TextView tag = null;
    TextView name = null;
    private Boolean isChange = false;
    TextView adds = null;
    TextView dels = null;
    MyListener myListener = new MyListener();

    public FocusListAdapter(@NonNull Context context, int resource, @NonNull List<focus> objects) {
        super(context, resource, objects);
        itemId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        focus f = getItem(position);//获取当前项 Base 实例
        Log.e("FocusListAdapter","get"+f.toString());

        View view = LayoutInflater.from(getContext()).inflate(itemId,parent,false);

        img = (ImageView) view.findViewById(R.id.focus_list_item_img);
        name = (TextView) view.findViewById(R.id.focus_list_item_name);
        tag = (TextView) view.findViewById(R.id.focus_list_item_tag);
        add = view.findViewById(R.id.focus_list_item_add);
        adds = view.findViewById(R.id.focus_list_adds);
        dels = view.findViewById(R.id.focus_list_dels);


        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(getContext())
                .load(Connect.BASE_URL+f.getImg())
                .apply(requestOptions)
                .into(img);
        name.setText(f.getName());
        tag.setText(f.getMore());
        add.setImageDrawable(getContext().getResources().getDrawable(R.drawable.add_focus));

        isChange = false;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChange){
                    Toast.makeText(getContext(),"正在取消关注...",Toast.LENGTH_SHORT).show();
                    add.setImageDrawable(getContext().getResources().getDrawable(R.drawable.del_focus));
                    isChange = true;
                }else {
                    Toast.makeText(getContext(),"正在关注...",Toast.LENGTH_SHORT).show();
                    add.setImageDrawable(getContext().getResources().getDrawable(R.drawable.add_focus));
                    isChange = false;
                }

            }
        });

        adds.setOnClickListener(myListener);

        return view;
    }


    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.focus_list_adds:
                    Toast.makeText(getContext(),"查看第"+"条信息",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }
}
