package com.onepilltest.index;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Article;

import java.util.ArrayList;
import java.util.List;

public class IndexAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Article> articles = new ArrayList<>();
    private int itemId;

    public IndexAdapter(Context context, List<Article> articles, int itemId) {
        this.context = context;
        this.articles = articles;
        this.itemId = itemId;
    }

    @NonNull
    /**自动完成重复利用
     * viewHolder 重复利用每个item中单独的视图控件  创建每个item视图对象的时候调用
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //加载Item的布局文件
        /**
         * inflate(布局，父布局（如果是adapterview类型传空）)
         * view ：每个item对应的视图对象
         */
        View view = LayoutInflater.from(context).inflate(itemId, viewGroup, false);
        return new MyItemViewHolder(view);
    }

    /**
     * @param viewHolder
     * @param i          所显示的条数
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        Article article = articles.get(i);
        //设置每一项所显示的内容
        MyItemViewHolder itemViewHolder = (MyItemViewHolder) viewHolder;
        itemViewHolder.writerName.setText(articles.get(i).getWriterName());
        String content = article.getContent();
        if (content.length()>150){
            content = content.substring(0,149)+"...";
        }
        itemViewHolder.articleContent.setText(content);
        itemViewHolder.title.setText(articles.get(i).getTitle());
        itemViewHolder.tag.setText(articles.get(i).getTag());
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(context)
                .load(Connect.BASE_URL + article.getHeadImg())
                .apply(requestOptions)
                .into(itemViewHolder.imgHeadImg);
        //设置每一项的点击事件监听器
        itemViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击第" + (i + 1) + "条数据", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context,ArticleActivity.class);
                String json = new Gson().toJson(article);
                intent1.putExtra("json",json);
                context.startActivity(intent1);

            }
        });
        itemViewHolder.imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                int num = i + 1;
                intent.putExtra("articleId", num + "");
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        if (articles != null)
            return articles.size();
        return 0;
    }

    //重复利用每个item中单独的视图控件
    private class MyItemViewHolder extends RecyclerView.ViewHolder {
        public TextView writerName;
        public TextView articleContent;
        public TextView tag;
        public TextView title;
        public LinearLayout root;//每一个Item的根视图
        public ImageView imgComment;
        public ImageView imgHeadImg;

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            writerName = itemView.findViewById(R.id.tv_writer_name);
            articleContent = itemView.findViewById(R.id.tv_article_content);
            tag = itemView.findViewById(R.id.tv_tag);
            title = itemView.findViewById(R.id.tv_article_title);
            root = itemView.findViewById(R.id.ll_article);
            imgComment = itemView.findViewById(R.id.iv_commentImg);
            imgHeadImg = itemView.findViewById(R.id.iv_article_headimg);
        }
    }
}
