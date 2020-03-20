package com.example.webservices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linearLayout;
    Button imageBtn,listBtn,retrofitBtn;
    public static final String URL="https://image.freepik.com/free-photo/image-human-brain_99433-298.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=findViewById(R.id.mainLayout);
        imageBtn=findViewById(R.id.btnSetImage);
        retrofitBtn=findViewById(R.id.downloadRetrofit);
        listBtn=findViewById(R.id.btn);
        imageBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        retrofitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnSetImage)
        {
            setBAckgroundImage();
        }
        if(v.getId()==R.id.btn)
        {
            Log.v("exe","exe1");
            Intent intent=new Intent(MainActivity.this,ListActivity.class);
            startActivity(intent);
        }


        if(v.getId()==R.id.downloadRetrofit)
        {
            Log.v("exe","exe1");
            Intent intent=new Intent(MainActivity.this,ListActivity1.class);
            startActivity(intent);
        }

    }

    private void setBAckgroundImage() {
        Glide.with(MainActivity.this)
                .load(URL)
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linearLayout.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });



    }
}
