package com.onepilltest.personal;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.util.LogUtil;
import com.onepilltest.util.OkhttpUtil;
import com.onepilltest.welcome.PerfectInforDoctorActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    ImageView Img = null;
    OkHttpClient okHttpClient = null;

    EditText et_nickName = null;
    EditText et_PID = null;
    EditText et_password = null;
    EditText et_phone = null;
    boolean isBack = false;
    LinearLayout my_layout = null;
    LinearLayout item_layout = null;
    TextView itemname = null;
    EditText itemtext = null;

    UserDao dao = new UserDao();
    DoctorDao doctorDao = new DoctorDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xffffffff );
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout._edit_user_info);
        EventBus.getDefault().register(this);
        myListener = new MyListener();
        okHttpClient = new OkHttpClient();
        find();
        init();
    }

    public void init(){
        if (UserBook.Code == 1){//医生
            initDoctor();
        }else if(UserBook.Code ==2){//用户
            initPatient();
        }
    }

    private void initPatient() {
        LogUtil.show(this,UserBook.NowUser.getHeadImg());
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(Connect.BASE_URL+UserBook.NowUser.getHeadImg())//本地图片的File对象
                .apply(requestOptions)
                .into(Img);
        et_nickName.setText(UserBook.NowUser.getNickName());
        et_PID.setText(UserBook.NowUser.getPID());
        et_password.setText(UserBook.NowUser.getPassword());
        et_phone.setText(UserBook.NowUser.getPhone());
    }

    private void initDoctor() {
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(Connect.BASE_URL+UserBook.NowDoctor.getHeadImg())//本地图片的File对象
                .apply(requestOptions)
                .into(Img);
        et_nickName.setText(UserBook.NowDoctor.getName());
        et_PID.setText(UserBook.NowDoctor.getPID());
        et_password.setText(UserBook.NowDoctor.getPassword());
        et_phone.setText(UserBook.NowDoctor.getPhone());

    }


    private void find() {
        Img = findViewById(R.id.edit_user_info_Img);
        et_nickName = findViewById(R.id.edit_user_info_nickName);
        et_PID = findViewById(R.id.edit_user_info_PID);
        et_password = findViewById(R.id.edit_user_info_password);
        et_phone = findViewById(R.id.edit_user_info_phone);
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

            if (cursor.moveToFirst()) {
                //获取图片的路径
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                if (true) {
                    RequestOptions requestOptions = new RequestOptions().circleCrop();
                    Glide.with(this)
                            .load(imagePath)//本地图片的File对象
                            .apply(requestOptions)
                            .into(Img);
                    /**
                     * 上传到服务器
                     */
                    // 3.1 获取 OkHttpClient 对象
                    // 3.2 Post 请求，创建 RequestBody 对象 指定上传类型：图片；指定上传内容
//                    MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");
//                    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
//                            .setType(MultipartBody.FORM)
//                            .addFormDataPart("keyVo", "上传头像");
//
////                // 可使用for循环添加img file
//                    requestBodyBuilder.addFormDataPart("files", file.getName(),
//                            RequestBody.create(MutilPart_Form_Data, file));
//                    // 3.3 其余一致
//                    RequestBody requestBody = requestBodyBuilder.build();
                    String postmsg = null;
                    if (UserBook.Code == 1){//医生
                        postmsg = "?doctorId="+UserBook.NowDoctor.getId();
                    }else if(UserBook.Code ==2){//用户
                        postmsg = "?userId="+UserBook.NowUser.getId();
                    }
                    File file = new File(imagePath);
                    RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", imagePath, image)
                            .build();

                    OkhttpUtil.post(requestBody,Connect.BASE_URL + "file/image"+postmsg).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String json = response.body().string();
                            Log.e("更新头像：",json);
                            Log.e("EditUserInfoActivity","返回的头像地址："+json);
                            Log.e("更新前的头像",""+UserBook.NowUser.getHeadImg());
                            if (UserBook.Code == 1){//医生
                                UserBook.NowDoctor.setHeadImg(json);
                            }else if(UserBook.Code ==2){//用户
                                UserBook.NowUser.setHeadImg(json);
                                Log.e("更新后的头像",""+UserBook.NowUser.getHeadImg());
                            }

                            EventMessage msg = new EventMessage();
                            msg.setCode("更新头像");
                            msg.setJson("yes");
                            EventBus.getDefault().post(msg);
                        }
                    });

