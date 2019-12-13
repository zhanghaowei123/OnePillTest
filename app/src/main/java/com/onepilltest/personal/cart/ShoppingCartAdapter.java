package com.onepilltest.personal.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onepilltest.R;
import com.onepilltest.URL.ConUtil;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Cart;
import com.onepilltest.entity.Medicine;
import com.onepilltest.entity.SelectCartItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartAdapter extends BaseAdapter{
    private String updateIp = Connect.BASE_URL+ "/CartUpdateServlet";
    private String deleteIp = Connect.BASE_URL+ "/CartDeleteServlet";

    private ImageView imgView = null;
    private Button btnSettlement= null;
    private Button btnDelete = null;
    private CheckBox checkBox =null;
    private TextView tvSettlementPrice = null;
    private Map<Integer,SelectCartItem> selectCartItemMap = new HashMap<>();
    private boolean selectAll = false;
    private boolean nowIsAllSelect = false;
    private LinearLayout llEmptyCart = null;
    private ListView lvCart = null;
    private List<Integer> deleteIds = new ArrayList<>();



    private List<Cart> dataSource = null;
    private Context context;
    private int item_layout_id;

    public void delete(int pos) {
        dataSource.remove(pos);
        notifyDataSetChanged();
    }

    public void add(Cart map){
        dataSource.add(map);
        notifyDataSetChanged();
    }

    public ShoppingCartAdapter(List<Cart> dataSource,
                               Context context,
                               int item_layout_id, Button btnSettlement,Button btnDelete,CheckBox checkBox,TextView tvSettlementPrice,
                               LinearLayout llEmptyCart,ListView lvCart) {
        this.context = context;
        this.dataSource = dataSource;
        this.item_layout_id = item_layout_id;
        this.btnSettlement = btnSettlement;
        this.btnDelete = btnDelete;
        this.checkBox=checkBox;
        this.tvSettlementPrice = tvSettlementPrice;
        this.llEmptyCart = llEmptyCart;
        this.lvCart = lvCart;
    }
    public int getTotalPrice() {
        int totalPrice = 0;
        for (int key : selectCartItemMap.keySet()) {
            if (selectCartItemMap.get(key).isSelected()) {
                SelectCartItem item = selectCartItemMap.get(key);
                totalPrice += item.getCart().getCount()*item.getCart().getMedicine().getPrice().get(0);
            }
        }
        return totalPrice;
    }

    public int getCount() {
        return dataSource.size();
    }

    public Object getItem(int position) {
        return dataSource.get(position);
    }

    public long getItemId(int position) {
        return dataSource.get(position).getId();
    }

    public View getView(final int position,
                        View convertView,
                        ViewGroup parent) {
        if(null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id, null);
        }
        final CheckBox cbChooseOne = convertView.findViewById(R.id.cb_check);
        imgView = convertView.findViewById(R.id.img_Medicine);
        TextView name = convertView.findViewById(R.id.sc_tv_name);
        TextView type = convertView.findViewById(R.id.sc_tv_type);
        TextView price = convertView.findViewById(R.id.sc_tv_price);
        final TextView tvCartCount = convertView.findViewById(R.id.tv_count);
        final Button btnAdd = convertView.findViewById(R.id.btn_plus);
        final Button btnMinus = convertView.findViewById(R.id.btn_minus);
        //获取对应的信息进行填充
        final Cart cart = dataSource.get(position);
        Medicine medicine  = (Medicine) cart.getMedicine();
        final int maxCount = medicine.getStocks().get(0);
        final int minCount =1;
        cbChooseOne.setText(cart.getId()+"");
        name.setText(cart.getName().toString());
        type.setText(cart.getType().toString());
        price.setText(cart.getMedicinePrice());
        final int cartId = Integer.parseInt(cbChooseOne.getText().toString());
        //根据SelectCartItems中的选中位，设置是否全选
        if (selectCartItemMap.get(cartId).isSelected()){
            cbChooseOne.setChecked(true);
        }else {
            cbChooseOne.setChecked(false);
        }
        cbChooseOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //更改为选中状态
                    //获取cb的id，在资源中找到id对应的item
                    for (int key:selectCartItemMap.keySet()){
                        if (key == cartId){
                            selectCartItemMap.get(key).setSelected(true);
                            break;
                        }
                    }
                    boolean allS = true;
                    for (int key1 : selectCartItemMap.keySet()) {
                        if (!selectCartItemMap.get(key1).isSelected()) {
                            // 当有一个为未选中状态时，cbAll不被选中
                            allS = false;
                            break;
                        }
                    }
                    if (allS){  // 全部选中时
                        checkBox.setChecked(true);
                        nowIsAllSelect = true;
                    }
                    notifyDataSetChanged();
                }else {
                    //更改为非选中状态
                    for (int key : selectCartItemMap.keySet()){
                        if (key == cartId){
                            selectCartItemMap.get(key).setSelected(false);
                            checkBox.setChecked(false);
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }
//                //获取当前选中的总额 显示在页面上
                tvSettlementPrice.setText(getTotalPrice()+"");
            }
        });
        //给button绑定事件
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.获取当前count文本框的值
                int nowCount = Integer.parseInt(tvCartCount.getText().toString());
                //2.判断当前的值是否大于或等于最大值（库存）
                if (nowCount>=maxCount){
                    //若大于，则无法增加文本框的数字，并且btnAdd按钮无法点击，背景框变色
                    btnAdd.setClickable(false);
                    //点击btnMinus
                    btnMinus.setClickable(true);
                    Toast.makeText(context,"对不起，您所购买的药品已超过购买上限",Toast.LENGTH_SHORT).show();
                }else {
                    //添加
                    btnAdd.setClickable(true);
                    tvCartCount.setText(nowCount+1+"");
                    btnMinus.setClickable(true);
                    //将信息传入数据库
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(updateIp);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("POST");
                                JSONObject jsobj = new JSONObject();
                                jsobj.put("cartId",cart.getId());
                                jsobj.put("newCount",Integer.parseInt(tvCartCount.getText().toString()));
                                ConUtil.setOutputStream(connection,jsobj.toString());
                                ConUtil.getInputStream(connection);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                //3.获取现在的新数据
                int afterCount = Integer.parseInt(tvCartCount.getText().toString());
                dataSource.get(position).setCount(afterCount);
                notifyDataSetChanged();
                if (cbChooseOne.isChecked()){
                    //如果现在改变的item被选中，则更改totalPrice
                    tvSettlementPrice.setText(getTotalPrice()+"");
                }
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 获取当前count文本框的值
                int nowCount = Integer.parseInt(tvCartCount.getText().toString());
                // 2. 判断当前的值是否小于或等于最小值（0）
                if (nowCount <= minCount) {
                    // 若小于，则无法增加文本框的数字，并且btnMinus按钮无法点击，背景框变色
                    btnMinus.setClickable(false);
                    // 但是btnAdd可以点击，背景为正常
                    btnAdd.setClickable(true);
                    Toast.makeText(context, "不可以再减少了", Toast.LENGTH_SHORT).show();
                } else {  // 当前值小于库存，可以减少
                    btnMinus.setClickable(true);
                    tvCartCount.setText(nowCount - 1 + "");
                    btnAdd.setClickable(true);
                    // 将 修改信息传入数据库
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(updateIp);
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestMethod("POST");
                                JSONObject send = new JSONObject();
                                send.put("cartId", cart.getId());
                                send.put("newCount", Integer.parseInt(tvCartCount.getText().toString()));
                                ConUtil.setOutputStream(con, send.toString());
                                ConUtil.getInputStream(con);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                // 3. 获取现在的新数据，造成改变
                int afterCount = Integer.parseInt(tvCartCount.getText().toString());
                dataSource.get(position).setCount(afterCount);
                notifyDataSetChanged();
                if (cbChooseOne.isChecked()) {
                    // 如果现在改变的item被选中，则更改totalPrice
                    tvSettlementPrice.setText(getTotalPrice() + "");
                }
            }
        });
        return convertView;
    }

    public void setSelectCartItems() {
    }

    public void setCbChooseAllListener() {
    }
    //点击删除按钮时
    public void setButtonListener() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   List<Integer> keys = new ArrayList<>();
                   for (Integer key : selectCartItemMap.keySet()){
                       if (selectCartItemMap.get(key).isSelected()){
                           keys.add(key);
                       }
                   }
                   for (int key:keys){
                       int deleteId = selectCartItemMap.get(key).getCart().getId();
                       deleteIds.add(deleteId);
                       selectCartItemMap.remove(key);
                       for (int i = 0;i<dataSource.size();i++){
                           Cart cart = dataSource.get(i);
                           if (cart.getId()==deleteId){
                               dataSource.remove(i);
                               break;
                           }
                       }
                   }
                   //获取现在更新后的总价值，放入totalPrice中
//                   int nowPrice = getTotalPrice();
//                   tvSettlementPrice.setText(nowPrice+"");
                   //将删除的cartId发送到服务器中
                 for (final int id:deleteIds){
                     new Thread(){
                         @Override
                         public void run() {
                             try {
                                 URL url = new URL(deleteIp);
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
                 //3.展示删除成功
                 Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                 //4.如果删除后，listView中没有数据，则显示空购物车
                if (dataSource.isEmpty()){
                    lvCart.setVisibility(View.GONE);
                    llEmptyCart.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        });
        btnSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean haveShop = false;
                List<Cart> buyCart = new ArrayList<>();
                for (int key : selectCartItemMap.keySet()) {
                    if (selectCartItemMap.get(key).isSelected()){
                        buyCart.add(selectCartItemMap.get(key).getCart());
                        haveShop = true;
                    }
                }
//                if (haveShop) { // 有商品时可以进入结算界面
//                    Intent intent = new Intent(context, PlaceOrderActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("buyCart", (Serializable) buyCart);
//                    intent.putExtra("fromWhere", ShoppingCartActivity.FROM_CART);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }else
//                    Toast.makeText(context,"请选择要购买的商品",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
