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
    public void addComment() {
        Subscription rxSubscription = Observable
                .just("说点什么好呢~")
                .map(s -> {
                    ArticleComment articleComment = new ArticleComment();
                    articleComment.userName = "大苞米";
                    articleComment.commentContent = s;
                    articleComment.isPublisher = true;
                    return articleComment;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArticleComment>() {
                    @Override
                    public void onCompleted() {
                        bottomView.goToComment();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ArticleComment articleComment) {
                        commentView.addComment(articleComment);
                    }
                });

        addSubscribe(rxSubscription);
    }

    @Override
    public void showBottomView() {
        bottomView.showBottomView();
    }

    @Override
    public void hideBottomView() {
        bottomView.hideBottomView();
    }

    @Override
    public void onLoadingArticle() {
        if (articleView.isActive())
            articleView.showArticle("https://github.com/a396901990");
    }

    @Override
    public void onLoadingComment() {

        Subscription rxSubscription = Observable
                .create(new Observable.OnSubscribe<ArrayList<ArticleComment>>() {
                    @Override
                    public void call(Subscriber<? super ArrayList<ArticleComment>> subscriber) {
                        ArrayList<ArticleComment> comments = new ArrayList<ArticleComment>();
                        for (int i = 0; i < 10; i++) {
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
                        // 加载失败操作 todo
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
                        for (int i = 0; i < 5; i++) {
                            ArticleComment newComment = new ArticleComment();
                            newComment.userName = "游客" + i;
                            newComment.commentContent = "他很懒什么都没说。";
                            comments.add(newComment);
                        }
                        subscriber.onNext(comments);
                    }
                })
                .delay(2, TimeUnit.SECONDS)
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
