package com.onepilltest.personal;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class UserBook {
    public static final int Doctor = 1;
    public static final int Patient = 2;
    public static int Code = 0;//身份
    public static UserPatient NowUser;//当前用户
    public static UserDoctor NowDoctor;//当前医生
    private static List<UserPatient> UserList = new ArrayList<>();//用户列表
    private static List<UserDoctor> DoctorList = new ArrayList<>();//医生列表

 /*   public UserPatient getNowUser() {
        return NowUser;
    }*/
    public Object getNowUser(){
        if (Code == 1)
            return NowDoctor;
        else if (Code == 2){
            return NowUser;
        }else
            return null;
    }

    public UserDoctor getNowDocter() {
        return NowDoctor;
    }
    public void setNowUser(UserPatient nowUser) {
        NowUser = nowUser;
    }

    public UserDoctor getNowDoctor() {
        return NowDoctor;
    }

    public void setNowDoctor(UserDoctor nowDoctor) {
        NowDoctor = nowDoctor;
    }

    public static void addUser(UserPatient userPatient, int code) {
        int size = UserList.size();
        Log.e("UserList","size"+size);
        Code = code;
        boolean f = true;
        if(size ==0){
            UserList.add(userPatient);
        }
        for (int i = 0; i < size; i++) {
            Log.e("UserList","比较"+UserList.get(i).getUserId()+"\n"+userPatient.getUserId());
            if (UserList.get(i).getUserId() == userPatient.getUserId())
                f = false;
        }
        if (f && size!=0)
            UserList.add(userPatient);
        NowUser = userPatient;
    }

    public static void addDocter(UserDoctor userDoctor, int code) {
        int size = DoctorList.size();
        Code = code;
        boolean f = true;
        if(size ==0){
            DoctorList.add(userDoctor);
        }
        for (int i = 0; i < size; i++) {
            if (DoctorList.get(i).getDoctorId() == userDoctor.getDoctorId())
                f = false;
        }
        if (f)
            DoctorList.add(userDoctor);
        NowDoctor = userDoctor;
    }

    public static List<UserPatient> getList (){
        return UserList;
    }
    public static String print(){
        String str = "";
        for(int i = 0;i<UserList.size();i++){
            str += "\n"+UserList.get(i).getNickName();

        }
        str+="UserList一共记录了"+UserList.size()+"个用户信息";
        return str;
    }

    public static String getDegree(){
        if (Code == 1){
            return "医生";
        }else{
            return "用户";
        }
    }

}
