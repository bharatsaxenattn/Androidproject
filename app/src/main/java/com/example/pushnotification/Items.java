package com.example.pushnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Items extends AppCompatActivity {
    ImageView imageView;
    TextView textView;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        Intent intent=getIntent();
        String price=intent.getStringExtra("value");
        String url=intent.getStringExtra("url");
        String body=intent.getStringExtra("body");
        String title=intent.getStringExtra("title");
        textView.setText( "title :"+title+"\n"+"body :"+body+"\n"+"price== "+price);

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.food)
                .error(R.drawable.food)
                .into(imageView);





    }
}
