package com.onepilltest.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.onepilltest.R;

public class PersonalActivity extends AppCompatActivity{

    private ImageView setting;
    private LinearLayout order;
    private LinearLayout cart;
    private LinearLayout wallet;
    private LinearLayout ask;
    private LinearLayout help;
    private LinearLayout focus;
    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);

        myListener = new MyListener();
        find();
    }

    private void find() {
        setting = findViewById(R.id.iv_setting);
        setting.setOnClickListener(myListener);
        order = findViewById(R.id.ll_order);
        order.setOnClickListener(myListener);
        cart = findViewById(R.id.ll_cart);
        cart.setOnClickListener(myListener);
        wallet = findViewById(R.id.ll_wallet);
        wallet.setOnClickListener(myListener);
        ask = findViewById(R.id.ll_ask);
        ask.setOnClickListener(myListener);
        help = findViewById(R.id.ll_help);
        help.setOnClickListener(myListener);
        focus = findViewById(R.id.ll_sc);
        focus.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_setting:
                    Intent intent = new Intent(PersonalActivity.this,SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_order:
                    Intent intent1 = new Intent(PersonalActivity.this,MyOrdersActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.ll_cart:
                    Intent intent2 = new Intent(PersonalActivity.this,ShoppingCartActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.ll_wallet:
                    Intent intent3 = new Intent(PersonalActivity.this,WalletActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.ll_ask:
                    Intent intent4 = new Intent(PersonalActivity.this,recordActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.ll_help:
                    Intent intent5 = new Intent(PersonalActivity.this,HelpAndFeedBackActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.ll_sc:
                    Intent intent6 = new Intent(PersonalActivity.this,FocusAndSaveActivity.class);
                    startActivity(intent6);
                    break;
            }
        }
    }
}
