package com.onepilltest.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;

public class ZxingText extends Fragment {

    private ImageView imageView = null;
    private TextView textView = null;
    View view = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getActivity().getLayoutInflater().inflate(R.layout.zxing_text, null);
        find();
        init();


    }

    public void find(){
        imageView = view.findViewById(R.id.zxing_textz_loading);
        textView = view.findViewById(R.id.zxing_text);
    }

    public void init(){
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this).load(R.drawable.loading).apply(requestOptions).into(imageView);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.zxing_text,container, false);
    }
}
