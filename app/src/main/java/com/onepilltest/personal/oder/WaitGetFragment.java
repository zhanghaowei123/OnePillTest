package com.onepilltest.personal.oder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onepilltest.R;
import com.onepilltest.URL.ConUtil;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.Medicine;
import com.onepilltest.entity.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WaitGetFragment extends Fragment {
    private List<Order> waitGetOrder = new ArrayList<>();
    private ListView lvWaitGet = null;
    private ListOrderAdapter adapter = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_order_waitget_buyer,container,false);
        lvWaitGet = view.findViewById(R.id.lv_order_waitget);
        GetWaitGetOrderListTask task = new GetWaitGetOrderListTask();
        task.execute();
        return view;
    }
    private class GetWaitGetOrderListTask extends AsyncTask {
        @Override
        protected void onPostExecute(Object o) {
            adapter = new ListOrderAdapter(getContext(), waitGetOrder, R.layout.order_patient_item);
            lvWaitGet.setAdapter(adapter);
            lvWaitGet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Order order = waitGetOrder.get(position);
                    Intent intent = new Intent(getContext(),BuyerOrdersActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", (Serializable) order);
                    intent.putExtras(bundle);
                    intent.putExtra("enterFragmentId",BuyerOrdersActivity.SHOW_WAITGET);
                    startActivityForResult(intent,AllFragment.WAITGET_TO_DETAIL);
                }
            });
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            waitGetOrder.clear();
            try {
                URL url = new URL(BuyerOrdersActivity.ORDER_LIST_CON_IP);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                JSONObject send = new JSONObject();
                send.put("buyerId", getActivity().getSharedPreferences("买家登陆", Context.MODE_PRIVATE).getInt("account", 0));
                send.put("listType", BuyerOrdersActivity.SHOW_WAITGET);
                ConUtil.setOutputStream(con, send.toString());
                String get = ConUtil.getInputStream(con);
                JSONArray getArray = new JSONArray(get);
                dealWithJsonArray(getArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void dealWithJsonArray(JSONArray getArray) {
        for (int i = 0; i < getArray.length(); i++) {
            try {
                JSONObject object = getArray.getJSONObject(i);
                Address address = new Address();
                Medicine medicine = new Medicine();
                Order order = new Order();
                address.setId(object.getInt("addressId"));
                address.setName(object.getString("addressName"));
                address.setPhoneNumber(object.getString("addressPhone"));
                address.setUserId(object.getInt("addressUserId"));
                address.setAddress(object.getString("address"));
                address.setMore(object.getString("addressMore"));
                address.setPostalCode(object.getString("addressType"));
                address.setUserId(getActivity().getSharedPreferences("买家登陆", Context.MODE_PRIVATE).getInt("account", 0));
                order.setAddress(address);
                medicine.setId(object.getInt("medicinId"));
                medicine.setMedicineName(object.getString("medicinName"));
                medicine.setIntrodution(object.getString("medicineIntrodution"));
                medicine.setPrice(object.getInt("medicinePrice"));
                medicine.setStocks(object.getInt("medicineStock"));
                medicine.setStandard(object.getString("medicineSize"));
                order.setMedicine(medicine);
                order.setCount(object.getInt("count"));
                order.setType(object.getInt("type"));
                order.setId(object.getInt("id"));
                waitGetOrder.add(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GetWaitGetOrderListTask task = new GetWaitGetOrderListTask();
        task.execute();
    }
}
