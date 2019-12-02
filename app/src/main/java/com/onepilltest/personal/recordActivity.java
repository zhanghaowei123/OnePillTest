package com.onepilltest.personal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.onepilltest.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class recordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        //页面内容皆从数据库传入
        MyListener myListener = new MyListener();
        Button btn_right = findViewById(R.id.btn_right);
        btn_right.setOnClickListener(myListener);
        Button btn_left = findViewById(R.id.btn_left);
        btn_left.setOnClickListener(myListener);

        ImageView imageView = findViewById(R.id.iv_portrait);
        TextView tv = findViewById(R.id.tv_nickname);
        TextView tv_text = findViewById(R.id.tv_text);
        tv_text.setText("浔阳江头夜送客， 枫叶荻花秋瑟瑟。 主人下马客在船， 举酒欲饮无管弦。 " +
                        "醉不成欢惨将别， 别时茫茫江浸月。 忽闻水上琵琶声， 主人忘归客不发。" +
                " 寻声暗问弹者谁？ 琵琶声停欲语迟。");//（测试内容位置）

        //病例日期，暂且放系统时间
        TextView textView = findViewById(R.id.tv_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        String time = (simpleDateFormat.format(date));
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        textView.setText(y+"年"+m+"月"+d+"日");


    }
    private class MyListener implements View.OnClickListener{

        TextView textView = findViewById(R.id.tv_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        String time = (simpleDateFormat.format(date));
        Calendar c = Calendar.getInstance();


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_left:
                    c.add(Calendar.DAY_OF_MONTH,-2);
//                    c.add(Calendar.MONTH,1);
                    int y = c.get(Calendar.YEAR);
                    int m = c.get(Calendar.MONTH);
                    int d = c.get(Calendar.DAY_OF_MONTH);
                    textView.setText(y+"年"+m+"月"+d+"日");
                case R.id.btn_right:
                    c.add(Calendar.DATE,1);
                    int y1 = c.get(Calendar.YEAR);
                    int m1 = c.get(Calendar.MONTH);
                    int d1 = c.get(Calendar.DATE);
                    textView.setText(y1+"年"+m1+"月"+d1+"日");
            }
        }
    }

}
