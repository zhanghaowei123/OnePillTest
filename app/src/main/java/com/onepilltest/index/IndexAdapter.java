package com.onepilltest.index;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.onepilltest.R;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int i) {
        //设置每一项所显示的内容
        MyItemViewHolder itemViewHolder = (MyItemViewHolder) viewHolder;
        itemViewHolder.writerName.setText(articles.get(i).getWriterName());
        itemViewHolder.articleContent.setText(articles.get(i).getArticleCotent());
        itemViewHolder.commentCotent.setText(articles.get(i).getComment());
        itemViewHolder.tag.setText(articles.get(i).getTag());
        //设置每一项的点击事件监听器
        itemViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击第" + (i + 1) + "条数据", Toast.LENGTH_SHORT).show();
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
        public TextView commentCotent;
        public LinearLayout root;//每一个Item的根视图

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            writerName = itemView.findViewById(R.id.tv_writer_name);
            articleContent = itemView.findViewById(R.id.tv_article_content);
            tag = itemView.findViewById(R.id.tv_tag);
            commentCotent = itemView.findViewById(R.id.tv_comment_content);
            root = itemView.findViewById(R.id.ll_article);
        }
    }
}
