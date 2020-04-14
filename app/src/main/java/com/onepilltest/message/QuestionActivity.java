package com.onepilltest.message;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Inquiry;
import com.onepilltest.index.HomeFragment;
import com.onepilltest.personal.UserBook;
import com.onepilltest.welcome.PerfectInforDoctorActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private OkHttpClient okHttpClient;
    private Request request, request1;
    private Inquiry inquiry;
    private String imagePath;
    private int mFlag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff56ced4);
        }
        setContentView(R.layout.question);
        okHttpClient = new OkHttpClient();
        myListener = new MyListener();
        find();
    }

    private void find() {
        back = findViewById(R.id.question_back);
        back.setOnClickListener(myListener);
        finish = findViewById(R.id.question_finish);
        finish.setOnClickListener(myListener);
        title = findViewById(R.id.et_kswz_title);
        upImg = findViewById(R.id.iv_upimg);
        upImg.setOnClickListener(myListener);
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
                    save();
                    finish();
                    break;
                case R.id.kswz_outline_yes:
                    yes.setBackgroundColor(getResources().getColor(R.color.doderBlue));
                    no.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    mFlag = 1;
                    break;
                case R.id.kswz_outline_no:
                    no.setBackgroundColor(getResources().getColor(R.color.doderBlue));
                    yes.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    mFlag = 0;
                    break;
                case R.id.iv_upimg:
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
     *
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
            RequestOptions requestOptions = new RequestOptions().fitCenter();
            if (cursor.moveToFirst()) {
                //获取图片的路径
                imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                upImg.setBackgroundResource(0);
                Glide.with(this)
                        .load(imagePath)//本地图片的File对象
                        .apply(requestOptions)
                        .into(upImg);
                /**
                 * 上传到服务器
                 */
                // 3.1 获取 OkHttpClient 对象
                // 3.2 Post 请求，创建 RequestBody 对象 指定上传类型：图片；指定上传内容
                MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data;charset=utf-8");
                MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("keyVo", "上传病情图片");


                File file = new File(imagePath);
                requestBodyBuilder.addFormDataPart("files", file.getName(), RequestBody.create(MutilPart_Form_Data, file));

                RequestBody requestBody = requestBodyBuilder.build();
                request1 = new Request.Builder().url(Connect.BASE_URL + "ImageServlet")
                        .post(requestBody)
                        .build();
                okHttpClient = new OkHttpClient();
                Call call = okHttpClient.newCall(request1);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("上传失败", "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("上传图片：", response.body().string());
                    }
                });
            }
        }
    }

    private void save() {
        int userId = UserBook.NowUser.getId();
        String content = main.getText().toString();
        String title1 = title.getText().toString();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(date);
        inquiry = new Inquiry();
        inquiry.setUserId(userId);
        inquiry.setContent(content);
        inquiry.setTitle(title1);
        inquiry.setTime(time);
        inquiry.setFlag(mFlag);
        inquiry.setHeadImg(UserBook.NowUser.getHeadImg());
        inquiry.setName(UserBook.NowUser.getNickName());
        inquiry.setPhone(UserBook.NowUser.getPhone());
        String jsonStr = null;
        jsonStr = new Gson().toJson(inquiry);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                jsonStr);
        request = new Request.Builder()
                .post(requestBody)
                .url(Connect.BASE_URL + "InquiryServlet")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("上传问诊记录失败", "" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("上传问诊记录：", response.body().string());
            }
        });
    }
}
