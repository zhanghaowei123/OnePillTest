package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.entity.Address;
import com.onepilltest.message.QuestionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人_设置_用户地址页面
 */
public class AddressActivity extends AppCompatActivity {

    Button back;
    Button bAdd = null;
    ListView addressList = null;
    MyListener myListener = null;
    private List<AddressBase> baseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout.user_address);
        myListener = new MyListener();
        find();

        Toast.makeText(AddressActivity.this, "地址栏", Toast.LENGTH_SHORT).show();
        initBases();//初始化baseList数据

        //创建ContentAdapter实例，传入上下文， 子布局id ,数据baseList
        BaseAdapter adapter = new AddressAdapter(AddressActivity.this, R.layout.user_address_item, baseList);

        addressList = (ListView) findViewById(R.id.user_address_list);
        addressList.setAdapter(adapter);//绑定适配器


        //item点击监听器
        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("click", "点击第" + position + "个item");

                AddressBase info = baseList.get(position);
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                bundle.putString("info", gson.toJson(info.getAddress()));

                Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);


            }
        });

    }

    private void initBases() {

        Address address = new Address(0,  "Liuhua", "18831107935", "河北-石家庄", "河北师范大学软件学院406", "050600");
        Address address1 = new Address(0,  "Charlotte", "18831107935", "河北-石家庄", "河北师范大学软件学院406", "050600");
        for (int i = 0; i < 5; i++) {
            AddressBase baseOne = new AddressBase(R.drawable.img1, address);
            baseList.add(baseOne);
            AddressBase baseTwo = new AddressBase(R.drawable.img2, address1);
            baseList.add(baseTwo);
            AddressBase baseThree = new AddressBase(R.drawable.img1, address);
            baseList.add(baseThree);
            AddressBase baseFore = new AddressBase(R.drawable.img2, address1);
            baseList.add(baseFore);
        }

    }


    private void find() {
        back = findViewById(R.id.user_address_back);
        back.setOnClickListener(myListener);
        addressList = findViewById(R.id.user_address_list);
        bAdd = findViewById(R.id.user_address_add);
        bAdd.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.user_address_add:
                    Intent add_intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                    startActivity(add_intent);
                    break;
                case R.id.user_address_back:
                    Intent back_intent = new Intent(AddressActivity.this, SettingActivity.class);
                    startActivity(back_intent);
                    break;
            }
        }
    }


}
