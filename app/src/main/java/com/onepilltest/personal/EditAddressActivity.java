package com.onepilltest.personal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.onepilltest.BaseActivity;
import com.onepilltest.R;
import com.onepilltest.entity.Address;
import com.onepilltest.util.StatusBarUtil;

/**
 * 个人_设置_用户地址_编辑地址界面
 */
public class EditAddressActivity extends BaseActivity {

    EditText et_name = null;
    EditText et_phone = null;
    EditText et_address = null;
    EditText et_more = null;
    EditText et_postalCode = null;

    Button delete = null;
    Button back = null;
    Button save = null;
    MyListener myListener = null;
    Address editaddress = null;
    Gson gson = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xffffffff );
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        setContentView(R.layout.edit_address);
        myListener = new MyListener();
        find();
        gson = new Gson();
        Bundle info = getIntent().getExtras();
        //获取Bundle的信息
        String json=info.getString("info");
        Log.e("G送字符串",""+json);
        editaddress = gson.fromJson(json,Address.class);
        Log.e("火车Id",""+editaddress.toString()+"???:"+editaddress.getId());
        init(editaddress);


        initBar(this);
    }

    private void initBar(Activity activity) {

        //设置状态栏paddingTop
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏颜色0xff56ced4
//        StatusBarUtil.setStatusBarColor(activity,0xff56ced4);
        StatusBarUtil.setStatusBarColor(activity,0xffffffff);
        //设置状态栏神色浅色切换
        StatusBarUtil.setStatusBarDarkTheme(activity,true);

    }


    @Override
    public int intiLayout() {
        return R.layout.edit_address;
    }

    private void init(Address address){
        et_name.setText(address.getName());
        et_phone.setText(address.getPhoneNumber());
        et_address.setText(address.getAddress());
        et_more.setText(address.getMore());
        et_postalCode.setText(address.getPostalCode());
    }

    private void find() {

        et_address = findViewById(R.id.edit_address_address);
        et_name = findViewById(R.id.edit_address_name);
        et_more = findViewById(R.id.edit_address_more);
        et_phone = findViewById(R.id.edit_address_phoneNumber);
        et_postalCode = findViewById(R.id.edit_address_postalCode);

        delete = findViewById(R.id.edit_address_delet);
        delete.setOnClickListener(myListener);
        back = findViewById(R.id.edit_address_back);
        back.setOnClickListener(myListener);
        save = findViewById(R.id.edit_address_save);
        save.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_address_back:
                    Intent back_intent = new Intent(EditAddressActivity.this, AddressActivity.class);
                    startActivity(back_intent);
                    break;
                case R.id.edit_address_save:
                    //存入数据库
                    save();
                    break;
                case R.id.edit_address_delet:
                    delete(editaddress);
                    break;
            }
        }
    }

    private void delete(Address deleteaddress) {

        AddressDao dao = new AddressDao();
        dao.delet(deleteaddress.getId());
        finish();
    }

    private void save() {
        int Id = editaddress.getId();
        int UserId =UserBook.NowUser.getId();
        String name = et_name.getText().toString();
        String phoneNumber = et_phone.getText().toString();
        String address = et_address.getText().toString();
        String more = et_more.getText().toString();
        String postalCode = et_postalCode.getText().toString();
        editaddress = new Address(Id,UserId,name,phoneNumber,address,more,postalCode);

        AddressDao dao = new AddressDao();
        dao.update(editaddress);
        finish();
    }


}
