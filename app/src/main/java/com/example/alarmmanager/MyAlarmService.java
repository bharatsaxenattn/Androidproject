package com.example.alarmmanager;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyAlarmService extends BroadcastReceiver {
    public static final String CHANNEL_ID_1 ="1" ;
    public static final String CHANNEL_ID_2 ="2" ;
    public static final String CHANNEL_NAME_1 ="channel 1" ;
    public static final String CHANNEL_NAME_2 ="channel 2" ;

    public MyAlarmService() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       /* creating notification builder 1 for sound and vibration*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID_1)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setVibrate(new long[]{1000,1000,1000,1000})
                .setContentTitle("Sound and Vibration")
                .setContentText("Sound and Vibration");
        builder.setPriority(Notification.PRIORITY_MAX);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1,builder.build());

        try {
            /* to make sure that both thread will not occur at same time*/
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       /* creating notification 2 for silent notification*/
        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context,CHANNEL_ID_2)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Silent")
                .setContentText("this is silent notification");
        notificationManager.notify(2,builder1.build());


    }


}
