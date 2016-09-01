package com.dean.articlecomment;

/**
 * Created by DeanGuo on 8/21/16.
 */

public interface ArticleAction {

    void loadingArticle();
    void onArticleLoadingSuccess();
    void onArticleLoadingFailed();

    String getArticleUrl();

    void commentAction();
    void likeAction();
    void shareAction();

    void deleteComment();

    void commentArticle();
    void onCommentSuccess();
    void onCommentFailed();

    void onLoadMoreComment();

    void gotoComment();
}
