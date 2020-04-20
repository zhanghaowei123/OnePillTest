package com.onepilltest.index;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.entity.ZxingMessage;
import com.onepilltest.util.SharedPreferencesUtil;

public class ZxingUser extends Fragment {
    ImageView img = null;
    TextView name = null;
    TextView phone = null;
    ImageView focus = null;
    boolean isFocus = false;
    View view = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getActivity().getLayoutInflater().inflate(R.layout.zxing_user, null);
        find();
        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        focus = getActivity().findViewById(R.id.zxing_user_focus);
        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zxingUser:","点击关注");
                if (!isFocus){
                    focus.setImageResource(R.drawable.isfocus);
                    isFocus = true;
                }else{
                    focus.setImageResource(R.drawable.notfocus);
                    isFocus = false;
                }
            }
        });

    }

    public void find(){
        img = view.findViewById(R.id.zxing_user_img);
        name = view.findViewById(R.id.zxing_user_name);
        phone = view.findViewById(R.id.zxing_user_phone);
        focus = view.findViewById(R.id.zxing_user_focus);
    }

    public void init(){
        String imgs = null;
        String names = null;
        String phones = null;
       SharedPreferences sharedPreferences =  SharedPreferencesUtil.getShared(getContext(),"zxing");
       String json = sharedPreferences.getString("result","no");
        ZxingMessage zxingMessage = new Gson().fromJson(json,ZxingMessage.class);
        int code = zxingMessage.getCode();
        if (code == 1){
            UserDoctor userDoctor = new Gson().fromJson(zxingMessage.getJson(),UserDoctor.class);
            imgs = userDoctor.getHeadImg();
            names = userDoctor.getName();
            phones = userDoctor.getPhone();
        }else {
            UserPatient userPatient = new Gson().fromJson(zxingMessage.getJson(),UserPatient.class);
            imgs = userPatient.getHeadImg();
            names = userPatient.getNickName();
            phones = userPatient.getPhone();
        }

        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this).load(imgs).apply(requestOptions).into(img);
        name.setText(names);
        phone.setText(phones);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zxing_user,container, false);
    }


}
