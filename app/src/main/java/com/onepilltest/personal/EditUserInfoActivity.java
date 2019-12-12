package com.onepilltest.personal;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.welcome.PerfectInforDoctorActivity;

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

public class EditUserInfoActivity extends AppCompatActivity {

    MyListener myListener = null;
    Button back = null;
    Button save = null;
    TextView setImg = null;
    com.onepilltest.others.RoundImageView Img = null;
    OkHttpClient okHttpClient = null;

    EditText et_nickName = null;
    EditText et_PID = null;
    EditText et_password = null;
    EditText et_phone = null;
    boolean isBack = false;

    UserDao dao = new UserDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        //getSupportActionBar().hide();
        setContentView(R.layout._edit_user_info);
        myListener = new MyListener();
        find();
    }

    private void find() {
        Img = findViewById(R.id.edit_user_info_Img);
        et_nickName = findViewById(R.id.edit_user_info_nickName);
        et_nickName.setText(UserBook.NowUser.getNickName());
        et_PID = findViewById(R.id.edit_user_info_PID);
        et_PID.setText(UserBook.NowUser.getPID());
        et_password = findViewById(R.id.edit_user_info_password);
        et_password.setText(UserBook.NowUser.getPassword());
        et_phone = findViewById(R.id.edit_user_info_phone);
        et_phone.setText(UserBook.NowUser.getPhone());
        back = findViewById(R.id.edit_user_info_back);
        back.setOnClickListener(myListener);
        save = findViewById(R.id.edit_user_info_save);
        save.setOnClickListener(myListener);
        setImg = findViewById(R.id.edit_user_info_setImg);
        setImg.setOnClickListener(myListener);
    }


    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.edit_user_info_back:
                    finish();
                    break;
                case R.id.edit_user_info_save://跳转到修改成功界面
                    save();
                    finish();
                    break;
                case R.id.edit_user_info_setImg:
                    setImage();
                    break;
            }
        }
    }

    //修改头像
    private void setImage() {
        //动态申请权限
        ActivityCompat.requestPermissions(EditUserInfoActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        isBack = true;
    }

    //权限提示框，用户点击允许时回调该方法
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //打开手机相册
        if (requestCode == 1) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        }
    }

    @Override
    //手机相册界面返回之后回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取照片：通过ContentResolver访问图库的ContentProvider公开的数据
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();//图片的Uri对象
            Cursor cursor = getContentResolver().query(uri, null, null,
                    null, null);
            RequestOptions requestOptions = new RequestOptions().fitCenter().override(100, 100);
            if (cursor.moveToFirst()) {
                //获取图片的路径
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                if (!isBack) {
                    Glide.with(this)
                            .load(imagePath)//本地图片的File对象
                            .into(Img);
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
                            Log.e("上传头像：", response.body().string());
                        }
                    });
                }
            }
        }
    }

    //保存
    private void save() {
        String nickName = et_nickName.getText().toString();
        String PID = et_PID.getText().toString();
        String password = et_password.getText().toString();
        String phone = et_phone.getText().toString();
        int UserId = UserBook.NowUser.getUserId();
        dao.update("nickName",nickName);
        dao.update("password",password);
        dao.update("phone",phone);
        dao.update("PID",PID);

    }
}
