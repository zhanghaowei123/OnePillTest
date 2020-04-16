package com.onepilltest.index;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.personal.DoctorDao;
import com.onepilltest.personal.UserBook;
import com.onepilltest.personal.focusDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DoctorDetailsActivity extends AppCompatActivity {
    private ImageView headImg = null;//头像
    UserDoctor doctor = null;
    MyListener myListener = null;
    TextView name = null;//名字
    TextView hos = null;//所在医院
    DoctorDao dao = new DoctorDao();
    Button editResume = null;//提交按钮
    EditText resume = null;//简介
    Context context = null;
    LinearLayout li_focus = null;
    focusDao fDao = new focusDao();
    ImageView img = null;
    boolean isFocus = false;
    //设置DoctorId来显示不同的医生详情页
    int DoctorId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff808080 );
        }
        setContentView(R.layout.doctor_details);
        EventBus.getDefault().register(this);
        setId();
        dao.searchDoctorById(DoctorId);
        myListener = new MyListener();
        find();
        context = getBaseContext();
        //init();
    }

    public void setId(){
        /*使用putExtra（）方法传递数据
        intent.putExtra("name","从mainActivity传过来的数据");*/
        int id = getIntent().getIntExtra("id",19);
        this.DoctorId = id;
    }
    private void find() {
        img = findViewById(R.id.doctor_focus_img);
        li_focus = findViewById(R.id.doctor_focus);
        li_focus.setOnClickListener(myListener);
        resume = findViewById(R.id.dc_details_resume);
        resume.setOnClickListener(myListener);
        editResume = findViewById(R.id.dc_details_editResume);
        editResume.setOnClickListener(myListener);
        headImg = findViewById(R.id.dc_details_headImg);
        name = findViewById(R.id.dc_details_name);
        hos = findViewById(R.id.dc_details_hos);
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if(!isDestroyed()){
                //头像
                RequestOptions requestOptions = new RequestOptions().circleCrop();
                Glide.with(this)
                        .load(Connect.BASE_URL+ doctor.getHeadImg())
                        .apply(requestOptions)
                        .into(headImg);
            }
        }


        name.setText(doctor.getName());
        hos.setText(doctor.getHospital());
        if (doctor.getResume() == null){
            resume.setHint("查无此人！");
        }else
        resume.setText(doctor.getResume());

        if (UserBook.Code == 1 && UserBook.NowDoctor.getId() == doctor.getId()){
            resume.setFocusableInTouchMode(true);
            resume.setFocusable(true);
            resume.requestFocus();
        }else{
            resume.setFocusable(false);
            resume.setFocusableInTouchMode(false);
        }

        if (UserBook.Code ==1){
            fDao.isHave(UserBook.NowDoctor.getId(),1,1,doctor.getId());
        }else{
            fDao.isHave(UserBook.NowUser.getId(),2,1,doctor.getId());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getdate(EventMessage msg){
        Log.e("找医生",""+msg.getCode()+msg.getJson());
        if(msg.getCode().equals("DoctorDao_searchDoctorById")){
            Gson gson = new Gson();
            doctor = gson.fromJson(msg.getJson(),UserDoctor.class);
            init();
        }else if(msg.getCode().equals("focusDao_isHave")){
            if (msg.getJson().equals("yes")){
                isFocus = true;
                img.setImageResource(R.drawable.isfocus);
            }else{
                isFocus = false;
                img.setImageResource(R.drawable.notfocus);
            }
        }else if (msg.getCode().equals("focusDao_del")){
            if (msg.getJson().equals("yes")){
                isFocus = false;
                Toast.makeText(getApplicationContext(),"已取消",Toast.LENGTH_SHORT).show();
                img.setImageResource(R.drawable.notfocus);
            }else{
                isFocus = true;
                Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();

            }
        }else if (msg.getCode().equals("focusDao_add")){
            if (msg.getJson().equals("yes")){
                isFocus = true;
                Toast.makeText(getApplicationContext(),"已关注",Toast.LENGTH_SHORT).show();
                img.setImageResource(R.drawable.isfocus);
            }else{
                isFocus = false;
                Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dc_details_editResume:
                    if (UserBook.Code == 1 && UserBook.NowDoctor.getId() == doctor.getId()){
                        DoctorDao dao = new DoctorDao();
                        Log.e("正在修改resume","yes");
                        Toast.makeText(context,"正在提交修改",Toast.LENGTH_SHORT).show();
                        dao.update("resume",resume.getText().toString());
                    }else{
                        Log.e("无权修改resume","no");
                        Toast.makeText(context,"无权修改",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.dc_details_resume:
                    if (UserBook.Code == 1 && UserBook.NowDoctor.getId() == doctor.getId()){
                        Toast.makeText(context,"正在修改",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"无权修改",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.doctor_focus://关注医生
                    if (isFocus){
                        if (UserBook.Code ==1){
                            fDao.del(UserBook.NowDoctor.getId(),1,1,doctor.getId());
                        }else{
                            fDao.del(UserBook.NowUser.getId(),2,1,doctor.getId());
                        }
                    }else{
                        if (UserBook.Code ==1){
                            fDao.add(UserBook.NowDoctor.getId(),1,1,doctor.getId());
                        }else{
                            fDao.add(UserBook.NowUser.getId(),2,1,doctor.getId());
                        }
                    }
                    /*if (UserBook.Code ==1){
                        fDao.add(UserBook.NowDoctor.getDoctorId(),1,1,doctor.getDoctorId());
                    }else{
                        fDao.add(UserBook.NowUser.getUserId(),2,1,doctor.getDoctorId());
                    }*/


                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
