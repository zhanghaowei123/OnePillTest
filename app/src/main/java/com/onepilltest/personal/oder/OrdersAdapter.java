package com.onepilltest.personal.oder;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Dao.OrdersDao;
import com.onepilltest.entity.Orders;
import com.onepilltest.personal.cart.CartNewAdapter;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Orders> ordersList ;
    private OrdersDao ordersDao;

    //私有属性
    private OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v,Orders orders, int position);
    }

    public OrdersAdapter(List<Orders> baseList) {
        ordersList = baseList;
        for(Orders orders:ordersList){
            Log.e("order数据源",""+orders.toString());
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView shopName;//店铺名称
        TextView status;//状态
        ImageView img;//药品图片
        TextView price;//总金额
        TextView del;//删除
        TextView more;//详细信息

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.setting_order_list_item_where);
            status = itemView.findViewById(R.id.setting_order_list_isPay);
            img = itemView.findViewById(R.id.setting_order_list_item_img1);
            price = itemView.findViewById(R.id.setting_order_list_item_money);
            del = itemView.findViewById(R.id.order_list_del);
            more = itemView.findViewById(R.id.order_list_more);
        }
    }
    private Context mContext;

    //初始化Order
    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list_item,viewGroup,false);
        mContext = viewGroup.getContext();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //绑定Order
    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder viewHolder, int i) {
        Orders orders = ordersList.get(i);
        ordersDao = new OrdersDao();
        viewHolder.shopName.setText("OnePill");
        if (orders.getStatus()==1){
            viewHolder.status.setText("已付款");
        }else if (orders.getStatus()==0){
            viewHolder.status.setText("未付款");
        }

        Glide.with(mContext)
                .load(Connect.BASE_URL + orders.getImg())
                .into(viewHolder.img);
        viewHolder.price.setText("￥"+orders.getPrice());

        //实现点击效果
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, orders, i);
                }
            }
        });

        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"快写删除功能",Toast.LENGTH_SHORT).show();
                //删除订单
                ordersDao.del(orders.getId());
                ordersList.remove(i);
            }
        });

        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"在这里付款和选择地址",Toast.LENGTH_SHORT).show();
                //跳转到详情页
                
            }
        });


    }

    //getItem
    @Override
    public int getItemCount() {
        return ordersList.size();
    }
}
