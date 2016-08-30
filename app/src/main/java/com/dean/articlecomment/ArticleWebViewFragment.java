package com.dean.articlecomment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleWebViewFragment extends Fragment {

    public static final String EMPTY_HTML_CONTENT = "about:blank";

    public ProgressWebView mWebView;

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
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWebView = (ProgressWebView) root.findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new ArticleWebViewClient());

        mArticleAction.loadingArticle();
    }

    /**
     * 初始化数据
     */
    public void loadingArticle(String url) {
        if (mWebView != null) {
            mWebView.loadUrl(url);
        }
    }

    public class ArticleWebViewClient extends WebViewClient {

        boolean isError;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!isVisible()) {
                super.onPageStarted(view, url, favicon);
                return;
            }
            isError = false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!isVisible()) {
                super.onPageFinished(view, url);
                return;
            }

            if (!isError) {
                mArticleAction.onArticleLoadingSuccess();
            } else {
                mArticleAction.onArticleLoadingFailed();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            isError = true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }
    }

    @Override
    public void onDestroy() {
        mWebView.loadUrl(EMPTY_HTML_CONTENT);
        mWebView.clearFormData();
        mWebView.clearHistory();
        mWebView.clearMatches();
        mWebView = null;
        super.onDestroy();
    }

    public void setArticleAction(ArticleAction articleAction) {
        this.mArticleAction = articleAction;
    }
}
