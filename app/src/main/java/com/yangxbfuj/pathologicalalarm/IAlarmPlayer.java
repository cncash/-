package com.yangxbfuj.pathologicalalarm;

import android.net.Uri;

/**
 * Created by yangxb on 2017/2/28.
 */

public interface IAlarmPlayer {

    /**
     * 播放闹铃
     * @param uri
     */
    void play(Uri uri);

    /**
     * 停止播放闹铃
     */
    void stop();

}
