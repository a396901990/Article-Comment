##简介

这个项目主要有两个功能，一个加载网页／文章，另一个用来显示评论。并应用了*MVP*模式，*Dagger2*、*RxJava*、*ButterKnife*等开源框架。效果图如下：

![demo](/screenshot/screenshot.gif) 
##结构
首先来看一下布局文件：

```
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:background="#ffffff"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context="com.dean.articlecomment.article.ArticleActivity">

    <com.dean.articlecomment.ui.XAppBarLayout
        android:id="@+id/app_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:fitsSystemWindows="true"
        android:theme="@style/AppTheme.AppBarOverlay">

            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:fitsSystemWindows="true"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_scrollFlags="scroll|enterAlways"
                app:popupTheme="@style/AppTheme.PopupOverlay" />

    </com.dean.articlecomment.ui.XAppBarLayout>

    <include layout="@layout/content_scrolling" />

    <include layout="@layout/article_bottom_view" />

</android.support.design.widget.CoordinatorLayout>
```

###toolbar
在显示网页文章时是仿知乎的操作，向下滑动时隐藏toolbar和屏幕下方发表评论的视图，向上滚动时再显示。  

toolbar的显示隐藏是通过设置其*scrollFlags*属性实现的。
>*enterAlways*：向上滑时toolbar隐藏，向下滑动即展示。 
>
>*enterAlwaysCollapsed*：向上滑时toolbar隐藏，向下滑动直到NestedScrollView的底部时toolbar才展示。 
>
>*exitUntilCollapsed*：当你定义了一个minHeight，这个view将在滚动到达这个最小高度的时候消失。
>
>*snap*：突然折断的意思，效果同enterAlwaysCollapsed，区别为滚动时手指离开屏幕时
toolbar不会显示一半的状态，显示的部分大于一半时即全漏出来，小于一半时即隐藏掉。

###article_bottom_view
article_bottom_view是屏幕下方的评论条，它的隐藏显示与toolbar同步，使用方式是通过`AppBarLayout.OnOffsetChangedListener`的状态监听与动画实现的。

```
  @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= 0) {
            if (xAppBarListener != null) {
                xAppBarListener.onFingerDown();
            }
        } else {
            if (xAppBarListener != null) {
                 xAppBarListener.onFingerUp();
            }
        }
    }
```

###content_scrolling
content_scrolling布局如下：

```
<com.dean.articlecomment.ui.XNestedScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/scrollView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:showIn="@layout/activity_scrolling">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <!--详细-->
        <FrameLayout
            android:id="@+id/article_content_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
        </FrameLayout>

        <!--评论-->
        <FrameLayout
            android:id="@+id/comment_content_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
        </FrameLayout>
    </LinearLayout>
</com.dean.articlecomment.ui.XNestedScrollView>
```
NestedScrollView中嵌套两个视图article_content_view，comment_content_view。分别是用于显示文章Fragment视图和评论fragment视图。

###文章Fragment
文章Fragment中使用Webview来显示网页／文章。
 
Webview使用了腾讯的[X5WebView](http://x5.tencent.com)，并在外层封装一个加载用的进度条。
###评论fragment
文章Fragment中使用了RecycleView（根据[XRecyclerView](https://github.com/jianghejie/XRecyclerView)改造）来显示添加评论，并且可以进行滑动加载更多。

值得注意的是NestedScrollview中嵌套RecycleView的问题，解决方法是：

- 使用Android Support Library 23.2.0以上，设置`layoutManager.setAutoMeasureEnabled(true);`

- 将recyclerView的高度设置为wrap_content

- 设置`recyclerView.setNestedScrollingEnabled(false)`避免和NestedScrolling的滑动冲突。
> 由于禁用了recyclerView的滚动，所以在实现底部加载更多的时候需要监听外层的NestedScrollingView


##MVP
本Demo使用了MVP模式(关于MVP的文章网上很多，我这里就不过多介绍)，主要借鉴了下面3个开源项目。并作了一些改动。

- [googlesamples/android-architecture](https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/)

- [JessYanCoding/MVPArms](https://github.com/JessYanCoding/MVPArms)

- [codeestX/GeekNews](https://github.com/codeestX/GeekNews)（主要参考）

大多数MVP模式里都是View持有Presenter的引用。一个fragment对应一个页面，一个页面对应一个Presenter，因此如果一个功能中页面较多时会导致逻辑复杂以及代码文件的增加。

我这里的处理是反过来使Presenter持有View的引用，即一个Activity持有一个Presenter，每个Fragment是一个View，用一个Presenter持有所有的View引用。
>所有的逻辑和业务代码都放在Presenter中处理，Activity和Fragment只负责页面的显示。这样的好处是结构简单，逻辑比较清晰，方便在多个view中交互操作。缺点就是会导致Presenter中代码量过大。

代码如下：

```
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
}
```
Contract代码如下：

```
public interface ArticleContract {

    interface Presenter extends BasePresenter {
        void addComment();
        void showBottomView();
        void hideBottomView();
        void onLoadingArticle();
        void onLoadingComment();
        void onLoadingMoreComment();
        void onLoadingArticleSuccess();
        void onLoadingArticleFailed();
    }

    interface CommentView extends BaseView<Presenter> {
        void showComments(ArrayList<ArticleComment> comments);
        void showLoadMoreComments(ArrayList<ArticleComment> comments);
        void addComment(ArticleComment comment);
        void onScrollToPageEnd();
    }

    interface ArticleView extends BaseView<Presenter> {
        void showArticle(String url);
    }

    interface View extends BaseView<Presenter> {
        void showBottomView();
        void hideBottomView();
        void goToComment();
        void goToArticle();
    }
}
```


##Rxjava/RxAndroid

[ReactiveX/RxJava](https://github.com/ReactiveX/RxJava)

[ReactiveX/RxAndroid](https://github.com/ReactiveX/RxAndroid)

Rxjava也是最近才知道。。。使用后发现是真的很牛逼。。。

于是也简单的在这个Demo中应用了一下，加载更多评论的代码如下：

- 首先在IO线程中创建数据，这里延迟2秒模拟网络请求。
- 然后在UI线程中显示，由于懒没写Error的代码。。。

```
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
```
Rxjava简单使用很容易，但要达到能适应各种场景就不轻松了，我也在摸索中。下面列出我找到相关文章：

[给 Android 开发者的 RxJava 详解](http://gank.io/post/560e15be2dca930e00da1083)

[RxJava操作符大全](http://blog.csdn.net/maplejaw_/article/details/52396175)


[ReactiveX/RxJava文档中文版](https://mcxiaoke.gitbooks.io/rxdocs/content/)


##dagger
[google/dagger](https://github.com/google/dagger)

实话实说，这个依赖注入框架真心不太明白，感觉学习成本和使用成本都有点高，demo里也仅仅做了最简单的应用。

下面列出我觉得不错的文章：  
[依赖注入神器：Dagger2详解系列](https://dreamerhome.github.io/2016/07/07/dagger/)

##butterknife
[JakeWharton/butterknife](https://github.com/JakeWharton/butterknife/)

视图注入框架，很好用！网上例子很多，使用起来也方便就不介绍了。

##最后

还有一些小细节，比如添加／删除评论，双击toolbar回到文章头，点击评论按钮跳转到评论等等。写这个demo的主要目的是为了练习使用MVP以及各种开源框架，如果以后有时间会陆续加入下面列表中的开源框架。

- Realm
- Retrofit
- RxCache
- RxBinding
- RxBus

*demo地址*：  
[https://github.com/a396901990/Article-Comment](https://github.com/a396901990/Article-Comment)

