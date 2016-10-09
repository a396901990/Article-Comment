package com.dean.articlecomment.article;

import android.support.v7.widget.LinearLayoutManager;

import com.dean.articlecomment.R;
import com.dean.articlecomment.ui.VerticalSpaceItemDecoration;
import com.dean.articlecomment.base.BaseFragment;
import com.dean.articlecomment.ui.XNestedScrollView;
import com.dean.articlecomment.ui.xrecycleview.ProgressStyle;
import com.dean.articlecomment.ui.xrecycleview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by DeanGuo on 8/18/16.
 */
public class ArticleCommentFragment extends BaseFragment<ArticleContract.Presenter> implements ArticleContract.CommentView {

    @BindView(R.id.comment_view)
    XRecyclerView mRecyclerView;

    private ArticleCommentAdapter mAdapter;

    public static ArticleCommentFragment newInstance() {
        return new ArticleCommentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.article_comment_fragment_view;
    }

    @Override
    protected void initEventAndData() {

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
                mPresenter.onLoadingMoreComment();
            }
        });

        mAdapter = new ArticleCommentAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public XRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void showComments(ArrayList<ArticleComment> comments) {
        mAdapter.setData(comments);
    }

    @Override
    public void showLoadMoreComments(ArrayList<ArticleComment> comments) {
        mAdapter.addData(comments);
        mRecyclerView.setNoMore(false);
    }

    @Override
    public void onScrollToPageEnd() {
        mRecyclerView.onLoadMore();
    }
}
