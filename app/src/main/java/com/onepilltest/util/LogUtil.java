package com.onepilltest.util;

import android.app.Activity;
import android.util.Log;

public class LogUtil {

    public static void show (Activity activity,String string){
        Log.e(activity.getLocalClassName(),string);
    }
}
