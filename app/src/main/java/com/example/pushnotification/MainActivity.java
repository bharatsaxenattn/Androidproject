package com.example.pushnotification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //for creating notification
    NotificationManager mnotificationManager;
    NotificationChannel mNotificationChannel;
    NotificationCompat.Builder builder;
    public static final String CHANNEL_ID="notification";
    public static final String CHANNEL_NAME="myNotification";
    public static final int NOTIFICATION_ID=100;


    Button button,cancel,update;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.notifyBtn);
        cancel=findViewById(R.id.cancel);
        update=findViewById(R.id.update);

        createNotificationChannel();
        createNotificationBuilder();

        button.setOnClickListener(this);
        cancel.setOnClickListener(this);
        update.setOnClickListener(this);



    }

    private void createNotificationBuilder() {
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle(" title is set")
                .setContentText("welcome to mynotification")
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_alarm,"this is an action",pendingIntent)
                .addAction(R.drawable.ic_alarm,"this is an action",pendingIntent)
                .setAutoCancel(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        mnotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mNotificationChannel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
        mNotificationChannel.enableLights(true);
        mNotificationChannel.setLightColor(Color.RED);
        mNotificationChannel.enableVibration(true);
        mNotificationChannel.setDescription("hello this is my first notification");
        mnotificationManager.createNotificationChannel(mNotificationChannel);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.notifyBtn:
                mnotificationManager.notify(NOTIFICATION_ID,builder.build());
                break;
            case R.id.cancel:
                mnotificationManager.cancel(NOTIFICATION_ID);
                break;

            case R.id.update:
                updateNotification();
                break;
        }

    }

    private void updateNotification() {
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.food);
        builder.setStyle(new NotificationCompat.BigPictureStyle()
        .bigPicture(bitmap)
        .setBigContentTitle("hello your notification updated"));
    }
}
