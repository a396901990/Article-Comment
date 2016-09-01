package com.dean.articlecomment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jianghejie on 15/11/26.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    public ArrayList<Comment> datas = null;
    public CommentAdapter(ArrayList<Comment> datas) {
        this.datas = datas;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.userName.setText(datas.get(position).userName);
        viewHolder.commentContent.setText(datas.get(position).commentContent);
        viewHolder.deleteComment.setVisibility(datas.get(position).isPublisher ? View.VISIBLE : View.GONE);
        viewHolder.deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, datas.size());
            }
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView commentContent;
        public TextView deleteComment;

        public ViewHolder(View view){
            super(view);
            userName = (TextView) view.findViewById(R.id.user_name);
            commentContent = (TextView) view.findViewById(R.id.comment_content);
            deleteComment = (TextView) view.findViewById(R.id.del_comment);
        }
    }
}
