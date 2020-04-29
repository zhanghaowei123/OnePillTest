package com.onepilltest.index;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.onepilltest.entity.Comment;

import java.util.List;

public class NewCommentAdapter extends BaseAdapter {

    //原始数据
    List<Comment> commentList = null;
    //上下文
    private Context context = null;
    //item对应的布局文件


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
