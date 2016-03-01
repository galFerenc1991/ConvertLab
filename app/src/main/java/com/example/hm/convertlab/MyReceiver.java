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
    private static long mRefreshTime = 30000;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Ricsimegmondta", "elindult");
        Intent myIntent = new Intent(context, MyService.class);
        PendingIntent pending1 = PendingIntent.getService(context, 0, myIntent,
                PendingIntent.FLAG_NO_CREATE);
        if (pending1 != null) {
            Log.d("Ricsimegmondta", "van intent");
        } else {
            PendingIntent pIntent = PendingIntent.getService(context, 0, myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), mRefreshTime, pIntent);
        }
    }
}
