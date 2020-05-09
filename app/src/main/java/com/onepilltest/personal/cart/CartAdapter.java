package com.onepilltest.personal.cart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.onepilltest.R;
import com.onepilltest.URL.ConUtil;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Cart;
import com.onepilltest.entity.MyCart;
import com.onepilltest.entity.SelectCartItem;
import com.onepilltest.entity.medicine_;
import com.onepilltest.personal.UserBook;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //new
    public CheckBox checkBoxItem;
    public CheckBox checkBoxAll;
    public Map<Integer, SelectCartItem> selectCartItemMap = new HashMap<>();
    public boolean selectAll = false;
    public boolean nowIsAllSelect = false;
    public List<Integer> deleteIds = new ArrayList<>();
    public Button btnDelete=null;

    public void delete(int pos) {
        myCartList.remove(pos);
        notifyDataSetChanged();
    }

    public CartAdapter(List<MyCart> myCarts, Context context, int item_layout_id,CheckBox checkBoxItem
                       ,Button btnDelete) {
        this.myCartList = myCarts;
        this.context = context;
        this.item_layout_id = item_layout_id;
        this.checkBoxAll =checkBoxItem;
        this.btnDelete =btnDelete;
    }
    //删除
    public void setBtnDeleteListener(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 在cartList，以及selectItems中删除对应的商品信息
                List<Integer> keys = new ArrayList<>();
                for (Integer key:selectCartItemMap.keySet()){
                    if (selectCartItemMap.get(key).isSelected())
                        keys.add(key);
                }
                for (int key:keys){
                    int deleteId =selectCartItemMap.get(key).getCart().getId();
                    deleteIds.add(deleteId);
                    selectCartItemMap.remove(key);
                    for (int i=0;i<myCartList.size();i++){
                        MyCart cart = myCartList.get(i);
                        if (cart.getId()==deleteId){
                            myCartList.remove(i);
                            break;
                        }
                    }
                }
                // 2. 将删除的 cartId 发送到至服务器中
                for (final int id : deleteIds) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(Connect.BASE_URL+"cart/deleteList?id="+id);
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestMethod("POST");
                                JSONObject send = new JSONObject();
                                send.put("id", id);
                                ConUtil.setOutputStream(con, send.toString());
                                ConUtil.getInputStream(con);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                // 3. 展示删除成功
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    //全选
    public void setCheckBoxItemAll(){
        checkBoxAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                selectAll=true;
                changeSelectCartItem();
//                tvCartMedicineCount.setText(myCartList.get(0).getCount()+"");
//                Log.e("textmoney",myCartList.get(0).getCount()+"");
            }else {//取消全选状态时
                selectAll=false;
                changeSelectCartItem();
                tvCartMedicineCount.setText("0");
            }
            notifyDataSetChanged();
        });
    }
    private void changeSelectCartItem(){
        Map<Integer,SelectCartItem> keep = selectCartItemMap;
        for (int key:selectCartItemMap.keySet()){
            selectCartItemMap.get(key).setSelected(selectAll);
        }
        if (nowIsAllSelect){
            for (int key:selectCartItemMap.keySet()){
                selectCartItemMap.get(key).setSelected(keep.get(key).isSelected());
            }
            nowIsAllSelect = false;
        }
    }
    public  void setSelectCartItemMap(){
        for (MyCart cart:myCartList){
            SelectCartItem itemMap =new SelectCartItem();
            itemMap.setSelected(false);
            itemMap.setCart(cart);
            selectCartItemMap.put(cart.getId(),itemMap);
        }
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
            checkBoxItem =convertView.findViewById(R.id.choose_item);
        }
//        medicine_ medicine = medicines.get(position);
        MyCart myCart = myCartList.get(position);
        Glide.with(context)
                .load(Connect.BASE_URL + myCart.getImg())
                .into(ivCartMedicineImg);
        checkBoxItem.setText(myCart.getId()+"");
        tvCartMedicineName.setText("药品名：" + myCart.getName());
        tvCartMedicineStandard.setText("规格：" + myCart.getStandard());
        tvCartMedicinePrice.setText("￥" + myCart.getPrice());
        tvCartMedicineCount.setText(myCart.getCount()+"");
        checkBoxItem.setText(myCart.getId()+"");

        int cartId = Integer.parseInt(checkBoxItem.getText().toString());

        //根据selectCaryItemMap中的选中位，设置是否全选
        if (selectCartItemMap.get(cartId).isSelected()){
            checkBoxItem.setChecked(true);
        }else {
            checkBoxItem.setChecked(false);
        }
        //单选框点击事件
        checkBoxItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //更改为选中状态
                    //获取checkbox 的id,在资源中找到id对应的item
                    for (int key : selectCartItemMap.keySet()) {
                        if (key == cartId) {
                            selectCartItemMap.get(key).setSelected(true);
                            break;
                        }
                    }
                    boolean allS = true;
                    for (int key1 : selectCartItemMap.keySet()) {
                        if (!selectCartItemMap.get(key1).isSelected()) {
                            //当有一个未选中状态时
                            allS = false;
                            break;
                        }
                    }
                    if (allS) {
                        //全部选中
                        checkBoxAll.setChecked(true);
                        nowIsAllSelect = true;
                    }
                    notifyDataSetChanged();
                } else {
                    //更改为非选中状态
                    for (int key : selectCartItemMap.keySet()) {
                        if (key == cartId) {
                            selectCartItemMap.get(key).setSelected(false);
                            checkBoxAll.setChecked(false);
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        });
        return convertView;
        }
}
