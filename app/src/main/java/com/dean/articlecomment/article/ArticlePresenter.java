package com.dean.articlecomment.article;

import com.dean.articlecomment.base.RxPresenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by DeanGuo on 9/20/16.
 */

public class ArticlePresenter extends RxPresenter implements ArticleContract.Presenter {

    protected final ArticleContract.ArticleView articleView;

    protected final ArticleContract.CommentView commentView;

    protected final ArticleContract.View bottomView;

    @Inject
    public ArticlePresenter(ArticleContract.ArticleView articleView, ArticleContract.CommentView commentView, ArticleContract.View bottomView) {
        this.articleView = articleView;
        this.commentView = commentView;
        this.bottomView = bottomView;
    }

    @Inject
    void setupListeners() {
        // view中注入presenter
        articleView.setPresenter(this);
        commentView.setPresenter(this);
        bottomView.setPresenter(this);
    }

    @Override
    public void onLoadingArticle() {
        if (articleView.isActive())
            articleView.showArticle("https://www.baidu.com");
    }

    @Override
    public void onLoadingComment() {

        Subscription rxSubscription = Observable
                .create(new Observable.OnSubscribe<ArrayList<ArticleComment>>() {
                    @Override
                    public void call(Subscriber<? super ArrayList<ArticleComment>> subscriber) {
                        ArrayList<ArticleComment> comments = new ArrayList<ArticleComment>();
                        for (int i = 0; i < 15; i++) {
                            ArticleComment newComment = new ArticleComment();
                            newComment.userName = "游客" + i;
                            newComment.commentContent = "他很懒什么都没说。";
                            comments.add(newComment);
                        }

                        subscriber.onNext(comments);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<ArticleComment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<ArticleComment> articleComments) {
                        if (commentView.isActive())
                            commentView.showComments(articleComments);
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void onLoadingMoreComment() {

        Subscription rxSubscription = Observable
                .create(new Observable.OnSubscribe<ArrayList<ArticleComment>>() {
                    @Override
                    public void call(Subscriber<? super ArrayList<ArticleComment>> subscriber) {
                        ArrayList<ArticleComment> comments = new ArrayList<ArticleComment>();
                        for (int i = 0; i < 15; i++) {
                            ArticleComment newComment = new ArticleComment();
                            newComment.userName = "游客" + i;
                            newComment.commentContent = "他很懒什么都没说。";
                            comments.add(newComment);
                        }
                        subscriber.onNext(comments);
                    }
                })
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<ArticleComment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<ArticleComment> articleComments) {
                        if (commentView.isActive())
                            commentView.showLoadMoreComments(articleComments);
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void onLoadingArticleSuccess() {
        onLoadingComment();
    }

    @Override
    public void onLoadingArticleFailed() {

    }
}
