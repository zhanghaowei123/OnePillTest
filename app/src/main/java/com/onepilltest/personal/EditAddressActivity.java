package com.onepilltest.personal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepilltest.R;

/**
 * 个人_设置_用户地址_编辑地址界面
 */
public class EditAddressActivity extends AppCompatActivity {

    Button back = null;
    Button save = null;
    MyListener myListener = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout.edit_address);
        myListener = new MyListener();
        find();
    }

    private void find() {
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
                    Intent add_intent = new Intent(EditAddressActivity.this, changedActivity.class);
                    startActivity(add_intent);
                    break;
            }
        }
    }
}
