package com.dean.articlecomment.article;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dean.articlecomment.R;
import com.dean.articlecomment.XAppBarLayout;
import com.dean.articlecomment.XNestedScrollView;
import com.dean.articlecomment.base.BaseActivity;
import com.dean.articlecomment.util.ActivityUtils;

import butterknife.BindView;

public class ArticleActivity extends BaseActivity<ArticleContract.Presenter> implements ArticleContract.View, XAppBarLayout.XAppBarListener, XNestedScrollView.XNestedScrollViewListener{
    Animation mHideAnimation,mShowAnimation;

    @BindView(R.id.bottom_content)
    View bottomContent;

    @BindView(R.id.scrollView)
    XNestedScrollView nestedScrollView;

    @BindView(R.id.app_bar)
    XAppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ArticleDetailFragment mArticleFragment;

    private ArticleCommentFragment mCommentFragment;

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
        mCommentFragment = ArticleCommentFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCommentFragment, R.id.comment_content_view);

        appBarLayout.setXAppBarListener(this);

        nestedScrollView.setXNestedScrollViewListener(this);

        initBottomContent();

        new ArticlePresenter(mArticleFragment, mCommentFragment, this);
    }

    private void initBottomContent() {
        mHideAnimation = AnimationUtils.loadAnimation(this, R.anim.hide_to_bottom);
        mShowAnimation = AnimationUtils.loadAnimation(this, R.anim.show_from_bottom);
    }

    @Override
    public void onFingerUp() {
        showBottomView();
    }

    @Override
    public void onFingerDown() {
        hideBottomView();
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
        mCommentFragment.onScrollToPageEnd();
    }

    @Override
    public void showBottomView() {
        if (!isHidden()) {
            playHideAnimation();
            bottomContent.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void hideBottomView() {
        if (isHidden()) {
            playShowAnimation();
            bottomContent.setVisibility(View.VISIBLE);
        }
    }
}
