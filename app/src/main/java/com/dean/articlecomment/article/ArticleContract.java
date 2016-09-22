package com.dean.articlecomment.article;

import com.dean.articlecomment.Comment;
import com.dean.articlecomment.base.BasePresenter;
import com.dean.articlecomment.base.BaseView;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/20/16.
 */

public interface ArticleContract {

    interface Presenter extends BasePresenter {
        void onLoadingArticle();
        void onLoadingComment();
        void onLoadingMoreComment();
        void onLoadingArticleSuccess();
        void onLoadingArticleFailed();
    }

    interface CommentView extends BaseView<Presenter> {
        void showComments(ArrayList<Comment> comments);
        void showLoadMoreComments(ArrayList<Comment> comments);
    }

    interface ArticleView extends BaseView<Presenter> {
        void showArticle(String url);
    }

    interface View extends BaseView<Presenter> {
        void showBottomView();
        void hideBottomView();
    }
}