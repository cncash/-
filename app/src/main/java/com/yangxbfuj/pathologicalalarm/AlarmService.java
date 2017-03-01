package com.yangxbfuj.pathologicalalarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;

import java.io.IOException;

/**
 * 播放闹铃的 service
 */
public class AlarmService extends Service{

    private final String TAG = getClass().getSimpleName();

    MediaPlayer mMediaPlayer;

    public AlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(this,R.raw.forgiveme);
        mMediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Player(mMediaPlayer,getBaseContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer = null;
        return super.onUnbind(intent);
    }

    /**
     * 播放器代理
     */
    public static class Player extends Binder implements IAlarmPlayer {

        final String TAG = getClass().getSimpleName();
        // 震动参数
        final long[] VIBRATOR_PAR = {500,200,500,200};

        Vibrator mVibrator;
        MediaPlayer mMediaPlayer;
        Context mContext;

        Player(MediaPlayer mediaPlayer ,Context context){
            this.mMediaPlayer = mediaPlayer;
            this.mContext = context;
            mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        }

        @Override
        public void play(Uri uri) {
            Logger.d(TAG,"start play");
            try {
                mMediaPlayer.stop();
                //mMediaPlayer.setDataSource(mContext,uri);
                mMediaPlayer.prepare();
                // 循环播放
                mMediaPlayer.setLooping(true);
                // 开始播放
                mMediaPlayer.start();
                // 开启震动
                mVibrator.vibrate(VIBRATOR_PAR,2);
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
            Logger.d(TAG,"stop");
            mVibrator.cancel();
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
    }
}
