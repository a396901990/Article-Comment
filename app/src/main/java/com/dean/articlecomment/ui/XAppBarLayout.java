package com.dean.articlecomment.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

/**
 * Created by DeanGuo on 8/31/16.
 */

public class XAppBarLayout extends AppBarLayout implements AppBarLayout.OnOffsetChangedListener {

    public interface XAppBarListener {
        void onFingerUp();
        void onFingerDown();
    }

    private XAppBarListener xAppBarListener;

    public XAppBarLayout(Context context) {
        super(context);
    }

    public XAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addOnOffsetChangedListener(this);
    }

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

    public void setXAppBarListener(XAppBarListener xAppBarListener) {
        this.xAppBarListener = xAppBarListener;
    }
}
