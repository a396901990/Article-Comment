package com.dean.articlecomment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dean.articlecomment.util.ActivityUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleActivity extends AppCompatActivity implements ArticleAction, XAppBarLayout.XAppBarListener, XNestedScrollView.XNestedScrollViewListener{
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

    private ArticleWebViewFragment mWebViewFragment;

    private ArticleCommentFragment mCommentFragment;

    ArticleActivity articleActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleActivity = this;
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(articleActivity);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // webView fragment
        mWebViewFragment = ArticleWebViewFragment.newInstance(this);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mWebViewFragment, R.id.article_content_view);

        // comment fragment
        mCommentFragment = ArticleCommentFragment.newInstance(this);
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
        mAdapter = new CommentAdapter();
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

    @Override
    public void loadingArticle() {
        if (mWebViewFragment != null) {
            mWebViewFragment.loadingArticle(getArticleUrl());
        }
    }

    @Override
    public void onArticleLoadingSuccess() {
        mCommentFragment.getRecyclerView().setAdapter(mAdapter);
    }

    @Override
    public void onArticleLoadingFailed() {

    }

    @Override
    public String getArticleUrl() {
        return "https://www.baidu.com";
    }

    @Override
    public void commentAction() {

    }

    @Override
    public void likeAction() {

    }

    @Override
    public void shareAction() {

    }

    @Override
    public void deleteComment() {

    }

    @OnClick(R.id.comment_btn)
    @Override
    public void commentArticle() {
        Comment newComment = new Comment();
        newComment.userName = "大苞米";
        newComment.commentContent = "最新评论";
        newComment.isPublisher = true;
        listData.add(0, newComment);
        mAdapter.notifyItemInserted(0);
    }

    @Override
    public void onCommentSuccess() {

    }

    @Override
    public void onCommentFailed() {

    }

    @Override
    public void onLoadMoreComment() {
        if(times < 2){
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    for(int i = 0; i < 8 ;i++) {
                        Comment newComment = new Comment();
                        newComment.userName = "游客" + listData.size();
                        newComment.commentContent = "他很懒什么都没说。";
                        listData.add(newComment);
                    }
                    mCommentFragment.getRecyclerView().loadMoreComplete();
                    mAdapter.notifyDataSetChanged();
                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    for(int i = 0; i < 9 ;i++) {
                        Comment newComment = new Comment();
                        newComment.userName = "游客" + listData.size();
                        newComment.commentContent = "他很懒什么都没说。";
                        listData.add(newComment);
                    }
                    mCommentFragment.getRecyclerView().setNoMore(true);
                    mAdapter.notifyDataSetChanged();
                }
            }, 1000);
        }
        times ++;
    }

    @OnClick(R.id.go_comment_btn)
    @Override
    public void gotoComment() {
        final View commentView = findViewById(R.id.comment_content_view);
        nestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.smoothScrollTo(0, commentView.getTop());
            }
        });
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
