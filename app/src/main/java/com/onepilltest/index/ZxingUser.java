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
import com.onepilltest.entity.Dao.focusDao;
import com.onepilltest.entity.UserDoctor;
import com.onepilltest.entity.UserPatient;
import com.onepilltest.entity.ZxingMessage;
import com.onepilltest.personal.UserBook;
import com.onepilltest.util.SharedPreferencesUtil;

public class ZxingUser extends Fragment {
    private ImageView img = null;
    private TextView name = null;
    private TextView phone = null;
    private ImageView focus = null;
    private boolean isFocus = false;
    private View view = null;
    private focusDao dao  = new focusDao();
    private int code = 0;
    private UserPatient userPatient = null;
    private UserDoctor userDoctor = null;


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

                if (isFocus){
                    if (UserBook.Code ==1){
                        dao.del(UserBook.NowDoctor.getId(),UserBook.Code,code,(code == 1? userDoctor.getId():userPatient.getId()));
                    }else{
                        dao.del(UserBook.NowUser.getId(),2,code,(code == 1? userDoctor.getId():userPatient.getId()));
                    }

                    focus.setImageResource(R.drawable.notfocus);
                    isFocus = false;
                }else{
                    if (UserBook.Code ==1){
                        dao.add(UserBook.NowDoctor.getId(),1,code,(code == 1? userDoctor.getId():userPatient.getId()));
                    }else{
                        dao.add(UserBook.NowUser.getId(),2,code,(code == 1? userDoctor.getId():userPatient.getId()));
                    }
                    focus.setImageResource(R.drawable.isfocus);
                    isFocus = true;
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
        code = zxingMessage.getCode();
        if (code == 1){
            userDoctor = new Gson().fromJson(zxingMessage.getJson(),UserDoctor.class);
            imgs = userDoctor.getHeadImg();
            names = userDoctor.getName();
            phones = userDoctor.getPhone();
        }else {
            userPatient = new Gson().fromJson(zxingMessage.getJson(),UserPatient.class);
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
