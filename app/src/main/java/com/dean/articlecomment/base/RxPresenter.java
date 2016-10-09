package com.dean.articlecomment.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by DeanGuo on 2016/8/2.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */
public class RxPresenter implements BasePresenter {

    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }
}
