package com.example.backgroundtasks;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    DownloadManager downloadManager;
    Uri uri;
    long reference;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        uri = Uri.parse(MainActivity.ImageUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Downloading Image");
        request.setDescription("Downloading image using service");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "FoodImage");
        reference = downloadManager.enqueue(request);


        String path = Environment.DIRECTORY_DOWNLOADS;
        Log.v("path", path);

    }
}
