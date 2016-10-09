package com.dean.articlecomment.article;

import android.graphics.Bitmap;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;

import com.dean.articlecomment.R;
import com.dean.articlecomment.base.BaseFragment;
import com.dean.articlecomment.util.SystemUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * Created by DeanGuo on 8/18/16.
 */
public class ArticleDetailFragment extends BaseFragment<ArticleContract.Presenter> implements ArticleContract.ArticleView {

    @BindView(R.id.web_view)
    WebView webView;

    public static ArticleDetailFragment newInstance() {
        return new ArticleDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.article_webview_fragment_view;
    }

    @Override
    protected void initEventAndData() {

        webView.setWebViewClient(new webViewClient());
        mPresenter.onLoadingArticle();
    }

    public class webViewClient extends WebViewClient  {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            return super.shouldOverrideUrlLoading(webView, s);
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            mPresenter.onLoadingArticleSuccess();
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }
    }

    @Override
    public void showArticle(String url) {
        webView.loadUrl(url);
    }
}
