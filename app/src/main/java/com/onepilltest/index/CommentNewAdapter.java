package com.onepilltest.index;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.Comment;
import com.onepilltest.entity.Dao.GoodDao;
import com.onepilltest.personal.UserBook;

import java.util.List;

public class CommentNewAdapter extends RecyclerView.Adapter<CommentNewAdapter.ViewHolder> {

    private GoodDao goodDao = new GoodDao();
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img ;
        TextView name;
        TextView time;
        TextView text;
        ImageView good;
        TextView goodnum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_commenterImg);
            name = itemView.findViewById(R.id.tv_commenter_name);
            time = itemView.findViewById(R.id.tv_commenter_time);
            text = itemView.findViewById(R.id.tv_commenter_content);
            good = itemView.findViewById(R.id.iv_good);
            goodnum = itemView.findViewById(R.id.tv_goodnum);
        }
    }
    //原始数据
    private List<Comment> comments = null;

    public CommentNewAdapter(List<Comment> comments){
        this.comments = comments;
        if (comments!=null){
            for(Comment c:comments){
                Log.e("focus数据源",""+c.toString());
            }
        }

    }

    private Context mContext;

    @NonNull
    @Override
    public CommentNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment,viewGroup,false);
        mContext = viewGroup.getContext();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentNewAdapter.ViewHolder viewHolder, int i) {
        Comment comment = comments.get(i);

        //加载头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(mContext)
                .load(Connect.BASE_URL + comment.getHeadImg())
                .apply(requestOptions)
                .into(viewHolder.img);

        //name
        viewHolder.name.setText(comment.getName());
        //time:还没有加
        //text
        viewHolder.text.setText(comment.getCcomment());
        //goodNum
        viewHolder.goodnum.setText(comment.getGoodNum()+"");
        //good
        if (comment.isGood())viewHolder.good.setImageResource(R.drawable.up_yes);
        else viewHolder.good.setImageResource(R.drawable.up_no);
        viewHolder.good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserBook.Code == 1){
                    Toast.makeText(mContext,"当前账号无法点赞",Toast.LENGTH_SHORT).show();
                }
                else if (comment.isGood()){

                        GoodDao.addgoodList(comment.getId(),false);
                        viewHolder.good.setImageResource(R.drawable.up_no);
                        comments.get(i).setGood(false);
                        int num = comments.get(i).getGoodNum()-1;
                        comments.get(i).setGoodNum(num);
                        viewHolder.goodnum.setText(""+num);

                }else if (!comment.isGood()){
                    GoodDao.addgoodList(comment.getId(),true);
                    viewHolder.good.setImageResource(R.drawable.up_yes);
                    comments.get(i).setGood(true);
                    int num = comments.get(i).getGoodNum()+1;
                    comments.get(i).setGoodNum(num);
                    viewHolder.goodnum.setText(""+num);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
