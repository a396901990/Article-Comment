package com.dean.articlecomment.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.dean.articlecomment.util.SystemUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by dun on 16/6/24.
 */
public class XProgressWebView extends WebView {

    ProgressBar mProgressView;
    int progressColor = 0xFFFF4081;

    public XProgressWebView(Context context) {
        this(context, null);
    }

    public XProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        mProgressView.setBackgroundColor(progressColor);
    }

    private void initViews() {

        WebSettings settings = getSettings();
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

        mProgressView = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        mProgressView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10));
        mProgressView.setBackgroundColor(progressColor);
        addView(mProgressView);

        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressView.setVisibility(GONE);
                } else {
                    if (mProgressView.getVisibility() == GONE)
                        mProgressView.setVisibility(VISIBLE);
                    mProgressView.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressView.getLayoutParams();
        lp.width = l;
        lp.height = t;
        mProgressView.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

}

