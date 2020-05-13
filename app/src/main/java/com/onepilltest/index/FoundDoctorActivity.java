package com.onepilltest.index;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.personal.DoctorDao;
import com.onepilltest.personal.SettingForUsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FoundDoctorActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText editSelect;
    private ImageView imgSelect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xff56ced4 );
        }
        setContentView ( R.layout.found_doctor_layout );
        EventBus.getDefault().register(this);
        imgBack = findViewById ( R.id.findoctor_left );
        editSelect =findViewById ( R.id.findoctor_select );//输入框
        imgSelect = findViewById ( R.id.img_select );//搜索按钮
        imgBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editSelect.getText().toString();
                Log.e("找医生","点击查找按钮");
                Toast.makeText(getApplicationContext(),"正在查询"+name+"医生",Toast.LENGTH_SHORT).show();
                DoctorDao dao = new DoctorDao();
                dao.searchDoctorByName(name);
            }
        });
    }


    public void buttonClick(View v){
        switch (v.getId ()){
            case R.id.tb_one://流感
                Toast.makeText(getApplicationContext(),"流感",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                break;
            case R.id.tb_two://头痛
                Toast.makeText(getApplicationContext(),"头痛",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent1.putExtra("id",2);
                startActivity(intent1);
                break;
            case R.id.tb_three://伤寒
                Toast.makeText(getApplicationContext(),"伤寒",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent2.putExtra("id",1);
                startActivity(intent2);
                break;
            case R.id.tb_four://宝宝健康
                Toast.makeText(getApplicationContext(),"宝宝健康",Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent3.putExtra("id",1);
                startActivity(intent3);
                break;
            case R.id.tb_five://肠胃
                Toast.makeText(getApplicationContext(),"肠胃",Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent4.putExtra("id",18);
                startActivity(intent4);
                break;
            case R.id.tb_six://皮肤
                Toast.makeText(getApplicationContext(),"皮肤",Toast.LENGTH_SHORT).show();
                Intent intent5 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent5.putExtra("id",2);
                startActivity(intent5);
                break;
            case R.id.tb_seven://肥胖
                Toast.makeText(getApplicationContext(),"肥胖",Toast.LENGTH_SHORT).show();
                Intent intent6 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent6.putExtra("id",1);
                startActivity(intent6);
                break;
            case R.id.tb_eight://月经不调
                Toast.makeText(getApplicationContext(),"月经不调",Toast.LENGTH_SHORT).show();
                Intent intent7 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent7.putExtra("id",2);
                startActivity(intent7);
                break;
            case R.id.tb_nine://心血管
                Toast.makeText(getApplicationContext(),"心血管",Toast.LENGTH_SHORT).show();
                Intent intent8 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent8.putExtra("id",1);
                startActivity(intent8);
                break;
            case R.id.tb_ten://鼻炎
                Toast.makeText(getApplicationContext(),"鼻炎",Toast.LENGTH_SHORT).show();
                Intent intent9 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent9.putExtra("id",1);
                startActivity(intent9);
                break;
            case R.id.tb_eleven://焦虑
                Toast.makeText(getApplicationContext(),"焦虑",Toast.LENGTH_SHORT).show();
                Intent intent10 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent10.putExtra("id",2);
                startActivity(intent10);
                break;
            case R.id.tb_twelve://空腔问题
                Toast.makeText(getApplicationContext(),"空腔问题",Toast.LENGTH_SHORT).show();
                Intent intent11 = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
                intent11.putExtra("id",2);
                startActivity(intent11);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getId(EventMessage msg){
        if(msg.getCode().equals("DoctorDao_searchDoctorByName")){
            Gson gson = new Gson();
            Log.e("找医生",""+msg.getJson());
            UserDoctor doctor = gson.fromJson(msg.getJson(),UserDoctor.class);
            Intent intent = new Intent(FoundDoctorActivity.this, DoctorDetailsActivity.class);
            intent.putExtra("id",doctor.getId());
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
