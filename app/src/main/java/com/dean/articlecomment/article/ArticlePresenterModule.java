package com.dean.articlecomment.article;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DeanGuo on 9/20/16.
 */

@Module
public class ArticlePresenterModule {

    ArticleContract.ArticleView articleView;

    ArticleContract.CommentView commentView;

    ArticleContract.View bottomView;

    public ArticlePresenterModule(ArticleContract.ArticleView articleView, ArticleContract.CommentView commentView, ArticleContract.View bottomView) {
        this.articleView = articleView;
        this.commentView = commentView;
        this.bottomView = bottomView;
    }

    @Provides
    ArticleContract.ArticleView provideArticleView() {
        return articleView;
    }

    @Provides
    ArticleContract.CommentView provideCommentView() {
        return commentView;
    }

    @Provides
    ArticleContract.View provideView() {
        return bottomView;
    }
}
