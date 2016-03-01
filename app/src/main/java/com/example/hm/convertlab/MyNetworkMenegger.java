package com.example.hm.convertlab;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by hm on 01.03.2016.
 */
public class MyNetworkMenegger {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
