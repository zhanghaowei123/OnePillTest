package com.onepilltest.message;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.index.HomeFragment;
import com.onepilltest.welcome.PerfectInforDoctorActivity;

/**
 * 首页_快速问诊
 */
public class QuestionActivity extends AppCompatActivity {

    MyListener myListener = null;
    private Button back;
    private Button finish;
    private EditText title;
    private ImageView upImg;
    private Button yes;
    private Button no;
    private EditText main;
    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_DCIM = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        setContentView(R.layout.question);
        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.question_back);
        back.setOnClickListener(myListener);
        finish = findViewById(R.id.question_finish);
        finish.setOnClickListener(myListener);
        title = findViewById(R.id.et_kswz_title);
        upImg = findViewById(R.id.btn_upimg);
        yes = findViewById(R.id.kswz_outline_yes);
        yes.setOnClickListener(myListener);
        no = findViewById(R.id.kswz_outline_no);
        no.setOnClickListener(myListener);
        main = findViewById(R.id.kswz_main);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.question_back:
                    finish();
                    break;
                case R.id.question_finish:
                    finish();
                    break;
                case R.id.kswz_outline_yes:
                    yes.setBackgroundColor(getResources().getColor(R.color.doderBlue));
                    no.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    break;
                case R.id.kswz_outline_no:
                    no.setBackgroundColor(getResources().getColor(R.color.doderBlue));
                    yes.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    break;
                case R.id.btn_upimg:
                    //动态申请权限
                    ActivityCompat.requestPermissions(QuestionActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE);
                    break;
            }
        }
    }

    //权限提示框，用户点击允许时回调该方法
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //打开手机相册
        if (requestCode == REQUEST_IMAGE) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_DCIM);
        }
    }

    /**
     * 手机相册界面返回之后回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取照片：通过ContentResolver访问图库的ContentProvider公开的数据
        if (requestCode == REQUEST_DCIM && resultCode == RESULT_OK) {
            Uri uri = data.getData();//图片的Uri对象
            Cursor cursor = getContentResolver().query(uri, null, null,
                    null, null);
            RequestOptions requestOptions = new RequestOptions().fitCenter().override(100, 100);
            if (cursor.moveToFirst()) {
                //获取图片的路径
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
//                    imgPhoto.setBackgroundResource(0);
//                    Glide.with(this)
//                            .load(imagePath)//本地图片的File对象
//                            .apply(requestOptions)
//                            .into(imgPhoto);
            }
        }
    }
}