//                    Request request = new Request.Builder().url(Connect.BASE_URL + "file/up"+postmsg)
//                            .post(requestBody)
//                            .build();
//                    Call call = okHttpClient.newCall(request);
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            String json = response.body().string();
//                            Log.e("更新头像成功：",json);
//                            if (UserBook.Code == 1){//医生
//                                UserBook.NowDoctor.setHeadImg(json);
//                            }else if(UserBook.Code ==2){//用户
//                                UserBook.NowUser.setHeadImg(json);
//                            }
//                            EventMessage msg = new EventMessage();
//                            msg.setCode("更新头像");
//                            msg.setJson("yes");
//                            EventBus.getDefault().post(msg);
//                        }
//                    });
                }
            }
        }
    }

    //保存
    private void save() {
        if (UserBook.Code == 1){//医生
            UserDoctor userDoctor = UserBook.NowDoctor;
            String name = et_nickName.getText().toString();
            String PID = et_PID.getText().toString();
            String password = et_password.getText().toString();
            String phone = et_phone.getText().toString();
            userDoctor.setName(name);
            userDoctor.setPID(PID);
            userDoctor.setPassword(password);
            userDoctor.setPhone(phone);
            doctorDao.update(userDoctor);
        }else if(UserBook.Code ==2){//用户
            UserPatient userPatient = UserBook.NowUser;
            Log.e("EditUser保存之前:",userPatient.toString());
            String nickName = et_nickName.getText().toString();
            String PID = et_PID.getText().toString();
            String password = et_password.getText().toString();
            String phone = et_phone.getText().toString();
            userPatient.setNickName(nickName);
            userPatient.setPID(PID);
            userPatient.setPassword(password);
            userPatient.setPhone(phone);
            Log.e("EditUser保存之后:",userPatient.toString());
            dao.update(userPatient);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage msg){

        Log.e("EditUser",msg.getCode()+msg.getJson());
        if (msg.getCode().equals("UserDao_update")){
            if(msg.getJson().equals("yes")){
                Log.e("更改NowUser",""+msg.getJson()+msg.getCode());

                String nickName = et_nickName.getText().toString();
                String PID = et_PID.getText().toString();
                String password = et_password.getText().toString();
                String phone = et_phone.getText().toString();

                UserBook.NowUser.setNickName(nickName);
                UserBook.NowUser.setPID(PID);
                UserBook.NowUser.setPassword(password);
                UserBook.NowUser.setPhone(phone);
                EventMessage msg2 = new EventMessage();
                msg2.setCode("用户信息已更新");
                EventBus.getDefault().post(msg2);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT);
            }
        }else if(msg.getCode().equals("更新头像")){
            if(msg.getJson().equals("yes")){
                RequestOptions requestOptions = new RequestOptions().circleCrop();
                Glide.with(this)
                        .load(Connect.BASE_URL+UserBook.NowUser.getHeadImg())
                        .apply(requestOptions)
                        .into(Img);
            }
        }else if(msg.getCode().equals("DoctorDao_update")){
            if(msg.getJson().equals("yes")){
                Log.e("更改NowDoctor",""+msg.getJson()+msg.getCode());

                String name = et_nickName.getText().toString();
                String PID = et_PID.getText().toString();
                String password = et_password.getText().toString();
                String phone = et_phone.getText().toString();

                UserBook.NowDoctor.setName(name);
                UserBook.NowDoctor.setPID(PID);
                UserBook.NowDoctor.setPassword(password);
                UserBook.NowDoctor.setPhone(phone);
                finish();
                EventMessage msg2 = new EventMessage();
                msg2.setCode("医生信息已更新");
                EventBus.getDefault().post(msg2);
            }else{
                Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
