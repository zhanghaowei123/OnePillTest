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
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;
import com.onepilltest.entity.Comment;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentAdapter extends BaseAdapter {
    //原始数据
    private List<Comment> comments = null;
    //上下文环境
    private Context context = null;
    //item对应的布局文件
    private int item_layout_id;
    private ViewHolder viewHolder;
    private boolean goodFlag = false;
    private boolean badFlag = false;

    private OkHttpClient okHttpClient;

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
        okHttpClient = new OkHttpClient();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(item_layout_id, null);
            viewHolder = new ViewHolder();
            viewHolder.ivCommenterImg = (ImageView) convertView.findViewById(R.id.iv_commenterImg);
            viewHolder.tvCommenterName = (TextView) convertView.findViewById(R.id.tv_commenter_name);
            viewHolder.tvCommenterCotent = (TextView) convertView.findViewById(R.id.tv_commenter_content);
            viewHolder.ivGood = (ImageView) convertView.findViewById(R.id.iv_good);
            viewHolder.ivBad = (ImageView)convertView.findViewById(R.id.iv_bad);
            viewHolder.tvGoodNum = convertView.findViewById(R.id.tv_goodnum);
            viewHolder.tvBadNum = convertView.findViewById(R.id.tv_badnum);
//            viewHolder.tvResponse = convertView.findViewById(R.id.tv_response);//点击item执行回复
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Comment comment = comments.get(position);
        Log.e("commentNew",comment.toString());
        //加载评论头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(context)
                .load(Connect.BASE_URL + comment.getHeadImg())
                .apply(requestOptions)
                .into(viewHolder.ivCommenterImg);
        Log.e("HeadImgURL",Connect.BASE_URL + comment.getHeadImg());
        viewHolder.tvCommenterName.setText(comment.getName());
        viewHolder.tvCommenterCotent.setText(comment.getCcomment());
        Log.e("goodNum",comment.getGoodNum()+"");
        viewHolder.tvGoodNum.setText(comment.getGoodNum()+"");
        viewHolder.tvBadNum.setText(comment.getBadNum()+"");
        //点赞功能
        viewHolder.ivGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment.getIsGood() == 0){
                    comment.setIsGood(1);
                    comment.setGoodNum(comment.getGoodNum()+1);
                    viewHolder.tvGoodNum.setText(comment.getGoodNum()+"");//数据库没动
                    viewHolder.ivGood.setImageResource(R.drawable.up_yes);
                    viewHolder.ivBad.setImageResource(R.drawable.down_no);
                    updateComment(comment);
                    notifyDataSetChanged();
                }else {
                    comment.setIsGood(0);
                    comment.setGoodNum(comment.getGoodNum()-1);
                    viewHolder.tvGoodNum.setText(comment.getGoodNum()+"");
                    viewHolder.ivGood.setImageResource(R.drawable.up_no);
                    updateComment(comment);
                    notifyDataSetChanged();
                }
            }
        });
        //差评功能
        viewHolder.ivBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.getIsBad() == 0){
                    comment.setIsBad(1);
                    comment.setGoodNum(comment.getBadNum()+1);
                    viewHolder.tvBadNum.setText(comment.getBadNum()+"");
                    viewHolder.ivBad.setImageResource(R.drawable.down_yes);
                    viewHolder.ivGood.setImageResource(R.drawable.up_no);
                }else {
                    comment.setIsBad(0);
                    comment.setBadNum(comment.getBadNum()-1);
                    viewHolder.tvBadNum.setText(comment.getBadNum()+"");
                    viewHolder.ivBad.setImageResource(R.drawable.down_no);
                }
            }
        });
        return convertView;
    }

    public void updateComment(Comment comment){
        String jsonStr = null;
        jsonStr = new Gson().toJson(comment);
        Log.e("test", jsonStr.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                jsonStr);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Connect.BASE_URL + "comment/update")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("updateComment_false", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("updateComment_true",response.body().string());
            }
        });
    }

    private class ViewHolder {
        public ImageView ivCommenterImg;
        public TextView tvCommenterCotent;
        public TextView tvCommenterName;
        public ImageView ivGood;
        public ImageView ivBad;
        public TextView tvGoodNum;
        public TextView tvBadNum;
//        public TextView tvResponse;
    }
}
