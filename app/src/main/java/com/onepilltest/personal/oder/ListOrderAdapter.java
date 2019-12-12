package com.onepilltest.personal.oder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onepilltest.R;
import com.onepilltest.entity.Order;

import java.util.List;

public class ListOrderAdapter extends BaseAdapter{
    private Context context;
    private List<Order> orderList = null;
    private int layout_item_id;

    public ListOrderAdapter(Context context,List<Order> orderList, int layout_item_id){
        this.context = context;
        this.orderList = orderList;
        this.layout_item_id = layout_item_id;
    }
    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout_item_id,null);
        }
        TextView tvOrderType = convertView.findViewById(R.id.tv_order_type);
        TextView tvOrderName = convertView.findViewById(R.id.tv_order_name);
        TextView tvOrderSize = convertView.findViewById(R.id.tv_order_size);
        TextView tvOrderCount = convertView.findViewById(R.id.tv_order_count);
        TextView tvOrderPay = convertView.findViewById(R.id.tv_order_pay);
        Order order = orderList.get(position);
        // 获取order的类型，根据类型显示类型信息
        switch (order.getType()) {
            case Order.TYPE_UNPAY:
                tvOrderType.setText("订单尚未支付");
                break;
            case Order.TYPE_UNSEND:
                tvOrderType.setText("待卖家发货");
                break;
            case Order.TYPE_WAITGET:
                tvOrderType.setText("卖家已发货");
                break;
            case Order.TYPE_FINISH:
                tvOrderType.setText("订单已完成");
                break;
        }
        // 设置初始信息
        tvOrderName.setText(order.getMedicine().getGeneraName().toString());
        tvOrderSize.setText(order.getMedicine().getStandard().get(0));
        tvOrderCount.setText("已选择"+order.getCount()+"件");
        tvOrderPay.setText("应支付：￥ "+order.getCount()*order.getMedicine().getPrice().get(0));
        return convertView;
    }
    public void updateOrder(Order order){
        for (int i=0;i<orderList.size();i++){
            if(orderList.get(i).getId() == order.getId()){
                orderList.remove(i);
                orderList.add(i,order);
                break;
            }
        }
        notifyDataSetChanged();
    }
}
