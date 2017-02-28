package com.yangxbfuj.pathologicalalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.coolerfall.daemon.Daemon;

public class BootCompleteReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();

    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(TAG,"onReceive");
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
//            Intent intent1 = new Intent(context,MainActivity.class);
//            context.startActivity(intent1);
            AlarmManager manager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            long triggerTime = SystemClock.elapsedRealtime();
            Intent intent2 = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent2,0);
            Log.d(TAG,"set a alarm task start after 5 minutes");
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,30 * 1000,pendingIntent);
            //manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,pendingIntent);
        }
    }
}
