package com.onepilltest.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.EventMessage;
import com.onepilltest.entity.Inquiry;
import com.onepilltest.entity.UserPatient;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionAdapter extends BaseAdapter {
    //原始数据
    private List<Inquiry> inquiries = null;
    //上下文环境
    private Context context = null;
    //item对应的布局文件
    private int item_layout_id;

    public QuestionAdapter(List<Inquiry> inquiries, Context context, int item_layout_id) {
        this.inquiries = inquiries;
        this.context = context;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        if (null != inquiries) {
            return inquiries.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != inquiries) {
            return inquiries.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(item_layout_id, null);
            viewHolder = new ViewHolder();
            viewHolder.ivQuestionHeadImg = convertView.findViewById(R.id.iv_question_headimg);
            viewHolder.tvQuestionName = convertView.findViewById(R.id.tv_question_name);
            viewHolder.tvQuestionTitle = convertView.findViewById(R.id.tv_question_title);
            viewHolder.tvQuestionFlag = convertView.findViewById(R.id.tv_question_flag);
            viewHolder.tvQuestionContent = convertView.findViewById(R.id.tv_question_content);
            viewHolder.ivQuestionImg = convertView.findViewById(R.id.iv_question_img);
            viewHolder.tvQuestionTime = convertView.findViewById(R.id.tv_question_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Inquiry inquiry = inquiries.get(position);
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(context)
                .load(Connect.BASE_URL + inquiry.getHeadImg())
                .apply(requestOptions)
                .into(viewHolder.ivQuestionHeadImg);

        viewHolder.ivQuestionHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, inquiry.getPhone());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                context.startActivity(intent);
            }
        });

        viewHolder.tvQuestionName.setText(inquiry.getName());
        viewHolder.tvQuestionTitle.setText(inquiry.getTitle());
        if (inquiry.getFlag() == 1) {
            viewHolder.tvQuestionFlag.setText("去过医院就诊");
        } else if (inquiry.getFlag() == 0) {
            viewHolder.tvQuestionFlag.setText("未去过医院就诊");
        }
        viewHolder.tvQuestionContent.setText(inquiry.getContent());
        //加载病人病情照片
        Glide.with(context)
                .load(Connect.BASE_URL + inquiry.getImg())
                .into(viewHolder.ivQuestionImg);
        viewHolder.tvQuestionTime.setText(inquiry.getTime());
        return convertView;
    }

    private class ViewHolder {
        public ImageView ivQuestionHeadImg;
        public TextView tvQuestionName;
        public TextView tvQuestionTitle;
        public TextView tvQuestionFlag;
        public TextView tvQuestionContent;
        public ImageView ivQuestionImg;
        public TextView tvQuestionTime;
    }

}
