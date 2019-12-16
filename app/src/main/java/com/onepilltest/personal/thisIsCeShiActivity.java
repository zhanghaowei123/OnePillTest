package com.onepilltest.personal;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.onepilltest.R;
import com.onepilltest.entity.QR;

import java.util.HashMap;
import java.util.Map;

public class thisIsCeShiActivity extends AppCompatActivity {

    //二维码尺寸
    private int width = 300;
    private int height = 300;
    private ImageView imageView;
    private Bitmap bit;
    private Button mybutton;
    private EditText myeditText;
    MyListener myListener = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.this_is_ce_shi);
        myListener = new MyListener();
        initView();

    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.ceshi_img);
        mybutton = (Button) findViewById(R.id.ceshi_btn);
        mybutton.setOnClickListener(myListener);
        myeditText = (EditText) findViewById(R.id.ceshi_text);
        myeditText.setOnClickListener(myListener);
    }



    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ceshi_btn:
                    String name=myeditText.getText().toString();
                    if(name.equals("")){
                        myeditText.setError("请输入内容");
                    }else{
                        imageView.setImageBitmap(QR.getQR(300,300,"Charlotte"));
                    }

                    break;
            }
        }
    }

    private void zxing(String name){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); //记得要自定义长宽
        BitMatrix encode = null;
        try {
            encode = qrCodeWriter.encode(name, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int[] colors = new int[width * height];
        //利用for循环将要表示的信息写出来
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (encode.get(i, j)) {
                    colors[i * width + j] = Color.BLACK;
                } else {
                    colors[i * width + j] = Color.WHITE;
                }
            }
        }

        bit = Bitmap.createBitmap(colors, width, height, Bitmap.Config.RGB_565);
        imageView.setImageBitmap(bit);
    }

}
