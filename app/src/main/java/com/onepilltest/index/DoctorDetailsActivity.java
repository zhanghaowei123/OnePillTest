package com.onepilltest.index;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.personal.DoctorDao;
import com.onepilltest.personal.UserBook;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DoctorDetailsActivity extends AppCompatActivity {
    private ImageView headImg = null;
    UserDoctor doctor = null;
    MyListener myListener = null;
    TextView name = null;
    TextView hos = null;
    DoctorDao dao = new DoctorDao();
    Button editResume = null;
    EditText resume = null;
    Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_details);
        EventBus.getDefault().register(
                this);
        dao.searchDoctorById(18);
        find();
        myListener = new MyListener();
        context = getBaseContext();
        //init();
    }

    private void find() {
        resume = findViewById(R.id.dc_details_resume);
        resume.setOnClickListener(myListener);
        editResume = findViewById(R.id.dc_details_editResume);
        editResume.setOnClickListener(myListener);
        headImg = findViewById(R.id.dc_details_headImg);
        name = findViewById(R.id.dc_details_name);
        hos = findViewById(R.id.dc_details_hos);
    }

    private void init(){
        //头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(Connect.BASE_URL+ doctor.getHeadImg())
                .apply(requestOptions)
                .into(headImg);

        name.setText(doctor.getName());
        hos.setText(doctor.getHospital());
        if (doctor.getResume() == null){
            resume.setHint("这个人很懒，什么都没写");
        }else
        resume.setText(doctor.getResume());

        if (UserBook.Code == 1 && UserBook.NowDoctor.getDoctorId() == doctor.getDoctorId()){
            resume.setFocusableInTouchMode(true);
            resume.setFocusable(true);
            resume.requestFocus();
        }else{
            resume.setFocusable(false);
            resume.setFocusableInTouchMode(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getdate(EventMessage msg){
        Log.e("查询医生","100"+msg.getJson());
        if(msg.getCode() == "DoctorDao_searchDoctorById"){
            Gson gson = new Gson();
            doctor = gson.fromJson(msg.getJson(),UserDoctor.class);
            init();
        }
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dc_details_editResume:
                    if (UserBook.Code == 1 && UserBook.NowDoctor.getDoctorId() == doctor.getDoctorId()){
                        DoctorDao dao = new DoctorDao();
                        Log.e("修改resume","yes");
                        dao.update("resume",resume.getText().toString());
                    }else{
                        Toast.makeText(context,"无权修改",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.dc_details_resume:
                    if (UserBook.Code == 1 && UserBook.NowDoctor.getDoctorId() == doctor.getDoctorId()){
                        Toast.makeText(context,"正在修改",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"无权修改",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}
