package com.dean.articlecomment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.articlecomment.util.SystemUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DeanGuo on 8/18/16.
 */
public class ArticleWebViewFragment extends Fragment {

    public static final String EMPTY_HTML_CONTENT = "about:blank";

    @BindView(R.id.web_view)
    WebView webView;

    private ArticleAction mArticleAction;

    private View root;

    public ArticleWebViewFragment(ArticleAction articleAction) {
        this.mArticleAction = articleAction;
    }

    public static ArticleWebViewFragment newInstance(ArticleAction articleAction) {
        return new ArticleWebViewFragment(articleAction);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.article_webview_fragment_view, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mArticleAction.loadingArticle();
    }

    public void initView() {
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
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mArticleAction.onArticleLoadingSuccess();
                } else {
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    public void loadingArticle(String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }
//
//    public class ArticleWebViewClient extends WebViewClient {
//
//        boolean isError;
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            if (!isVisible()) {
//                super.onPageStarted(view, url, favicon);
//                return;
//            }
//            isError = false;
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            if (!isVisible()) {
//                super.onPageFinished(view, url);
//                return;
//            }
//
//            if (!isError) {
//                mArticleAction.onArticleLoadingSuccess();
//            } else {
//                mArticleAction.onArticleLoadingFailed();
//            }
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//            isError = true;
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
//            webView.loadUrl(s);
//            return true;
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setArticleAction(ArticleAction articleAction) {
        this.mArticleAction = articleAction;
    }
}
