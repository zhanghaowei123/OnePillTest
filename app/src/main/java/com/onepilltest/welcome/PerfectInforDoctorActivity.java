package com.onepilltest.welcome;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.UserDoctor;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PerfectInforDoctorActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText editName;
    private EditText editNum;
    private EditText editHosptal;
    private EditText editAddress;
    private EditText editTag;
    private ImageView imgPhoto;
    private ImageView imgPhotoback;
    private Button btnSucceed;
    private OkHttpClient okHttpClient;
    private SharedPreferences sharedPreferences;
    private UserDoctor userDoctor;
    private boolean flag = false;
    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_DCIM = 2;
    private boolean isBack = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff56ced4);
        }
        setContentView(R.layout.doctorinfo_layout);

        findViews();
        sharedPreferences = getSharedPreferences("doctorRegister", MODE_PRIVATE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplicationContext(), "请完善个人信息", Toast.LENGTH_LONG).show();
            }
        });
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请权限
                ActivityCompat.requestPermissions(PerfectInforDoctorActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE);
                isBack = false;
            }
        });

        imgPhotoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请权限
                ActivityCompat.requestPermissions(PerfectInforDoctorActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE);
                isBack = true;
            }
        });
        btnSucceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerInfo();
                if (flag) {
                    signup();
                    postUserDoctor();
                } else {
                    Toast.makeText(getApplicationContext(), "请完善个人信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(Connect.BASE_URL + "auth/register?userName="+
                                    userDoctor.getPhone()+"&password="+
                                    userDoctor.getPassword()+"&nickName="+userDoctor.getName())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().toString();
                            Log.e("ECtest,注册成功",result);
                        }
                    });
                } catch (Exception e) {
                    Log.e("ECtest","注册失败");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void postUserDoctor() {
        String jsonStr = null;
        jsonStr = new Gson().toJson(userDoctor);
        Log.e("test", jsonStr.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                jsonStr);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Connect.BASE_URL + "doctor/register")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("false", "返回错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功时回调
                String isSuccessful = response.body().string();
                if (isSuccessful.equals("true")) {
                    Log.e("successful", isSuccessful);
                    Intent intent = new Intent(PerfectInforDoctorActivity.this,
                            DoctorSuccessActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void registerInfo() {
        userDoctor = new UserDoctor();
        userDoctor.setPhone(sharedPreferences.getString("phone", ""));
        userDoctor.setPassword(sharedPreferences.getString("password", ""));
        userDoctor.setAddress(editAddress.getText().toString());
        userDoctor.setHospital(editHosptal.getText().toString());
        userDoctor.setName(editName.getText().toString());
        userDoctor.setPID(editNum.getText().toString());
        userDoctor.setTag(editTag.getText().toString());
        if (!userDoctor.getAddress().equals("")
                && !userDoctor.getHospital().equals("")
                && !userDoctor.getName().equals("")
                && !userDoctor.getPID().equals("")) {
            flag = true;
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

    @Override
    //手机相册界面返回之后回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                if (!isBack) {
                    imgPhoto.setBackgroundResource(0);
                    Glide.with(this)
                            .load(imagePath)//本地图片的File对象
                            .apply(requestOptions)
                            .into(imgPhoto);
                    /**
                     * 上传到服务器
                     */
                    // 3.1 获取 OkHttpClient 对象
                    // 3.2 Post 请求，创建 RequestBody 对象 指定上传类型：图片；指定上传内容
                    MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");
                    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("keyVo", "上传医师资格证");

                    File file = new File(imagePath);
//                // 可使用for循环添加img file
                    requestBodyBuilder.addFormDataPart("files", file.getName(),
                            RequestBody.create(MutilPart_Form_Data, file));
                    // 3.3 其余一致
                    RequestBody requestBody = requestBodyBuilder.build();
                    Request request = new Request.Builder().url(Connect.BASE_URL + "GetImageFrontServlet")
                            .post(requestBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("上传医师资格证：", response.body().string());
                        }
                    });
                } else {
                    imgPhotoback.setBackgroundResource(0);
                    Glide.with(this)
                            .load(imagePath)//本地图片的File对象
                            .apply(requestOptions)
                            .into(imgPhotoback);
                    // 3.1 获取 OkHttpClient 对象
                    // 3.2 Post 请求，创建 RequestBody 对象 指定上传类型：图片；指定上传内容
                    MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");
                    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("keyVo", "上传医师资格证");

                    File file = new File(imagePath);
//                // 可使用for循环添加img file
                    requestBodyBuilder.addFormDataPart("files", file.getName(),
                            RequestBody.create(MutilPart_Form_Data, file));
                    // 3.3 其余一致
                    RequestBody requestBody = requestBodyBuilder.build();
                    Request request = new Request.Builder().url(Connect.BASE_URL + "GetImageReverseServlet")
                            .post(requestBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("上传医师资格证：", response.body().string());
                        }
                    });
                }
            }
        }
    }

    private void findViews() {
        //1.创建OkHttpClient对象  入口
        okHttpClient = new OkHttpClient();
        editName = findViewById(R.id.perfect_doctorname);
        editNum = findViewById(R.id.perfect_dnum);
        editHosptal = findViewById(R.id.perfect_hosptal);
        editAddress = findViewById(R.id.perfect_address);
        editTag = findViewById(R.id.perfect_tag);
        imgBack = findViewById(R.id.img_left_yd);
        imgPhoto = findViewById(R.id.img_photo);
        imgPhotoback = findViewById(R.id.img_photoback);
        btnSucceed = findViewById(R.id.btn_perfect_ds);
    }


}
