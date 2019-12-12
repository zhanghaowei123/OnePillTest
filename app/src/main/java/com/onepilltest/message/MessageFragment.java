package com.onepilltest.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onepilltest.R;

public class MessageFragment extends Fragment {
    MyListener myListener;
    private TextView tvAsk;
    private TextView tvInform;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        myListener = new MyListener();
        findViews(view);

        return view;
    }

    private void findViews(View view) {
        tvAsk = view.findViewById(R.id.tv_ask);
        tvAsk.setOnClickListener(myListener);
        tvInform = view.findViewById(R.id.tv_inform);
        tvInform.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_ask:

                    break;
                case R.id.tv_inform:

                    break;
            }
        }
    }
}
