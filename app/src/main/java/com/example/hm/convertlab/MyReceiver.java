package com.example.hm.convertlab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by hm on 22.02.2016.
 */
public class MyReceiver extends BroadcastReceiver {
    private static int mRefreshTimeMinute = 10;
    private static final long mRefreshTimeOneMinute = 60000;
    private static long mRefreshTime = mRefreshTimeOneMinute * mRefreshTimeMinute;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, MyService.class);
        PendingIntent pending1 = PendingIntent.getService(context, 0, myIntent,
                PendingIntent.FLAG_NO_CREATE);
        if (pending1 != null) {
        } else {
            PendingIntent pIntent = PendingIntent.getService(context, 0, myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), mRefreshTime, pIntent);
        }
    }
}
