package com.dean.articlecomment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity implements ArticleAction, XAppBarLayout.XAppBarListener, XNestedScrollView.XNestedScrollViewListener{
    Animation mHideAnimation,mShowAnimation;
    View bottomContent, commentBtn, goCommentBtn;

    private MyAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;
    XNestedScrollView nestedScrollView;
    private ArticleWebViewFragment mWebViewFragment;

    private ArticleCommentFragment mCommentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // webView fragment
        mWebViewFragment = ArticleWebViewFragment.newInstance(this);
        getFragmentManager().beginTransaction().add(R.id.article_content_view, mWebViewFragment).commit();

        // comment fragment
        mCommentFragment = ArticleCommentFragment.newInstance(this);
        getFragmentManager().beginTransaction().add(R.id.comment_content_view, mCommentFragment).commit();

        XAppBarLayout appBarLayout = (XAppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setXAppBarListener(this);

        nestedScrollView = (XNestedScrollView) findViewById(R.id.scrollView);
        nestedScrollView.setXNestedScrollViewListener(this);
        
        initBottomContent();

        listData = new ArrayList<String>();
        for(int i = 0; i < 15 ;i++){
            listData.add("item" + i);
        }
        mAdapter = new MyAdapter(listData);
    }

    private void initBottomContent() {
        bottomContent = findViewById(R.id.bottom_content);
        mHideAnimation = AnimationUtils.loadAnimation(this, R.anim.hide_to_bottom);
        mShowAnimation = AnimationUtils.loadAnimation(this, R.anim.show_from_bottom);

        commentBtn = findViewById(R.id.comment_btn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentArticle();
            }
        });

        goCommentBtn = findViewById(R.id.go_comment_btn);
        goCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoComment();
            }
        });
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
    public void commentArticle() {
        listData.add(0, "new comment");
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
                    for(int i = 0; i < 15 ;i++){
                        listData.add("item" + (1 + listData.size() ) );
                    }
                    mCommentFragment.getRecyclerView().loadMoreComplete();
                    mAdapter.notifyDataSetChanged();
                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    for(int i = 0; i < 9 ;i++){
                        listData.add("item" + (1 + listData.size() ) );
                    }
                    mCommentFragment.getRecyclerView().setNoMore(true);
                    mAdapter.notifyDataSetChanged();
                }
            }, 1000);
        }
        times ++;
    }

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
