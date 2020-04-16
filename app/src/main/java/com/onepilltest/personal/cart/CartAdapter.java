package com.onepilltest.personal.cart;

import android.content.Context;
import android.util.Log;
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
import com.onepilltest.entity.MyCart;
import com.onepilltest.entity.medicine_;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    //原始数据
    private List<MyCart> myCartList = null;
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

//    public CartAdapter(List<medicine_> medicines, Context context, int item_layout_id) {
//        this.medicines = medicines;
//        this.context = context;
//        this.item_layout_id = item_layout_id;
//    }

    public CartAdapter(List<MyCart> myCarts, Context context, int item_layout_id) {
        this.myCartList = myCarts;
        this.context = context;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        if (null != myCartList) {
            return myCartList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != myCartList) {
            return myCartList.get(position);
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
            Log.e("创建listview：",position+"");
            convertView = LayoutInflater.from(context).inflate(item_layout_id, parent,false);
            ivCartMedicineImg = convertView.findViewById(R.id.iv_cart_medicine_img);
            tvCartMedicineName = convertView.findViewById(R.id.tv_cart_medicine_name);
            tvCartMedicineStandard = convertView.findViewById(R.id.tv_cart_medicine_standard);
            tvCartMedicinePrice = convertView.findViewById(R.id.tv_cart_medicine_price);
            tvCartMedicineCount = convertView.findViewById(R.id.tv_cart_medicine_count);
            btnCartMinus = convertView.findViewById(R.id.btn_cart_minus);
            btnCartPlus = convertView.findViewById(R.id.btn_cart_plus);
        }
//        medicine_ medicine = medicines.get(position);
        MyCart myCart = myCartList.get(position);
        Glide.with(context)
                .load(Connect.BASE_URL + myCart.getImg())
                .into(ivCartMedicineImg);
        tvCartMedicineName.setText("药品名：" + myCart.getName());
        tvCartMedicineStandard.setText("规格：" + myCart.getStandard());
        tvCartMedicinePrice.setText("￥" + myCart.getPrice());
        tvCartMedicineCount.setText(myCart.getCount()+"");
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
