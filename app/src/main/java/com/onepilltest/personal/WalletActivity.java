package com.onepilltest.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.onepilltest.R;
import com.onepilltest.entity.WalletBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人_钱包页面
 */
public class WalletActivity extends AppCompatActivity {

    MyListener myListener = null;
    Button back = null;
    ListView walletList = null;
    List<WalletBase> baseList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        setContentView(R.layout.wallet);
        myListener = new MyListener();
        find();
        baseList = new ArrayList<>();
        
        initDate();
        BaseAdapter adapter = new WalletAdapter(WalletActivity.this,R.layout.wallet_list,baseList);
        
        walletList.setAdapter(adapter);
        walletList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击
            }
        });
    }

    private void initDate() {
        WalletBase base = new WalletBase("测试时间2019","300",true);
        WalletBase base2 = new WalletBase("测试时间2017","50",false);
        for(int i = 0;i<10;i++){
            baseList.add(base);
            baseList.add(base);
            baseList.add(base2);
        }

    }

    private void find() {
        walletList = findViewById(R.id.wallet_list);
        back = findViewById(R.id.wallet_back);
        back.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.wallet_back:
                    finish();
                    break;
            }
        }
    }
}
