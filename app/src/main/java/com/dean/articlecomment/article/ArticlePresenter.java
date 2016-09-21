package com.dean.articlecomment.article;

/**
 * Created by DeanGuo on 9/20/16.
 */

public class ArticlePresenter implements ArticleContract.Presenter {

    protected final ArticleContract.ArticleView articleView;

    protected final ArticleContract.CommentView commentView;

    protected final ArticleContract.BottomView bottomView;

    public ArticlePresenter(ArticleContract.ArticleView articleView, ArticleContract.CommentView commentView, ArticleContract.BottomView bottomView) {
        this.articleView = articleView;
        this.commentView = commentView;
        this.bottomView = bottomView;

        // view中注入presenter
        articleView.setPresenter(this);
        commentView.setPresenter(this);
//        bottomView.setPresenter(this);
    }

    @Override
    public void getArticleUrl() {
        articleView.showArticle("https://www.baidu.com");
    }

    @Override
    public void start() {

    }
}
