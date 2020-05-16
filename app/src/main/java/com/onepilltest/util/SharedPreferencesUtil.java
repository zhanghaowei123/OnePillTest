package com.onepilltest.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.personal.UserBook;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {

    private static String FileName = "NowUser";


    //获取SharedPreferences
    public static SharedPreferences getShared(Context context, String file) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, MODE_PRIVATE);
        return sharedPreferences;
    }

    //获取Edit
    public static SharedPreferences.Editor getSharedEdit(Context context, String file) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }

    //保存User
    public static boolean saveUser(Context context, UserPatient userPatient) {
        Gson gson = new Gson();
        String json = gson.toJson(userPatient);
        SharedPreferences sharedPreferences = context.getSharedPreferences(FileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("user", json);

        //保存到userList
        List<UserPatient> userPatientList = userList(context);
        if (userPatientList != null) {
            Log.e("userList数量：", userPatientList.size() + "");
            boolean f = false;
            for (UserPatient user : userPatientList) {
                if (user.getPhone().equals(userPatient.getPhone()))
                    f = true;
            }
            if (!f)
                userPatientList.add(userPatient);
        } else {
            userPatientList = new ArrayList<>();
            userPatientList.add(userPatient);
        }
        editor.putString("userList", gson.toJson(userPatientList));


        //更改类别
        editor.putInt("code", 2);
        editor.commit();

        //initUserBook
        initUserBook(context);


        return true;
    }

    //保存Doctor
    public static boolean saveDoctor(Context context, UserDoctor userDoctor) {
        Gson gson = new Gson();
        String json = gson.toJson(userDoctor);
        SharedPreferences sharedPreferences = context.getSharedPreferences(FileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("doctor", json);

        //保存到doctorList
        List<UserDoctor> userDoctorList = doctorList(context);
        if (userDoctorList != null) {
            boolean f = false;
            for (UserDoctor user : userDoctorList) {
                if (user.getPhone().equals(userDoctor.getPhone()))
                    f = true;
            }
            if (!f)
                userDoctorList.add(userDoctor);
        } else {
            userDoctorList = new ArrayList<>();
            userDoctorList.add(userDoctor);
        }
        editor.putString("userList", gson.toJson(userDoctorList));

        //更改类别
        editor.putInt("code", 1);
        editor.commit();

        //init
        initUserBook(context);


        return true;
    }

    //获取userList
    public static List<UserPatient> userList(Context context) {
        Gson gson = new Gson();
        String str = getShared(context, FileName).getString("userList", "");
        if (!str.equals("")) {
            Type userListType = new TypeToken<ArrayList<UserPatient>>() {
            }.getType();
            return gson.fromJson(str, userListType);
        }
        return new ArrayList<>();
    }

    //获取doctorList
    public static List<UserDoctor> doctorList(Context context) {
        Gson gson = new Gson();
        String str = getShared(context, FileName).getString("doctorList", "");
        if (!str.equals("")) {
            Type userListType = new TypeToken<ArrayList<UserDoctor>>() {
            }.getType();
            return gson.fromJson(str, userListType);
        }
        return new ArrayList<>();
    }

    //判断User是否存在
    public static boolean userExist(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FileName, MODE_PRIVATE);

        if (!sharedPreferences.getString("user", "").isEmpty())
            return true;
        else
            return false;

    }

    //判断Doctor是否存在
    public static boolean doctorExist(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FileName, MODE_PRIVATE);

        if (!sharedPreferences.getString("doctor", "").isEmpty())
            return true;
        else
            return false;

    }

    //删除User
    public static boolean delUser(Context context) {
        SharedPreferences.Editor editor = getSharedEdit(context, FileName).remove("user");
        editor.commit();
        delUserBook();
        return true;
    }

    //删除Doctor
    public static boolean delDoctor(Context context) {
        SharedPreferences.Editor editor = getSharedEdit(context, FileName).remove("doctor");
        editor.commit();
        delUserBook();
        return true;
    }

    //初始化UserBook
    public static void initUserBook(Context context) {
        SharedPreferences sharedPreferences = getShared(context, FileName);
        SharedPreferences.Editor editor = getSharedEdit(context, FileName);
        int code = sharedPreferences.getInt("code", 0);
        Gson gson = new Gson();

        if (code == 1) {
            UserDoctor userDoctor = gson.fromJson(sharedPreferences.getString("doctor", ""), UserDoctor.class);
            UserBook.Code = code;
            UserBook.NowUser = null;
            UserBook.NowDoctor = userDoctor;
        } else if (code == 2) {
            UserPatient userPatient = gson.fromJson(sharedPreferences.getString("user", ""), UserPatient.class);
            UserBook.Code = code;
            UserBook.NowUser = userPatient;
            Log.e("初始化user：", sharedPreferences.getString("user", "暂无数据"));
            Log.e("初始化UserBook：", UserBook.NowUser.toString());
            UserBook.NowDoctor = null;
        }
        UserBook.UserList = userList(context);
        UserBook.DoctorList = doctorList(context);
    }

    //重置UserBook
    public static void delUserBook() {
        UserBook.NowDoctor = null;
        UserBook.NowUser = null;
        UserBook.Code = 0;
    }
}
