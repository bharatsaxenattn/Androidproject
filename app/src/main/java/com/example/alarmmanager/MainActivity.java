package com.example.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button startBtn,stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn=findViewById(R.id.start);
        stopBtn=findViewById(R.id.stop);

        stopBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.start)
        {
            Log.v("Exe","start");
            Intent intent=new Intent(MainActivity.this,MyAlarmService.class);
            PendingIntent pendingIntent= PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeInMillis = System.currentTimeMillis();
            long intervalInMillis = 5000;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, intervalInMillis, pendingIntent);
        }
        if (v.getId()==R.id.stop)
        {
            Intent intent = new Intent(MainActivity.this, MyAlarmService.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

        }

    }
}
