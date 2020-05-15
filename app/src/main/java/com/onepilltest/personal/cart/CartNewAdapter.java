package com.onepilltest.personal.cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.MyCart;

import java.util.ArrayList;
import java.util.List;

public class CartNewAdapter extends RecyclerView.Adapter<CartNewAdapter.ViewHolder>{
    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView imageView;
        TextView tvName;
        TextView tvStandard;
        TextView tvPrice;
        Button btnMin;
        Button btnAdd;
        TextView tvCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.choose_item);
            imageView = itemView.findViewById(R.id.iv_cart_medicine_img);
            tvName = itemView.findViewById(R.id.tv_cart_medicine_name);
            tvStandard = itemView.findViewById(R.id.tv_cart_medicine_standard);
            tvPrice = itemView.findViewById(R.id.tv_cart_medicine_price);
            tvCount = itemView.findViewById(R.id.tv_cart_medicine_count);
            btnMin = itemView.findViewById(R.id.btn_cart_minus);
            btnAdd = itemView.findViewById(R.id.btn_cart_plus);
        }
    }
    private  Context context;
    //原始数据
    private List<MyCart> myCarts = null;
    private List<Boolean> booleanList = null;

    int num = 0;

    public CartNewAdapter(List<MyCart> myCarts,List<Boolean> booleanList){
        this.myCarts = myCarts;
        this.booleanList = booleanList;
        for (MyCart c:myCarts){
            Log.e("购物车数据源",""+c.toString());
            //设置默认的显示
//            booleanList.add(false);
        }
    }

    @NonNull
    @Override
    public CartNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mcart,viewGroup,false);
        context = viewGroup.getContext();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartNewAdapter.ViewHolder viewHolder, int i) {
        MyCart myCart = myCarts.get(i);

        //图片
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(context)
                .load(Connect.BASE_URL+myCart.getImg())
                .apply(requestOptions)
                .into(viewHolder.imageView);
        //药品名
        viewHolder.tvName.setText(myCart.getName());
        //规格
        viewHolder.tvStandard.setText(myCart.getStandard());
        //CheckBox勾选框
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用集合保存当前状态
                booleanList.set(i,isChecked);
            }
        });
        viewHolder.checkBox.setChecked(booleanList.get(i));
        //数量
        viewHolder.tvCount.setText(myCart.getCount()+"");
        //钱
        viewHolder.tvPrice.setText(myCart.getPrice()+"");
        //加减
        int max = 10;
        int min =1;

        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = myCarts.get(i).getCount();
                if (max<=num){
                    viewHolder.btnAdd.setClickable(false);
                    viewHolder.btnMin.setClickable(true);
                    Toast.makeText(context,"不可以在增加了",Toast.LENGTH_SHORT).show();
                }else{
                    viewHolder.btnMin.setClickable(true);
                    viewHolder.btnAdd.setClickable(true);
                    num += 1;
                    viewHolder.tvCount.setText(num+"");
                    //获取新数据
                    myCarts.get(i).setCount(num);
                    notifyDataSetChanged();
                }

            }
        });
        viewHolder.btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (min>=num){
                    viewHolder.btnAdd.setClickable(true);
                    viewHolder.btnMin.setClickable(false);
                    Toast.makeText(context,"不可以再减少了",Toast.LENGTH_SHORT).show();
                }else {
                    viewHolder.btnMin.setClickable(true);
                    viewHolder.btnAdd.setClickable(true);
                    viewHolder.tvCount.setText(num-1+"");
                }
                //获取新数据
                myCarts.get(i).setCount(num-1);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCarts.size();
    }
    //删除选中的数据
    public void deleteingData(){
        int y=0;
        for (int i = 0;i<myCarts.size();i++){
            if (booleanList.get(i)!=null && booleanList.get(i)){
                myCarts.remove(i);
                booleanList.remove(i);
                y++;
                i--;

            }
        }
        notifyDataSetChanged();
        if (y==0){
            Toast.makeText(context,"请选择要删除的药品",Toast.LENGTH_SHORT).show();
        }
    }


}
