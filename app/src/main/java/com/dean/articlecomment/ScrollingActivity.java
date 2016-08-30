package com.dean.articlecomment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {
    Context context;
    Animation mHideAnimation,mShowAnimation;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_scrolling);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mHideAnimation =  AnimationUtils.loadAnimation(context, R.anim.hide_to_bottom);
        mShowAnimation =  AnimationUtils.loadAnimation(context, R.anim.show_from_bottom);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    if (isHidden()) {
                        playShowAnimation();
                        floatingActionButton.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (!isHidden()) {
                        playHideAnimation();
                        floatingActionButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
//
//        NestedScrollView textView = (NestedScrollView) findViewById(R.id.scrollView);
//        textView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
////                toolbar.setVisibility(View.VISIBLE);
////                floatingActionButton.setVisibility(View.VISIBLE);
//                Log.e("onc", "");
//                return false;
//            }
//        });
    }

    public boolean isHidden() {
        return floatingActionButton.getVisibility() == View.INVISIBLE;
    }

    void playShowAnimation() {
        if (floatingActionButton.isShown()) {

        }
        mHideAnimation.cancel();
        floatingActionButton.startAnimation(mShowAnimation);
    }

    void playHideAnimation() {
        mShowAnimation.cancel();
        floatingActionButton.startAnimation(mHideAnimation);
    }
}
