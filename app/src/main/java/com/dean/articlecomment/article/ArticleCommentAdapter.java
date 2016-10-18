package com.dean.articlecomment.article;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.articlecomment.R;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/1/16.
 */
public class ArticleCommentAdapter extends RecyclerView.Adapter<ArticleCommentAdapter.ViewHolder> {
    public ArrayList<ArticleComment> datas = null;

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_comment_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.userName.setText(datas.get(position).userName);
        viewHolder.commentContent.setText(datas.get(position).commentContent);
        viewHolder.deleteComment.setVisibility(datas.get(position).isPublisher ? View.VISIBLE : View.GONE);
        viewHolder.deleteComment.setOnClickListener(view -> {
            datas.remove(position);
            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, datas.size());
            notifyItemChanged(position);
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
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

    public void setData(ArrayList<ArticleComment> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    public void addData(ArrayList<ArticleComment> datas) {
        for (ArticleComment comment : datas) {
            this.datas.add(comment);
        }
        notifyDataSetChanged();
    }

    public void addComment(ArticleComment comment) {
        datas.add(0,comment);
        this.notifyItemInserted(0);
        this.notifyItemRangeChanged(0, datas.size());
    }
}
