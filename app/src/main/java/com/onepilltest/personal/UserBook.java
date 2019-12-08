package com.onepilltest.personal;

import android.content.SharedPreferences;

import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;

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

    public UserPatient getNowUser() {
        return NowUser;
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
        Code = code;
        boolean f = false;
        for (int i = 0; i < size; i++) {
            if (UserList.get(i).getUserId() != userPatient.getUserId())
                f = true;
        }
        if (f)
            UserList.add(userPatient);
        NowUser = userPatient;
    }

    public static void addUser(UserDoctor userDoctor, int code) {
        int size = UserList.size();
        Code = code;
        boolean f = false;
        for (int i = 0; i < size; i++) {
            if (DoctorList.get(i).getDoctorId() != userDoctor.getDoctorId())
                f = true;
        }
        if (f)
            DoctorList.add(userDoctor);
        NowDoctor = userDoctor;
    }


}
