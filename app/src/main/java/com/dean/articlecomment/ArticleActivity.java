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
    View bottomContent;

    private MyAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;

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

        XNestedScrollView scrollView = (XNestedScrollView) findViewById(R.id.scrollView);
        scrollView.setXNestedScrollViewListener(this);
        
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
        return "http://7xs3hm.com1.z0.glb.clouddn.com/0425%E7%8E%A9%E8%BD%AC%E6%88%B7%E5%A4%96%E6%8E%A2%E5%AD%90%E7%9A%84%E6%AD%A3%E7%A1%AE%E5%A7%BF%E5%8A%BF.html";
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
