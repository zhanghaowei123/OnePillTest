package com.onepilltest.util;

import java.io.File;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtil {

    //图片上传
    public static Call ImageUpLoad(String filePath, String url){
        File file = new File(filePath);
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", filePath, image)
                .build();

        return OkhttpUtil.post(requestBody,url);
    }

}
