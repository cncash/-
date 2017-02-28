package com.yangxbfuj.pathologicalalarm;

import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    KeyboardLayout mKeyboardLayout;

    IAlarm mPlayer;

    ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mKeyboardLayout = (KeyboardLayout) findViewById(R.id.activity_main);
//        mServiceConnection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                Logger.d(TAG,"onServiceConnected");
//                mPlayer = (IAlarm) service;
//                mPlayer.play(null);
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//                Logger.d(TAG,"onServiceDisconnected");
//                mPlayer.stop();
//                mPlayer = null;
//            }
//        };
//        Intent intent = new Intent(getApplicationContext(),AlarmService.class);
//        Logger.d(TAG,"bindService intent is " + intent);
//        getApplicationContext().bindService(intent,mServiceConnection,BIND_AUTO_CREATE);

        mPlayer = new DefaultAlarmPlayer(MediaPlayer.create(this,R.raw.forgiveme),this);

        mPlayer.play(null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mKeyboardLayout.pauseMove();
    }

    @Override
    protected void onResume() {
        super.onStart();
        mKeyboardLayout.startMove(10000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            getApplicationContext().unbindService(mServiceConnection);
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
