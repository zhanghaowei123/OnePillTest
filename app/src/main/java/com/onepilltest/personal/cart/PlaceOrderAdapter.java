package com.onepilltest.personal.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Cart;

import java.util.List;

public class PlaceOrderAdapter extends BaseAdapter {
    private Context context;
    private List<Cart> carts = null;
    private int layout_item_id ;

    public PlaceOrderAdapter(Context context,List<Cart> carts,int layout_item_id){
        this.context = context;
        this.carts = carts;
        this.layout_item_id = layout_item_id;
    }
    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int position) {
        return carts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return carts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout_item_id,null);
            viewHolder.ivMedicineImg = convertView.findViewById(R.id.place_img);
        }
        TextView tvMedicineName = convertView.findViewById(R.id.place_name);
        TextView tvMedicineSize = convertView.findViewById(R.id.place_size);
        TextView tvMedicinePrice = convertView.findViewById(R.id.place_price);
        TextView tvMedicineCount = convertView.findViewById(R.id.place_count);
        tvMedicineSize.setText(carts.get(position).getMedicine().getStandard().get(0));
        tvMedicinePrice.setText(""+carts.get(position).getMedicine().getPrice().get(0));
        tvMedicineName.setText(carts.get(position).getMedicine().getMedicineName().toString());
        tvMedicineCount.setText(""+carts.get(position).getCount());
        //获取药品图片
//        Cart cart1 = carts.get(position);
//        RequestOptions requestOptions = new RequestOptions().circleCrop();
//        Glide.with(context)
//                .load(Connect.BASE_URL + cart1.getMedicine().getImg1s())
//                .apply(requestOptions)
//                .into(viewHolder.ivMedicineImg);
        return convertView;
    }
    private class ViewHolder {
        public ImageView ivMedicineImg;
    }

}
