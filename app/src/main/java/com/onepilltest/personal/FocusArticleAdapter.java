package com.onepilltest.personal;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;

import java.util.ArrayList;
import java.util.List;

public class FocusArticleAdapter extends RecyclerView.Adapter<FocusArticleAdapter.ViewHolder> {
    private List<Article> articleList = new ArrayList<>();
    private Context mContext;

    public FocusArticleAdapter(List<Article> articleList){
        this.articleList = articleList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img ;
        TextView title;
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.focus_article_item_img);
            title = itemView.findViewById(R.id.focus_article_item_title);
            text = itemView.findViewById(R.id.focus_article_item_text);
        }
    }

    //item点击接口
    //私有属性
    private OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v,String json);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.focus_article_item,viewGroup,false);
        mContext = viewGroup.getContext();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FocusArticleAdapter.ViewHolder viewHolder, int i) {
        Article article = articleList.get(i);
//        RequestOptions requestOptions = new RequestOptions().circleCrop();
        String tag = "tag1.png";
        switch (article.getTag()){
            case "日常医学":
                tag = "tag1.png";
                break;
            case "急救知识":
                tag = "tag1.png";
                break;
            case "疾病科普":
                tag = "tag1.png";
                break;
            case "个人饮食":
                tag = "tag1.png";
                break;
        }
        article.setHeadImg(Connect.BASE_URL+"/image/"+tag);
        Glide.with(mContext)
                .load(article.getHeadImg())
//                .apply(requestOptions)
                .into(viewHolder.img);
        viewHolder.title.setText(article.getTitle());
        if (article.getContent().length()>60){
            String str = article.getContent().substring(0,59)+"...";
            viewHolder.text.setText(str);
        }else
        viewHolder.text.setText(article.getContent());

        //实现点击效果
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v,new Gson().toJson(article));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
