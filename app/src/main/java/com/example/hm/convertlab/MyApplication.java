package com.example.hm.convertlab;

import android.app.Application;
import android.content.Intent;

import com.example.hm.convertlab.database.BankDatabase;

/**
 * Created by hm on 15.02.2016.
 */
public class MyApplication extends Application {

    public static boolean sIsActive = false;

    final String action = "start.myreciver";

    @Override
    public void onCreate() {
        super.onCreate();
        MyNotification.init(this);

        Intent intent = new Intent(action);
        sendBroadcast(intent);
        BankDatabase.init(this);
    }
}
