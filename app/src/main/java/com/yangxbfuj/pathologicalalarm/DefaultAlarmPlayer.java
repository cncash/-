package com.yangxbfuj.pathologicalalarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;

import java.io.IOException;

/**
 *  默认的闹铃播放类
 *
 * Created by yangxb on 2017/2/28.
 */

public class DefaultAlarmPlayer implements IAlarmPlayer {

    final String TAG = getClass().getSimpleName();
    // 震动参数
    final long[] VIBRATOR_PAR = {500, 200, 500, 200};

    Vibrator mVibrator;
    MediaPlayer mMediaPlayer;
    Context mContext;

    DefaultAlarmPlayer(MediaPlayer mediaPlayer, Context context) {
        this.mMediaPlayer = mediaPlayer;
        this.mContext = context;
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void play(Uri uri) {
        Logger.d(TAG, "start play");
        try {
            mMediaPlayer.stop();
            //mMediaPlayer.setDataSource(mContext,uri);
            mMediaPlayer.prepare();
            // 循环播放
            mMediaPlayer.setLooping(true);
            // 开始播放
            mMediaPlayer.start();
            // 开启震动
            mVibrator.vibrate(VIBRATOR_PAR, 2);
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    /**
     * 停止播放，回收资源
     */
    @Override
    public void stop() {
        Logger.d(TAG, "stop");
        mVibrator.cancel();
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
    }
}
