package com.dean.articlecomment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.articlecomment.xrecycleview.ProgressStyle;
import com.dean.articlecomment.xrecycleview.XRecyclerView;


/**
 * Created by DeanGuo on 8/18/16.
 */
public class ArticleCommentFragment extends Fragment {

    private XRecyclerView mRecyclerView;

    private ArticleAction mArticleAction;

    private View root;

    public ArticleCommentFragment(ArticleAction articleAction) {
        this.mArticleAction = articleAction;
    }

    public static ArticleCommentFragment newInstance(ArticleAction articleAction) {
        return new ArticleCommentFragment(articleAction);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.article_comment_fragment_view, null);
        mRecyclerView = (XRecyclerView)root.findViewById(R.id.comment_view);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                mArticleAction.onLoadMoreComment();
            }
        });
    }

    public XRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

}
