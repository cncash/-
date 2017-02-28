package com.yangxbfuj.pathologicalalarm;

import android.view.View;

/**
 * Created by yangxb on 2017/2/27.
 */

public interface IChildViewMove {
    // X方向的速度
    int getXSpeed();

    // Y方向的速度
    int getYSpeed();

    // 绑定 View 对象
    IChildViewMove bindView(View view);

    // 放置 View 对象
    void layout(int l, int t, int r, int b);

}
