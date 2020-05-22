package com.onepilltest.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by BAIPEI on 2017/11/21.
 */

public class HeadImageUtil extends ScrollView {
    private View mZoomView;
    private int mZoomViewWidth;
    private int mZoomViewHeight;

    private float firstPosition;//记录第一次按下的位置
    private boolean isScrolling;//是否正在缩放
    private float mScrollRate = 0.3f;//缩放系数，缩放系数越大，变化的越大
    private float mReplyRate = 0.5f;//回调系数，越大，回调越慢

    public HeadImageUtil(Context context) {
        super(context);
    }

    public HeadImageUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadImageUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmZoomView(View mZoomView) {
        this.mZoomView = mZoomView;
    }

    public void setmScrollRate(float mScrollRate) {
        this.mScrollRate = mScrollRate;
    }

    public void setmReplyRate(float mReplyRate) {
        this.mReplyRate = mReplyRate;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        if (getChildAt(0) != null) {
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                mZoomView = vg.getChildAt(0);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            mZoomViewWidth = mZoomView.getMeasuredWidth();
            mZoomViewHeight = mZoomView.getMeasuredHeight();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                //手指离开后恢复图片
                isScrolling = false;
                replyImage();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isScrolling) {
                    if (getScrollY() == 0) {
                        firstPosition = ev.getY();// 滚动到顶部时记录位置，否则正常返回
                    } else {
                        break;
                    }
                }
                int distance = (int) ((ev.getY() - firstPosition) * mScrollRate)*2; // 滚动距离乘以一个系数
                if (distance < 0) { // 当前位置比记录位置要小，正常返回
                    break;
                }
                // 处理放大
                isScrolling = true;
                setZoom(distance);
                return true; // 返回true表示已经完成触摸事件，不再处理
        }
        return true;
    }

    //回弹动画
    private void replyImage() {
        float distance = mZoomView.getMeasuredWidth() - mZoomViewWidth;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distance, 0f).setDuration((long) (distance * mReplyRate));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    public void setZoom(float zoom) {
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        lp.width = (int) (mZoomViewWidth + zoom);
        lp.height = (int) (mZoomViewHeight * ((mZoomViewWidth + zoom) / mZoomViewWidth));
        ((MarginLayoutParams) lp).setMargins(-(lp.width - mZoomViewWidth) / 2, 0, 0, 0);
        mZoomView.setLayoutParams(lp);
    }
}

