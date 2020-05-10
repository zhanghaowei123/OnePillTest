package com.onepilltest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mob.tools.utils.SQLiteHelper;

public class MyDoctorDBHelper extends SQLiteOpenHelper{

    public MyDoctorDBHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //初始化数据库操作，第一次打开数据库时回调
        String user_table = "CREATE TABLE DOCTOR("
                +"ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"PHONE TEXT NOT NULL,NAME CHAR(20) NOT NULL,IMG CHAR(50) NOT NULL)";
        db.execSQL(user_table);

        Log.e("OpenHelper", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("OpenHelper", "onUpgrade");
    }
}
