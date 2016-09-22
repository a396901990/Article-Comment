package com.dean.articlecomment.article;

import android.os.Handler;

import com.dean.articlecomment.Comment;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/20/16.
 */

public class ArticlePresenter implements ArticleContract.Presenter {

    protected final ArticleContract.ArticleView articleView;

    protected final ArticleContract.CommentView commentView;

    protected final ArticleContract.View bottomView;

    public ArticlePresenter(ArticleContract.ArticleView articleView, ArticleContract.CommentView commentView, ArticleContract.View bottomView) {
        this.articleView = articleView;
        this.commentView = commentView;
        this.bottomView = bottomView;

        // view中注入presenter
        articleView.setPresenter(this);
        commentView.setPresenter(this);
        bottomView.setPresenter(this);
    }

    @Override
    public void onLoadingArticle() {
        articleView.showArticle("https://www.baidu.com");
    }

    @Override
    public void onLoadingComment() {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        for(int i = 0; i < 15 ;i++){
            Comment newComment = new Comment();
            newComment.userName = "游客" + i;
            newComment.commentContent = "他很懒什么都没说。";
            comments.add(newComment);
        }

        commentView.showComments(comments);
    }

    @Override
    public void onLoadingMoreComment() {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        for(int i = 0; i < 8 ;i++) {
            Comment newComment = new Comment();
            newComment.userName = "游客";
            newComment.commentContent = "他很懒什么都没说。";
            comments.add(newComment);
        }

        new Handler().postDelayed(new Runnable(){
            public void run() {
                commentView.showLoadMoreComments(comments);
            }
        }, 3000);
    }

    @Override
    public void onLoadingArticleSuccess() {
        onLoadingComment();
    }

    @Override
    public void onLoadingArticleFailed() {

    }

    @Override
    public void start() {

    }
}
