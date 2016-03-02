package com.example.hm.convertlab;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

/**
 * Created by hm on 02.03.2016.
 */
public class MyNotification {
    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder mBuilder;
    private static String percentage;

    private static Context context;
    public static void init(Context _context) {
        context = _context;
    }
    public static void setPercentage(int _bankSize, int _i){
        int d = _i * 100 / _bankSize ;
        percentage = String.valueOf(d);
    }

    public static void startNotification(int _bankSize, int id){
        setPercentage(_bankSize, id);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("Loading Date")
                .setContentText("  " + percentage + " %")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setProgress(_bankSize, id, false);
        Notification notification = mBuilder.build();
        notificationManager.notify(100, notification);

    }

    public static void completeNotification(){
        mBuilder.setContentText("Loading complete")
                .setProgress(0, 0, false);
        notificationManager.notify(100, mBuilder.build());
        notificationManager.cancelAll();
    }

}
