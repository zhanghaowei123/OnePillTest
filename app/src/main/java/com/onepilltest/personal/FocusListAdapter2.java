package com.onepilltest.personal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.onepilltest.R;
import com.onepilltest.URL.Connect;
import com.onepilltest.entity.ToFocus;
import com.onepilltest.entity.focus;
import com.onepilltest.index.DoctorDetailsActivity;
import com.onepilltest.index.FoundDoctorActivity;

import java.util.List;

public class FocusListAdapter2 extends RecyclerView.Adapter<FocusListAdapter2.ViewHolder> {

    private List<ToFocus> focusList;

    public FocusListAdapter2(List<ToFocus> baseList) {
        focusList = baseList;
        for(ToFocus f:focusList){
            Log.e("focus数据源",""+f.toString());
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img ;
        TextView name;
        TextView more;
        TextView add;
        TextView del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.focus_list_item_img);
            name = itemView.findViewById(R.id.focus_list_item_name);
            more = itemView.findViewById(R.id.focus_list_item_tag);
            add = itemView.findViewById(R.id.focus_list_adds);
            del = itemView.findViewById(R.id.focus_list_dels);
        }
    }
    private Context mContext;
//    //点击和长按
//    public interface OnItemClickListener {
//        void onClick(int position);
//    }
//    private OnItemClickListener listener;
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    public interface OnItemLongClickListener {
//        void onClick(int position);
//    }
//    private OnItemLongClickListener longClickListener;
//    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
//        this.longClickListener = longClickListener;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.focus_liste_item,viewGroup,false);
        mContext = viewGroup.getContext();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ToFocus focus = focusList.get(i);
        //加载头像
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(mContext)
                .load(Connect.BASE_URL + focus.getImg())
                .apply(requestOptions)
                .into(viewHolder.img);
        viewHolder.name.setText(focus.getName());
        viewHolder.more.setText(focus.getMore());
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("focusAdapter","查看第"+i+"条数据");
                Toast.makeText(mContext,"查看第"+i+"条数据,Type:"+focus.toString(),Toast.LENGTH_SHORT).show();
                if (focus.getType() == 1){
                    Intent intent = new Intent(mContext, DoctorDetailsActivity.class);
                    intent.putExtra("id",focus.getTypeId());
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, ProductActivity.class);
                    intent.putExtra("id",focus.getTypeId());
                    mContext.startActivity(intent);
                }

            }
        });
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("focusAdapter","删除第"+i+"条数据");
                Toast.makeText(mContext,"删除第"+i+"条数据",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return focusList.size();
    }

}
