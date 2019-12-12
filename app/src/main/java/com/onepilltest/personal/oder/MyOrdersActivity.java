package com.onepilltest.personal.oder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.onepilltest.R;

public class MyOrdersActivity extends AppCompatActivity{

    private ImageView ivback;
    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_order);

        myListener = new MyListener();
        find();
    }

    private void find() {
        ivback = findViewById(R.id.myorders_back);
        ivback.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.myorders_back:
                    finish();
                    break;
            }
        }
    }
}
