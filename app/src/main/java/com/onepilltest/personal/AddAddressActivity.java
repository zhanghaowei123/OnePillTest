package com.onepilltest.personal;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Address;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.util.StatusBarUtil;
import com.onepilltest.welcome.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddAddressActivity extends BaseActivity {

    MyListener myListener = new MyListener();
    Button save = null;
    Button back = null;
    EditText et_name = null;
    EditText et_phoneNumber = null;
    EditText et_address = null;
    EditText et_more = null;
    EditText et_postalCode = null;
    private OkHttpClient okHttpClient = null;
    Address addAddress = null;
    Gson gson = null;

    public static String NowUserName = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xffffffff );
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.add_address);
        myListener = new MyListener();
        find();
        gson = new Gson();
        okHttpClient = new OkHttpClient();

        initBar(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.add_address;
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }


    /*//获取当前账号的信息
    public UserPatient getNowUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("NowUser", MODE_PRIVATE);
        String json = sharedPreferences.getString("NowUser","");
        nowUser = gson.fromJson(json,UserPatient.class);
        //NowUserName = nowUser.getNickName();
        return nowUser;
    }*/

    private void find() {
        save = findViewById(R.id.add_address_save);
        save.setOnClickListener(myListener);
        back = findViewById(R.id.add_address_back);
        back.setOnClickListener(myListener);
        et_name = findViewById(R.id.add_address_name);
        et_phoneNumber = findViewById(R.id.add_address_phoneNumber);
        et_address = findViewById(R.id.add_address_address);
        et_more = findViewById(R.id.add_address_more);
        et_postalCode = findViewById(R.id.add_address_postalCode);

    }


    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_address_back:
                    finish();
                case R.id.add_address_save:
                    save();
                    break;
            }
        }
    }



    //添加地址
    private void save() {


        int UserId =UserBook.NowUser.getId();
        String name = et_name.getText().toString();
        String phoneNumber = et_phoneNumber.getText().toString();
        String address = et_address.getText().toString();
        String more = et_more.getText().toString();
        String postalCode = et_postalCode.getText().toString();
        addAddress = new Address(UserId,name,phoneNumber,address,more,postalCode);
        Log.e("添加地址save",""+UserId+"\n"+name+"\n"+phoneNumber+"\n"+address+"\n"+more+"\n"+postalCode);
        AddressDao dao = new AddressDao();
        dao.save(addAddress);
        finish();

        /*Request request = new Request.Builder().url(Connect.BASE_URL+"AddAddressServlet?name="+name+"&phoneNumber="+phoneNumber
        +"&address="+address+"&more="+more+"&postalCode="+postalCode+"&UserId="+UserId+"&Code=").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("返回结果","发送成功");
                Log.e("返回","onResponse: " + response.body().string());
                String re = response.body().string();
                if(re.equals("yes")){//添加成功
                    Log.e("结果","成功");
                }else if(re.equals("no")){//添加失败
                    Log.e("结果","失败");
                }
            }
        });*/


    }

}
