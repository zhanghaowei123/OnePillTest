package com.onepilltest.personal;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.onepilltest.R;

import java.util.List;

public class WalletAdapter extends ArrayAdapter<WalletBase> {
    private int itemId;

    public WalletAdapter(@NonNull Context context, int resource, @NonNull List<WalletBase> objects) {
        super(context, resource, objects);
        itemId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WalletBase base = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(itemId, parent, false);

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
        return view;
    }
}
