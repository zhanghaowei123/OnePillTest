package com.onepilltest.personal;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.MyCart;
import com.onepilltest.entity.Order;
import com.onepilltest.entity.Orders;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class UserBook {
    public static final int Doctor = 1;
    public static final int Patient = 2;
    public static int Code = 0;//身份:医生是1，用户是2
    public static UserPatient NowUser;//当前用户
    public static UserDoctor NowDoctor;//当前医生
    public static List<UserPatient> UserList = new ArrayList<>();//用户列表
    public static List<UserDoctor> DoctorList = new ArrayList<>();//医生列表
    public static int money = 1000;
    public static List<MyCart> myCartList = new ArrayList<>();//购物车列表
    public static List<Orders> ordersList = new ArrayList<>();//订单列表


    public static Object getNowUser() {
        if (Code == 1)
            return NowDoctor;
        else if (Code == 2) {
            return NowUser;
        } else
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

    public static void addUser(UserPatient userPatient) {
        int size = UserList.size();
        Log.e("UserList", "size" + size);
        Code = 2;
        boolean f = true;
        if (size == 0) {
            UserList.add(userPatient);
        }
        for (int i = 0; i < size; i++) {
            Log.e("UserList", "比较" + UserList.get(i).getId() + "\n" + userPatient.getId());
            if (UserList.get(i).getId() == userPatient.getId())
                f = false;
        }
        if (f && size != 0)
            UserList.add(userPatient);
        NowUser = userPatient;
    }

    public static void addDoctor(UserDoctor userDoctor) {

        int size = DoctorList.size();
        Code = 1;
        boolean f = true;
        if (size == 0) {
            DoctorList.add(userDoctor);
        }
        for (int i = 0; i < size; i++) {
            if (DoctorList.get(i).getId() == userDoctor.getId())
                f = false;
        }
        if (f && size !=0)
            DoctorList.add(userDoctor);
        NowDoctor = userDoctor;
    }

    public static List<UserPatient> getList() {
        return UserList;
    }

    public static List<UserDoctor> getDoctorList() {
        return DoctorList;
    }

    public static String print() {
        String str = "\nUserList一共记录了" + UserList.size() + "个用户信息";
        for (int i = 0; i < UserList.size(); i++) {
            str += "\n" + UserList.get(i).toString();

        }
        str += "\nDoctorList一共记录了" + DoctorList.size() + "个医生信息";
        for (int i = 0; i < DoctorList.size(); i++) {
            str += "\n" + DoctorList.get(i).toString();

        }
        return str;
    }

    public static String getDegree() {
        if (Code == 1) {

            return "医生";
        } else {
            return "用户";
        }
    }

}
