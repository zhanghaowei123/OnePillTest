package com.onepilltest.util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkhttpUtil {

    public static Call get(String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        return okHttpClient.newCall(request);
    }

    public static Call post(RequestBody requestBody,String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        return okHttpClient.newCall(request);
    }
}
