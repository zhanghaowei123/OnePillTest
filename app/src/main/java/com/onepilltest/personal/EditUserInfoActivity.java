package com.onepilltest.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onepilltest.R;

public class EditUserInfoActivity extends AppCompatActivity {

    MyListener myListener = null;
    Button back = null;
    Button save = null;

    EditText et_nickName = null;
    EditText et_PID = null;
    EditText et_password = null;
    EditText et_phone = null;

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
