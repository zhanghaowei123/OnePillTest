package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.index.HomeActivity;
import com.onepilltest.index.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    ListView userList = null;
    BaseAdapter adapter = null;
    private List<UserPatient> baseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        initBases();//初始化baseList数据
        //创建ContentAdapter实例，传入上下文， 子布局id ,数据baseList
        adapter = new UserListAdapter(UserList.this, R.layout.user_list_listview_item, baseList);
        userList = (ListView) findViewById(R.id.user_list_listview);
        userList.setAdapter(adapter);//绑定适配器

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserPatient base = baseList.get(position);
                UserBook.addUser(base,UserBook.Patient);
                //UserBook.NowUser = baseList.get(position);

                Intent intent = new Intent(UserList.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void initBases() {
        baseList = UserBook.getList();
        Log.e("base",""+UserBook.print());
    }
}
