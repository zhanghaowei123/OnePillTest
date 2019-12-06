package com.onepilltest.personal;

import android.content.SharedPreferences;

import com.onepilltest.entity.UserPatient;

import java.util.ArrayList;
import java.util.List;


public class UserBook {
    public static UserPatient NowUser = null;
    private static List<UserPatient> UserList = new ArrayList<>();



    public static void addUser(UserPatient userPatient){
        int size = UserList.size();
        boolean f = false;
        for(int i=0;i<size;i++){
            if(UserList.get(i).getUserId()!=userPatient.getUserId())
                f = true;
        }
        if (f)
            UserList.add(userPatient);
        NowUser = userPatient;
    }


}
