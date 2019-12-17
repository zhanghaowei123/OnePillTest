package com.onepilltest.personal;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.R;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Medicine;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.entity.focus;
import com.onepilltest.entity.medicine_;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FocusListActivity extends AppCompatActivity {

    Button back;
    Button doctorTag = null;
    Button medicineTag = null;
    ListView focusList = null;
    MyListener myListener = null;
    BaseAdapter adapter = null;
    Gson gson = new Gson();
    private List<focus> baseList = new ArrayList<>();
    private List<focus> baseList1 = new ArrayList<>();
    private List<focus> baseList2 = new ArrayList<>();
    private List<UserDoctor> doctorList = new ArrayList<>();
    private List<medicine_> medicineList = new ArrayList<>();
    focusDao fDao = new focusDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xfff8f8f8);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.focus_list);
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);
        myListener = new MyListener();
        find();
        init();
    }

    public void find() {
        back = findViewById(R.id.focus_list_back);
        back.setOnClickListener(myListener);
        doctorTag = findViewById(R.id.focus_list_tab1);
        doctorTag.setOnClickListener(myListener);
        medicineTag = findViewById(R.id.focus_list_tab2);
        medicineTag.setOnClickListener(myListener);
        focusList = findViewById(R.id.focus_list_list);
        
    }

    public void init() {
        if (UserBook.Code == 1) {
            fDao.searchDoctor(UserBook.NowDoctor.getDoctorId(), 1);
            fDao.searchMedicine(UserBook.NowDoctor.getDoctorId(), 1);
        } else {
            fDao.searchDoctor(UserBook.NowUser.getUserId(), 1);
            fDao.searchMedicine(UserBook.NowUser.getUserId(), 1);
        }
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.focus_list_back:
                    finish();
                    break;
                case R.id.focus_list_tab1:
                    initTag1();
                    break;
                case R.id.focus_list_tab2:
                    initTag2();
                    break;
                default:
                    break;
            }
        }
    }

    private void initTag2() {
        baseList = baseList2;
        adapter.notifyDataSetChanged();
    }

    private void initTag1() {
        baseList = baseList2;
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage msg) {
        if (msg.getCode().equals("focusDao_searchDoctor")) {
            doctorList = gson.fromJson(msg.getJson(), new TypeToken<List<UserDoctor>>() {
            }.getType());
            setBaseList1();
        } else if (msg.getCode().equals("focusDao_searchMedicine")) {
            medicineList = gson.fromJson(msg.getJson(), new TypeToken<List<medicine_>>() {
            }.getType());
            setBaseList2();
        }
        //创建ContentAdapter实例，传入上下文， 子布局id ,数据baseList
        adapter = new FocusListAdapter(FocusListActivity.this, R.layout.focus_liste_item, baseList);

        addressList = (ListView) findViewById(R.id.user_address_list);
        addressList.setAdapter(adapter);//绑定适配器
        initTag1();
    }

    private void setBaseList1() {
        focus fs = null;
        for (int i = 0; i < doctorList.size(); i++) {
            fs.setImg(doctorList.get(i).getHeadImg());
            fs.setName(doctorList.get(i).getName());
            fs.setMore(doctorList.get(i).getTag());
            if (UserBook.Code == 1)
                fs.setUserId(UserBook.NowDoctor.getDoctorId());
            else
                fs.setUserId(UserBook.NowUser.getUserId());
            fs.setUserType(UserBook.Code);
            fs.setType(1);
            fs.setTypeId(doctorList.get(i).getDoctorId());
            baseList1.add(fs);

        }
    }

    private void setBaseList2() {
        focus fs = null;
        for (int i = 0; i < medicineList.size(); i++) {
            fs.setImg(medicineList.get(i).getImg1());
            fs.setName(medicineList.get(i).getGeneralName());
            fs.setMore(medicineList.get(i).getStandard());
            if (UserBook.Code == 1)
                fs.setUserId(UserBook.NowDoctor.getDoctorId());
            else
                fs.setUserId(UserBook.NowUser.getUserId());
            fs.setUserType(UserBook.Code);
            fs.setType(2);
            fs.setTypeId(medicineList.get(i).getId());
            baseList2.add(fs);
        }
    }
}
