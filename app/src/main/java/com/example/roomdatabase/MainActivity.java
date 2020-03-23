package com.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  public static  FragmentManager fragmentManager;

  public  static MyRoomDatabase roomDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roomDatabase= Room.databaseBuilder(getApplicationContext(),MyRoomDatabase.class,"empdb").build();
        fragmentManager=getSupportFragmentManager();
        if(findViewById(R.id.fragmentContainer)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragmentContainer,new MainFragment()).commit();
        }
    }
}
