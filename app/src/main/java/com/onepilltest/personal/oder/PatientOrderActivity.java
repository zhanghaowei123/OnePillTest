package com.onepilltest.personal.oder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PatientOrderActivity extends AppCompatActivity {
    private ImageView imgBack;
    private ListView listView = null;
    private List<Order> dataSource = new ArrayList<>();
    private PatientOdertestAdapter adapter = null;
    public static final String ORDER_PATIENT_ID = Connect.BASE_URL+"/findByUserId";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordertest_patient_layout);
        listView = findViewById(R.id.ordertest_list);
        imgBack = findViewById(R.id.ordertest_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FormListTask task = new FormListTask ();
        task.execute(ORDER_PATIENT_ID);
    }
    private class FormListTask extends AsyncTask{
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new PatientOdertestAdapter(PatientOrderActivity.this,dataSource,R.layout.ordertest_patient_item);
            listView.setAdapter(adapter);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL((String) objects[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod ( "POST" );
                // 获取数据
                InputStream is = con.getInputStream ();
                byte[] temp = new byte[255];
                int len;
                StringBuffer sb = new StringBuffer ();
                while ((len = is.read ( temp )) != -1) {
                    sb.append ( new String ( temp, 0, len ) );
                }
                String get = new String ( sb );

                // 把get数据转化为JSONArray
                JSONArray getArray = new JSONArray ( get );
                for (int i = 0; i < getArray.length (); i++) {
                    JSONObject obj = getArray.getJSONObject ( i );
                    Order map = new Order ();
                    map.setId(obj.getInt("id"));
                    map.setSize(obj.getString("size"));
                    map.setUserId(obj.getInt("userId"));
                    map.setMedicineId(obj.getInt("medicineId"));
                    map.setCount(obj.getInt("count"));
                    map.setMedicineName(obj.getString("medicineName"));
                    map.setPrice(obj.getInt("price"));
                    Log.e("map",obj.getInt("price")+"");
                    dataSource.add ( map );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
