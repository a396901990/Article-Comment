package com.dean.articlecomment;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by DeanGuo on 8/31/16.
 */

public class XNestedScrollView extends NestedScrollView implements NestedScrollView.OnScrollChangeListener {

    public interface XNestedScrollViewListener {
        void onScrollToPageEnd();
    }

    private XNestedScrollViewListener xNestedScrollViewListener;

    public XNestedScrollView(Context context) {
        super(context);
    }

    public XNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View lastView = v.getChildAt(v.getChildCount() - 1);
        int lastViewHeight = lastView.getBottom();
        lastViewHeight -= (v.getHeight() + v.getScrollY());
        if (lastViewHeight == 0) {
            if (xNestedScrollViewListener != null) {
                xNestedScrollViewListener.onScrollToPageEnd();
            }
        }
    }

    public void setXNestedScrollViewListener(XNestedScrollViewListener xNestedScrollViewListener) {
        this.xNestedScrollViewListener = xNestedScrollViewListener;
    }
}
