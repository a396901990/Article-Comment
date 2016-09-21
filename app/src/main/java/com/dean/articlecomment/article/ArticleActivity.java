package com.dean.articlecomment.article;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dean.articlecomment.Comment;
import com.dean.articlecomment.CommentAdapter;
import com.dean.articlecomment.R;
import com.dean.articlecomment.XAppBarLayout;
import com.dean.articlecomment.XNestedScrollView;
import com.dean.articlecomment.base.BaseActivity;
import com.dean.articlecomment.util.ActivityUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class ArticleActivity extends BaseActivity implements XAppBarLayout.XAppBarListener, XNestedScrollView.XNestedScrollViewListener{
    Animation mHideAnimation,mShowAnimation;

    @BindView(R.id.bottom_content)
    View bottomContent;

    @BindView(R.id.scrollView)
    XNestedScrollView nestedScrollView;

    @BindView(R.id.app_bar)
    XAppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CommentAdapter mAdapter;
    private ArrayList<Comment> listData;
    private int times = 0;

    private ArticlePresenter mPresenter;

    private ArticleDetailFragment mArticleFragment;

    private ArticleCommentFragment2 mCommentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scrolling;
    }

    @Override
    protected void init() {
        // webView fragment
        mArticleFragment = ArticleDetailFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mArticleFragment, R.id.article_content_view);

        // comment fragment
        mCommentFragment = ArticleCommentFragment2.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCommentFragment, R.id.comment_content_view);

        appBarLayout.setXAppBarListener(this);

        nestedScrollView.setXNestedScrollViewListener(this);

        initBottomContent();

        listData = new ArrayList<Comment>();
        for(int i = 0; i < 15 ;i++){
            Comment newComment = new Comment();
            newComment.userName = "游客" + i;
            newComment.commentContent = "他很懒什么都没说。";
            listData.add(newComment);
        }
        mAdapter = new CommentAdapter(listData);

        mPresenter = new ArticlePresenter(mArticleFragment, mCommentFragment, null);
    }

    private void initBottomContent() {
        mHideAnimation = AnimationUtils.loadAnimation(this, R.anim.hide_to_bottom);
        mShowAnimation = AnimationUtils.loadAnimation(this, R.anim.show_from_bottom);
    }

    @Override
    public void onFingerUp() {
        if (!isHidden()) {
            playHideAnimation();
            bottomContent.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFingerDown() {
        if (isHidden()) {
            playShowAnimation();
            bottomContent.setVisibility(View.VISIBLE);
        }
    }

    public boolean isHidden() {
        return bottomContent.getVisibility() == View.INVISIBLE;
    }

    void playShowAnimation() {
        if (bottomContent.isShown()) {

        }
        mHideAnimation.cancel();
        bottomContent.startAnimation(mShowAnimation);
    }

    void playHideAnimation() {
        mShowAnimation.cancel();
        bottomContent.startAnimation(mHideAnimation);
    }

    @Override
    public void onScrollToPageEnd() {
        if (mCommentFragment.getRecyclerView() != null) {
            mCommentFragment.getRecyclerView().onLoadMore();
        }
    }
}
