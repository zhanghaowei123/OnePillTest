package com.onepilltest.index;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_details);
        EventBus.getDefault().register(this);
        dao.searchDoctorById(18);
        find();
        init();
    }

    private void find() {
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
        hos.setText(doctor.getTag());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getdate(EventMessage msg){
        if(msg.getCode() == "DoctorDao_searchDoctorById"){
            Gson gson = new Gson();
            doctor = gson.fromJson(msg.getJson(),UserDoctor.class);
        }
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }
}
