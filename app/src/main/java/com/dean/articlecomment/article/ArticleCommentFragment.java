package com.dean.articlecomment.article;

import android.support.v7.widget.LinearLayoutManager;

import com.dean.articlecomment.Comment;
import com.dean.articlecomment.CommentAdapter;
import com.dean.articlecomment.R;
import com.dean.articlecomment.VerticalSpaceItemDecoration;
import com.dean.articlecomment.base.BaseFragment;
import com.dean.articlecomment.xrecycleview.ProgressStyle;
import com.dean.articlecomment.xrecycleview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by DeanGuo on 8/18/16.
 */
public class ArticleCommentFragment extends BaseFragment<ArticleContract.Presenter> implements ArticleContract.CommentView {

    @BindView(R.id.comment_view)
    XRecyclerView mRecyclerView;

    private CommentAdapter mAdapter;
    private ArrayList<Comment> listData = new ArrayList<>();

    private int times = 0;

    public static ArticleCommentFragment newInstance() {
        return new ArticleCommentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.article_comment_fragment_view;
    }

    @Override
    protected void init() {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(3));
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
            }
        });

        mAdapter = new CommentAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public XRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void showComments(ArrayList<Comment> comments) {
        mAdapter.setData(comments);
    }

    @Override
    public void showLoadMoreComments(ArrayList<Comment> comments) {
        mAdapter.addData(comments);
    }
}
