package com.onepilltest.personal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.entity.Address;

/**
 * 个人_设置_用户地址_编辑地址界面
 */
public class EditAddressActivity extends AppCompatActivity {

    EditText et_name = null;
    EditText et_phone = null;
    EditText et_address = null;
    EditText et_more = null;
    EditText et_postalCode = null;

    Button back = null;
    Button save = null;
    MyListener myListener = null;
    Address address = null;
    Gson gson = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        //getSupportActionBar().hide();
        setContentView(R.layout.edit_address);
        myListener = new MyListener();
        find();
        gson = new Gson();
        Bundle info = getIntent().getExtras();
        //获取Bundle的信息
        String json=info.getString("info");
        address = gson.fromJson(json,Address.class);
        init(address);
    }

    private void init(Address address){
        et_name.setText(address.getName());
        et_phone.setText(address.getPhoneNumber());
        et_address.setText(address.getAddress());
        et_more.setText(address.getMore());
        et_postalCode.setText(address.getPostalCode());
    }

    private void find() {

        et_address = findViewById(R.id.edit_address_address);
        et_name = findViewById(R.id.edit_address_name);
        et_more = findViewById(R.id.edit_address_more);
        et_phone = findViewById(R.id.edit_address_phoneNumber);
        et_postalCode = findViewById(R.id.edit_address_postalCode);

        back = findViewById(R.id.edit_address_back);
        back.setOnClickListener(myListener);
        save = findViewById(R.id.edit_address_save);
        save.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_address_back:
                    Intent back_intent = new Intent(EditAddressActivity.this, AddressActivity.class);
                    startActivity(back_intent);
                    break;
                case R.id.edit_address_save:

                    //存入数据库

                    Intent add_intent = new Intent(EditAddressActivity.this, AddressActivity.class);
                    startActivity(add_intent);
                    break;
            }
        }
    }
}
