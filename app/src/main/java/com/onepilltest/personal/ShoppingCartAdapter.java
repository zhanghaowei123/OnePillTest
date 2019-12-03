package com.onepilltest.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onepilltest.R;

import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter{

    private List<Cart> dataSource;
    private Context context = null;
    private int item_layout_id;

    public void delete(int pos) {
        dataSource.remove(pos);
        notifyDataSetChanged();
    }

    public void add(Cart map){
        dataSource.add(map);
        notifyDataSetChanged();
    }

    public ShoppingCartAdapter(Context context,
                            List<Cart> dataSource,
                            int item_layout_id) {
        this.context = context;
        this.dataSource = dataSource;
        this.item_layout_id = item_layout_id;
    }

    public int getCount() {
        return dataSource.size();
    }

    public Object getItem(int position) {
        return dataSource.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position,
                        View convertView,
                        ViewGroup parent) {
        if(null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id, null);
        }
        TextView name = convertView.findViewById(R.id.sc_tv_name);
        TextView type = convertView.findViewById(R.id.sc_tv_type);
        TextView price = convertView.findViewById(R.id.sc_tv_price);
        Cart cart = dataSource.get(position);
        name.setText(cart.getName().toString());
        type.setText(cart.getType().toString());
        price.setText(cart.getPrice());
        return convertView;
    }
}
