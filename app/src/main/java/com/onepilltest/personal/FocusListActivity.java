package com.onepilltest.personal;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.entity.Dao.focusDao;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.ToFocus;
import com.onepilltest.others.MyRecyclerView;
import com.onepilltest.others.SwipeMenu;
import com.onepilltest.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FocusListActivity extends BaseActivity {

    Button back;
    Button doctorTag = null;
    Button medicineTag = null;
    ListView focusList = null;
    MyListener myListener = null;
    FocusListAdapter2 adapter = null;
    Gson gson = new Gson();
    ToFocus fs = null;
    private List<ToFocus> baseList = new ArrayList<>();
    private List<ToFocus> baseList1 = new ArrayList<>();
    private List<ToFocus> baseList2 = new ArrayList<>();
    private List<ToFocus> doctorList = new ArrayList<>();
    private List<ToFocus> medicineList = new ArrayList<>();
    focusDao fDao = new focusDao();
    private SwipeMenu swipeMenu = null;
    private MyRecyclerView recyclerView = null;
    private View mEmptyView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xfff8f8f8);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.focus_list);
        //将主线程注册成为订阅者
        EventBus.getDefault().register(this);
        myListener = new MyListener();
        find();
        init();

        initBar(this);
    }


    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }

    @Override
    public int intiLayout() {
        return R.layout.focus_list;
    }

    public void find() {
        back = findViewById(R.id.focus_list_back);
        back.setOnClickListener(myListener);
        doctorTag = findViewById(R.id.focus_list_tab1);
        doctorTag.setOnClickListener(myListener);
        medicineTag = findViewById(R.id.focus_list_tab2);
        medicineTag.setOnClickListener(myListener);
        recyclerView = findViewById(R.id.focus_list_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mEmptyView = findViewById(R.id.empty_iv);




    }

    public void init() {
        if (UserBook.Code == 1) {
            fDao.searchDoctor(UserBook.NowDoctor.getId(), 1);
            fDao.searchMedicine(UserBook.NowDoctor.getId(), 1);
        } else {
            fDao.searchDoctor(UserBook.NowUser.getId(), 2);
            fDao.searchMedicine(UserBook.NowUser.getId(), 2);
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

        baseList.clear();
        baseList.addAll(baseList2);
        //doctorTag.setTextColor();
        adapter.notifyDataSetChanged();
    }

    private void initTag1() {

        baseList.clear();
        baseList.addAll(baseList1);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(EventMessage msg) {
        if (msg.getCode().equals("focusDao_searchDoctor")) {
            Type focusList = new TypeToken<ArrayList<ToFocus>>(){}.getType();
            doctorList = gson.fromJson(msg.getJson(),focusList);
            //Log.e("doctorList",""+doctorList.get(0).toString());
            setBaseList1();
        } else if (msg.getCode().equals("focusDao_searchMedicine")) {
            medicineList = gson.fromJson(msg.getJson(), new TypeToken<List<ToFocus>>() {
            }.getType());
            setBaseList2();
        }

        adapter = new FocusListAdapter2(baseList);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(mEmptyView);
        initTag1();
    }

    private void setBaseList1() {

        baseList1  = doctorList;
    }

    private void setBaseList2() {

        baseList2 = medicineList;
    }
}
