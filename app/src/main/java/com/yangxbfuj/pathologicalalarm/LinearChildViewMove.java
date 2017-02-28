package com.yangxbfuj.pathologicalalarm;

import android.animation.ValueAnimator;
import android.view.View;

import java.util.Random;

/**
 * Created by yangxb on 2017/2/27.
 */

public class LinearChildViewMove implements IChildViewMove {

    private final String TAG = getClass().getSimpleName();

    public int mXSpeed;

    public int mYSpeed;

    public static final int DEFAULT_MAX_SPEED = 20;

    private View mView;

    public LinearChildViewMove(int maxSpeed){
        Random random = new Random();
        mXSpeed = random.nextInt() % maxSpeed + 1;
        mYSpeed = random.nextInt() % maxSpeed + 1;
    }

    public LinearChildViewMove(){
        this(DEFAULT_MAX_SPEED);
    }

    @Override
    public int getXSpeed() {
        return mXSpeed;
    }

    @Override
    public int getYSpeed() {
        return mYSpeed;
    }

    @Override
    public IChildViewMove bindView(View view) {
        this.mView = view;
        return this;
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        // 计算移动之前的坐标
        int ol = mView.getLeft();
        int ot = mView.getTop();
        int or = mView.getRight();
        int ob = mView.getBottom();

        // 计算移动之后的坐标
        int nl = ol + mXSpeed;
        int nt = ot + mYSpeed;
        int nr = or + mXSpeed;
        int nb = ob + mYSpeed;

        // 边界判定
        if(nl < l || nr > r){ // 横坐标出界
            mXSpeed = -mXSpeed;
            // 重新计算横坐标
            nl = ol + mXSpeed;
            nr = or + mXSpeed;
        }
        if(nt < t || nb > b){ // 纵坐标出界
            mYSpeed = -mYSpeed;
            // 重新计算纵坐标
            nt = ot + mYSpeed;
            nb = ob + mYSpeed;
        }

        mView.layout(nl,nt,nr,nb);
    }

}
