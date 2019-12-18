package com.onepilltest.personal;

import android.content.Intent;
import android.os.Build;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffffffff);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.user_address);
        myListener = new MyListener();
        find();
        init();
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);

    }

    public void init() {
        if (UserBook.Code == 1) {//医生
            initDoctor();
        } else if (UserBook.Code == 2) {//用户
            initPatient();
        }
    }

    //初始化用户信息
    private void initPatient() {
        Log.e("用户" + UserBook.NowUser.getNickName(), "进入地址列表");
        AddressDao dao = new AddressDao();
        dao.searchAll(UserBook.NowUser.getUserId());
        Log.e("更新地址列表", "" + baseList.toString());
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
                bundle.putString("info", gson.toJson(info));

                Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
    }

    //初始化医生信息------>医生修改页面没写
    private void initDoctor() {
        Log.e("用户" + UserBook.NowDoctor.getName(), "进入地址列表");
        AddressDao dao = new AddressDao();
        dao.searchDoctorAll(UserBook.NowDoctor.getDoctorId());
        Log.e("更新地址列表", "" + baseList.toString());
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
                bundle.putString("info", gson.toJson(info));

                Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("用户" + UserBook.NowUser.getNickName(), "退出地址列表");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpdateUI(EventMessage msg) {


        if (msg.getCode().equals("AddressDao_save")) {
            if (msg.getJson().equals("yes")) {
                new AddressDao().searchAll(UserBook.NowUser.getUserId());
                Log.e("添加成功", "" + baseList.get(0).getAddress());
                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT);
            } else if (msg.getJson().equals("no")) {
                Log.e("添加失败", "" + baseList.get(0).getAddress());
                Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT);
            }
        } else if (msg.getCode().equals("AddressDao_searchAll")) {
            List<Address> addressList = gson.fromJson(msg.getJson(), new TypeToken<List<Address>>() {
            }.getType());
            //baseList = new ArrayList<>();
            baseList.clear();
            baseList.addAll(addressList);
            Log.e("接收到EventBus", "" + baseList.get(0).getAddress());
            adapter.notifyDataSetChanged();
        }else if (msg.getCode().equals("AddressDao_searchDoctorAll")) {
            List<Address> addressList = gson.fromJson(msg.getJson(), new TypeToken<List<Address>>() {
            }.getType());
            //baseList = new ArrayList<>();
            baseList.clear();
            baseList.addAll(addressList);
            Log.e("接收到EventBus", "" + baseList.get(0).getAddress());
            adapter.notifyDataSetChanged();
        } else if (msg.getCode().equals("AddressDao_update")) {
            if (msg.getJson().equals("yes")) {
                new AddressDao().searchAll(UserBook.NowUser.getUserId());
                Log.e("更新成功", "" + baseList.get(0).getAddress());
                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT);
            } else if (msg.getJson().equals("no")) {
                Log.e("更新失败", "" + baseList.get(0).getAddress());
                Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT);
            }
        } else if (msg.getCode().equals("AddressDao_delete")) {
            if (msg.getJson().equals("yes")) {
                new AddressDao().searchAll(UserBook.NowUser.getUserId());
                Log.e("添加成功", "" + baseList.get(0).getAddress());
                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT);
            } else if (msg.getJson().equals("no")) {
                Log.e("添加失败", "" + baseList.get(0).getAddress());
                Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT);
            }
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
                    Log.e("用户" + UserBook.NowUser.getNickName(), "点击添加按钮");
                    Intent add_intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                    startActivity(add_intent);
                    break;
                case R.id.user_address_back:
                    Log.e("用户" + UserBook.NowUser.getNickName(), "点击返回按钮");
                    Intent back_intent = new Intent(AddressActivity.this, SettingActivity.class);
                    startActivity(back_intent);
                    break;
            }
        }
    }




}
