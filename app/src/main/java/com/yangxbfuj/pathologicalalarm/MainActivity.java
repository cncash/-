package com.yangxbfuj.pathologicalalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    KeyboardLayout mKeyboardLayout;

    IAlarm mPlayer;

    ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mKeyboardLayout = (KeyboardLayout) findViewById(R.id.activity_main);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Logger.d(TAG,"onServiceConnected");
                mPlayer = (IAlarm) service;
                mPlayer.play(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Logger.d(TAG,"onServiceDisconnected");
                mPlayer.stop();
                mPlayer = null;
            }
        };
        Intent intent = new Intent(this,AlarmService.class);
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
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
            unbindService(mServiceConnection);
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
