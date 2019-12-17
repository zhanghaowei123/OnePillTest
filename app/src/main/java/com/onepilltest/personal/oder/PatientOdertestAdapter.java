package com.onepilltest.personal.oder;

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
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class PatientOdertestAdapter extends BaseAdapter{
    private List<Order> dataSource = new ArrayList<>();
    private Context context = null;
    private int item_layout_id;
    private TextView tvName;
    private TextView tvSize;
    private TextView tvCount;
    private TextView tvPay;
    private TextView tvType;

    public PatientOdertestAdapter(Context context,
                                  List<Order> dataSource,
                                  int item_layout_id){
        this.context=context;
        this.dataSource = dataSource;
        for (int i=0;i<dataSource.size();i++){
            dataSource.get(i).setStatus("未发货");

        }
        this.item_layout_id = item_layout_id;
    }
    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null==convertView){
            Log.e ( "test", position + "位置创建新的view" );
            // 布局填充器，可以根据布局文件生成相应View对象
            LayoutInflater inflater = LayoutInflater.from ( context );
            // 使用布局填充器根据布局文件资源ID生成View视图对象
            convertView = inflater.inflate ( item_layout_id, null );
            viewHolder.imgHeader = convertView.findViewById(R.id.ordertest_medicine_headerimg);
        }
        tvName = convertView.findViewById(R.id.ordertest_text_name);
        tvSize = convertView.findViewById(R.id.ordertest_text_size);
        tvCount = convertView.findViewById(R.id.ordertest_text_count);
        tvPay = convertView.findViewById(R.id.ordertest_text_pay);
        tvType = convertView.findViewById(R.id.ordertest_btn_type);

        Order order = dataSource.get(position);
        tvName.setText(order.getMedicineName().toString());
        tvSize.setText(order.getSize().toString());
        tvCount.setText("数量:"+order.getCount()+"");
        tvPay.setText("应付款：￥"+order.getPrice()+"");
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.get(position).setStatus("已发货");
                if (tvType.getText().equals("未发货")){
                    tvType.setText("已发货");
                }if (tvType.getText().equals("已发货")){
                    tvType.setText("订单完成");
                }else {
                    tvType.setText("未发货");
                }
                notifyDataSetChanged();
            }
        });
//        Order order1 = dataSource.get(position);
//        RequestOptions requestOptions = new RequestOptions().circleCrop();
//        Glide.with(context)
//                .load(Connect.BASE_URL + order1.getMedicine().getImg1s())
//                .apply(requestOptions)
//                .into(viewHolder.imgHeader);
        return convertView;
    }
    private class ViewHolder {
        public ImageView imgHeader;
    }

}
