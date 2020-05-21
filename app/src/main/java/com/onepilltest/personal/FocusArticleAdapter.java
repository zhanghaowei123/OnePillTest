package com.onepilltest.personal;

import android.content.Context;
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
        Glide.with(mContext)
                .load(Connect.BASE_URL+article.getHeadImg())
//                .apply(requestOptions)
                .into(viewHolder.img);
        viewHolder.title.setText(article.getTitle());
        if (article.getContent().length()>60){
            String str = article.getContent().substring(0,59)+"...";
            viewHolder.text.setText(str);
        }else
        viewHolder.text.setText(article.getContent());

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
