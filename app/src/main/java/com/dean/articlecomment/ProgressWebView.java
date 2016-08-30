package com.dean.articlecomment;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;

/**
 * Created by dun on 16/6/24.
 */
public class ProgressWebView extends WebView {

    ProgressView mProgressView;
    int progressColor = 0xFFFF4081;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        mProgressView.setColor(progressColor);
    }

    private void initViews() {
        mProgressView = new ProgressView(getContext());
        mProgressView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10));
        mProgressView.setColor(progressColor);
        addView(mProgressView);
        this.setWebChromeClient(new ProgressWebChromeClient());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) mProgressView.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressView.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void setWebChromeClient(WebChromeClient webChromeClient) {
        super.setWebChromeClient(new ProgressWebChromeClient());
    }

    public class ProgressWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView webView, int progress) {
            super.onProgressChanged(webView, progress);
            Log.d("progress: ", progress+"");
//            if (progress == 100) {
//                mProgressView.setVisibility(GONE);
//            } else {
//                if (mProgressView.getVisibility() == GONE)
//                    mProgressView.setVisibility(VISIBLE);

                mProgressView.setProgress(progress);
//            }
        }
    }

}

