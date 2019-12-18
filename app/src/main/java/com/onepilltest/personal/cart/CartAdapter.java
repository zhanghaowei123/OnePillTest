package com.onepilltest.personal.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.medicine_;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    //原始数据
    private List<medicine_> medicines = null;
    //上下文环境
    private Context context = null;
    //item对应的布局文件
    private int item_layout_id;
    public ImageView ivCartMedicineImg;
    public TextView tvCartMedicineName;
    public TextView tvCartMedicineStandard;
    public TextView tvCartMedicinePrice;
    public TextView tvCartMedicineCount;
    public Button btnCartMinus;
    public Button btnCartPlus;

    public CartAdapter(List<medicine_> medicines, Context context, int item_layout_id) {
        this.medicines = medicines;
        this.context = context;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        if (null != medicines) {
            return medicines.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != medicines) {
            return medicines.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(item_layout_id, null);
            ivCartMedicineImg = convertView.findViewById(R.id.iv_cart_medicine_img);
            tvCartMedicineName = convertView.findViewById(R.id.tv_cart_medicine_name);
            tvCartMedicineStandard = convertView.findViewById(R.id.tv_cart_medicine_standard);
            tvCartMedicinePrice = convertView.findViewById(R.id.tv_cart_medicine_price);
            tvCartMedicineCount = convertView.findViewById(R.id.tv_cart_medicine_count);
            btnCartMinus = convertView.findViewById(R.id.btn_cart_minus);
            btnCartPlus = convertView.findViewById(R.id.btn_cart_plus);
        }
        medicine_ medicine = medicines.get(position);
        Glide.with(context)
                .load(Connect.BASE_URL + medicine.getImg1())
                .into(ivCartMedicineImg);
        tvCartMedicineName.setText("药品名：" + medicine.getMedicine());
        tvCartMedicineStandard.setText("规格：" + medicine.getStandard());
        tvCartMedicinePrice.setText("￥" + medicine.getPrice());
        tvCartMedicineCount.setText("2");
        return convertView;
    }

//    private class ViewHolder {
//        public ImageView ivCartMedicineImg;
//        public TextView tvCartMedicineName;
//        public TextView tvCartMedicineStandard;
//        public TextView tvCartMedicinePrice;
//        public TextView tvCartMedicineCount;
//        public Button btnCartMinus;
//        public Button btnCartPlus;
//    }
}
