package com.onepilltest.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkhttpUtil {

    private static OkHttpClient ok;

    static int connectTimeout = 30;          //连接超时（单位：秒）
    static int readTimeout = 30;             //读超时（单位：秒）
    static int writeTimeout = 30;            //写超时（单位：秒）
    static boolean retryOnDisconnect = true; //失败重新连接
    static String httpLogTAG = "Zeus-Net";           //Http请求日志TAG

    /**
     * 日志拦截器
     */
    public static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(@NonNull String s) {
            Log.i("NetUtil", "🥝-log" + s);
        }
    });

    public static OkHttpClient getClient(){
        ok = new OkHttpClient().newBuilder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(retryOnDisconnect)
                .addInterceptor(loggingInterceptor)
                .build();

        return ok;
    }

    public static Call get(String url){
        OkHttpClient okHttpClient = ok;
        Request request = new Request.Builder().url(url).build();
        return okHttpClient.newCall(request);
    }

    public static Call post(RequestBody requestBody,String url){
        OkHttpClient okHttpClient = ok;
        Request request = new Request.Builder().url(url).post(requestBody).build();
        return okHttpClient.newCall(request);
    }
}
