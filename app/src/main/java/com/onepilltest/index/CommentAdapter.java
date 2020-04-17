package com.onepilltest.index;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.Comment;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    //原始数据
    private List<Comment> comments = null;
    //上下文环境
    private Context context = null;
    //item对应的布局文件
    private int item_layout_id;

    public CommentAdapter(List<Comment> comments, Context context, int item_layout_id) {
        this.comments = comments;
        this.context = context;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        if (null != comments) {
            return comments.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != comments) {
            return comments.get(position);
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
            viewHolder.ivCommenterImg = convertView.findViewById(R.id.iv_commenterImg);
            viewHolder.tvCommenterName = convertView.findViewById(R.id.tv_commenter_name);
            viewHolder.tvCommenterCotent = convertView.findViewById(R.id.tv_commenter_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Comment comment = comments.get(position);
        //加载评论头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(context)
                .load(Connect.BASE_URL + comment.getHeadImg())
                .apply(requestOptions)
                .into(viewHolder.ivCommenterImg);
        Log.e("HeadImgURL",Connect.BASE_URL + comment.getHeadImg());
        viewHolder.tvCommenterName.setText(comment.getName());
        viewHolder.tvCommenterCotent.setText(comment.getCcomment());
        return convertView;
    }

    private class ViewHolder {
        public ImageView ivCommenterImg;
        public TextView tvCommenterCotent;
        public TextView tvCommenterName;
    }
}
