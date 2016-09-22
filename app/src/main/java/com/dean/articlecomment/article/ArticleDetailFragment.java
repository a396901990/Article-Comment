package com.dean.articlecomment.article;

import com.dean.articlecomment.R;
import com.dean.articlecomment.base.BaseFragment;
import com.dean.articlecomment.util.SystemUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

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
    protected void init() {
        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (SystemUtil.isNetworkConnected()) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mPresenter.onLoadingArticleSuccess();
                } else {
                }
            }
        });
        mPresenter.onLoadingArticle();
    }

    @Override
    public void showArticle(String url) {
        webView.loadUrl(url);
    }
}
