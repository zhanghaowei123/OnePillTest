package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.onepilltest.R;
import com.onepilltest.message.QuestionActivity;

/**
 * 个人_设置_用户地址页面
 */
public class AddressActivity extends AppCompatActivity {

    Button back;
    Button bAdd = null;
    ListView addressList = null;
    MyListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout.user_address);
        myListener = new MyListener();
        find();
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
            switch (v.getId()){
                case R.id.user_address_add:
                    Intent add_intent = new Intent(AddressActivity.this, EditAddressActivity.class);
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
