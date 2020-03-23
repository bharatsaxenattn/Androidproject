package com.example.alarmmanager;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AlarmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    MyAlarmService.CHANNEL_ID_1,
                    MyAlarmService.CHANNEL_NAME_1,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("channel1");
            channel1.enableVibration(true);
            channel1.shouldVibrate();


            NotificationChannel channel2 = new NotificationChannel(MyAlarmService.CHANNEL_ID_2,
                    MyAlarmService.CHANNEL_NAME_2,
                    NotificationManager.IMPORTANCE_LOW

            );
            channel2.setDescription("channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
