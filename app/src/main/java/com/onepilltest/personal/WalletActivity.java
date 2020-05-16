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
import android.widget.TextView;
import android.widget.Toast;

import com.onepilltest.R;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.WalletBase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 个人_钱包页面
 */
public class WalletActivity extends AppCompatActivity {

    MyListener myListener = null;
    Button back = null;
    TextView cash = null;
    ListView walletList = null;
    BaseAdapter adapter = null;
    static List<WalletBase> baseList = null;
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xfff1f1f1 );
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.wallet);
        EventBus.getDefault().register(this);
        myListener = new MyListener();
        find();
        baseList = new ArrayList<>();
        
        initDate();
        adapter = new WalletAdapter(WalletActivity.this,R.layout.wallet_list,baseList);
        walletList.setAdapter(adapter);

        walletList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("WalletList:","点击");
                //点击
                Toast.makeText(getApplicationContext(),"正在点击交易记录",Toast.LENGTH_SHORT).show();
            }
        });

        setGestureListener();
    }

    private void setGestureListener() {

    }

    private void initDate() {
        cash.setText("￥"+UserBook.money);
        WalletBase base = new WalletBase(simpleDateFormat.format(date),"300",true);
        WalletBase base2 = new WalletBase(simpleDateFormat.format(date),"50",false);
        for(int i = 0;i<10;i++){
            baseList.add(base);
            baseList.add(base);
            baseList.add(base2);
        }

    }

    private void find() {
        walletList = findViewById(R.id.wallet_list);
        cash = findViewById(R.id.wallet_cash);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage msg){
        if(msg.getCode().equals("update_wallet")){
            Log.e("付款",""+msg.getJson());
            int price = Integer.valueOf(msg.getJson());
            WalletBase base = new WalletBase(simpleDateFormat.format(date),price+"",false);
            baseList.add(base);
            adapter.notifyDataSetChanged();

        }
    }
}
