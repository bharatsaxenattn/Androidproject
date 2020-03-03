package com.example.firstlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="Lifecycler1";

    Button signinButton,signinButton1,recycler1,recycler2,signUpBtn,storage1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signinButton=findViewById(R.id.layout_design);
        signinButton1=findViewById(R.id.layout_design1);
        signUpBtn=findViewById(R.id.layout_design2);
        recycler1=findViewById(R.id.recyclerBtn);
        recycler2=findViewById(R.id.recyclerBtn1);
        storage1=findViewById(R.id.storage1);
        Log.v(TAG,"oncreate");
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Restaurnats.class));
            }
        });
        signinButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Restaurant1.class));

            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));

            }
        });
        recycler1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PaginationActivity.class));
            }
        });
        recycler2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CustomRecyclerview.class));

            }
        });

        storage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Storage.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG,"onStart=========");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG,"onResume=========");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG,"onPause=========");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG,"onStop=========");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG,"onDestroy=========");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG,"onResttart=========");
    }


}
