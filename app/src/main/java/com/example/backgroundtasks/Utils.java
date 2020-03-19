package com.example.backgroundtasks;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {



    public static boolean checkNetworkStatus(final Context context) {

        boolean networkStatus = false;

        // Get connect mangaer
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // check for wifi
        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // check for mobile data
        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() && wifi.isConnected()) {
            networkStatus = true;
        } else networkStatus = mobile.isAvailable() && mobile.isConnected();
        return networkStatus;
    }
}
