package com.onepilltest.personal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.onepilltest.R;
import com.onepilltest.entity.WalletBase;
import com.onepilltest.index.DoctorDetailsActivity;

import java.util.List;

public class WalletAdapter extends ArrayAdapter<WalletBase> {
    private int itemId;
    private MyListener myListener = new MyListener();
    Button call = null;
    Button more = null;
    View view = null;

    public WalletAdapter(@NonNull Context context, int resource, @NonNull List<WalletBase> objects) {
        super(context, resource, objects);
        itemId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WalletBase base = getItem(position);
        view = LayoutInflater.from(getContext()).inflate(itemId, parent, false);

        TextView time = view.findViewById(R.id.wallet_list_time);
        TextView cash = view.findViewById(R.id.wallet_list_cash);
        boolean code = base.isCode();

        time.setText(base.getTime());
        if (code) {//收钱
            cash.setText("+"+base.getCash());
            cash.setTextColor(Color.parseColor("#ff009900"));
            String name = AddAddressActivity.NowUserName;
        } else {//付钱
            cash.setTextColor(Color.parseColor("#ffdd0000"));
            cash.setText("-"+base.getCash());
        }

        //点击事件
        find();
        init();

        return view;
    }

    public void find(){
        call = view.findViewById(R.id.wallet_list_call);
        call.setOnClickListener(myListener);
        more = view.findViewById(R.id.wallet_list_more);
        more.setOnClickListener(myListener);
    }

    public void init(){

    }


    private class MyListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.wallet_list_call:
                    Toast.makeText(getContext(),"正在跳转联系人...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), DoctorDetailsActivity.class);
                    getContext().startActivity(intent);
                    break;
                case R.id.wallet_list_more:
                    Toast.makeText(getContext(),"正在跳转详情页...",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getContext(), WalletMoreActivity.class);
                    getContext().startActivity(intent1);
                    break;
            }
        }
    }
}
