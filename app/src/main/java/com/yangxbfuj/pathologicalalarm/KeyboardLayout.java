package com.yangxbfuj.pathologicalalarm;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by yangxb on 2017/2/27.
 */

public class KeyboardLayout extends FrameLayout {

    private final String TAG = getClass().getSimpleName();

    private ArrayList<IChildViewMove> mViewMoves;

    private boolean mFirstLayout = true;
    ValueAnimator mValueAnimator;

    public KeyboardLayout(Context context) {
        super(context);
        Logger.d(TAG, "init mViewMoves _0");
    }

    public KeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewMoves = new ArrayList<>();
        mValueAnimator = ValueAnimator.ofFloat(0F, 1F);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Logger.i(TAG, "onAnimationUpdate");
                requestLayout();
            }
        });
        Logger.d(TAG, "init mViewMoves _1");
    }

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Logger.d(TAG, "init mViewMoves _2");
    }

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Logger.d(TAG, "init mViewMoves _3");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.i(TAG, "start onLayout");
        int i = 0;
        if (mFirstLayout) {
            while (i < getChildCount()) {
                View child = getChildAt(i);
                if (child != null) {
                    Logger.i(TAG, "child " + i + "width = " + child.getMeasuredWidth());
                    int cl = (r - l - child.getMeasuredWidth()) / 2;
                    int ct = (b - t - child.getMeasuredHeight()) / 2;
                    child.layout(cl, ct, cl + child.getMeasuredWidth(), ct + child.getMeasuredHeight());
                    Logger.i(TAG, "addView to mViewMoves");
                    mViewMoves.add(new LinearChildViewMove().bindView(child));
                }
                i++;
            }
            mFirstLayout = false;
        } else {
            Logger.i(TAG, "mViewMoves size = " + mViewMoves.size());
            while (i < mViewMoves.size()) {
                Logger.i(TAG, "start relayout");
                mViewMoves.get(i).layout(l, t, r, b);
                i++;
            }
        }
    }


    /**
     * 空间内部的View开始移动
     */
    public void startMove(int time) {
        if (!mValueAnimator.isStarted()) {
            mValueAnimator.setRepeatCount(time);
            mValueAnimator.start();
        } else if (mValueAnimator.isPaused()) {
            mValueAnimator.resume();
        }
    }

    /**
     * 暂停移动
     */
    public void pauseMove() {
        mValueAnimator.pause();
    }

}
