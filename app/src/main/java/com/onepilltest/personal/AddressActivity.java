package com.onepilltest.personal;

import android.content.Intent;
import android.os.Messenger;
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
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.message.QuestionActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    BaseAdapter adapter = null;
    Gson gson = new Gson();
    private List<Address> baseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        //getSupportActionBar().hide();
        setContentView(R.layout.user_address);
        myListener = new MyListener();
        find();
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);

        Toast.makeText(AddressActivity.this, "地址栏", Toast.LENGTH_SHORT).show();
        initBases();//初始化baseList数据

        //创建ContentAdapter实例，传入上下文， 子布局id ,数据baseList
        adapter = new AddressAdapter(AddressActivity.this, R.layout.user_address_item, baseList);

        addressList = (ListView) findViewById(R.id.user_address_list);
        addressList.setAdapter(adapter);//绑定适配器


        //item点击监听器
        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("click", "点击第" + position + "个item");

                Address info = baseList.get(position);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void UpdateUI(EventMessage msg) {

        if(msg.getCode().equals("AddressDao_save")){
            if(msg.getJson().equals("yes")){
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT);
            }else if(msg.getJson().equals("no")){
                Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT);
            }
        }else if(msg.getCode().equals("AddressDao_searchAll")){
            List<Address> addressList = gson.fromJson(msg.getJson(),new TypeToken<List<Address>>() {}.getType());
            baseList = addressList;
            adapter.notifyDataSetChanged();
        }
    }

    private void initBases() {

        AddressDao dao = new AddressDao();
        dao.searchAll(UserBook.NowUser.getUserId());

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
