package com.onepilltest.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.onepilltest.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity{

    private List<Cart> cart = new ArrayList<>();
    private RecyclerView recyclerView;
    private ShoppingCartAdapter shoppingCartAdapter = null;
    private TextView tvprice;
    private ImageView ivback;
    private Button btnpay;
    private CheckBox cb;
    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);

        myListener = new MyListener();
        find();
    }

    private void find() {
        ivback = findViewById(R.id.cart_back);
        ivback.setOnClickListener(myListener);
        cb = findViewById(R.id.cb_allcheck);
        cb.setOnClickListener(myListener);
        tvprice = findViewById(R.id.tv_allprice);
        tvprice.setOnClickListener(myListener);
        btnpay = findViewById(R.id.sc_pay);
        btnpay.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cart_back:
                    Intent intent = new Intent(ShoppingCartActivity.this, PersonalActivity.class);
                    startActivity(intent);
                    break;
//                case R.id.sc_pay:
//                    Intent intent1 = new Intent(ShoppingCartActivity.this, SweepActivity.class);
//                    startActivity(intent1);
//                    break;
            }
        }
    }
}
