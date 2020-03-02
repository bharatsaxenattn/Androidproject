package com.example.firstlayout.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstlayout.POJO.NewsModel;
import com.example.firstlayout.R;

import java.util.ArrayList;

public class CustomListAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<NewsModel> al;
    public CustomListAdapter(ArrayList<NewsModel> al, Context context) {
        this.al=al;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch(viewType){
            case NewsModel.IMAGETYPE:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item1,parent,false);
                return new MyViewHolder1(view);
             case NewsModel.TEXTTYPE:
                 view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
                 return new MyViewHolder(view);
            case NewsModel.TEXTONLY:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2,parent,false);
                return new MyViewHolder2(view);


        }
       // View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsModel newsModel=al.get(position);
        String heading=newsModel.getMainHeading();
        String desc=newsModel.getHeading();
        String time=newsModel.getTime();
        int image=newsModel.getImages();

        switch (newsModel.type)
        {
            case NewsModel.TEXTTYPE:

                ((MyViewHolder)holder).mainHeading.setText(heading);
                ((MyViewHolder)holder).heading.setText(desc);
                ((MyViewHolder)holder).time.setText(time);
                ((MyViewHolder)holder).imageView.setImageResource(image);
                break;
                case NewsModel.IMAGETYPE:
                    int image1=newsModel.getImages();
                    ((MyViewHolder1)holder).imageView.setImageResource(image1);
                    break;
            case NewsModel.TEXTONLY:
                ((MyViewHolder2)holder).mainHeading.setText(heading);
                ((MyViewHolder2)holder).heading.setText(desc);
                ((MyViewHolder2)holder).time.setText(time);
                break;

        }




    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mainHeading,heading,time;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainHeading=itemView.findViewById(R.id.mainHeading);
            heading=itemView.findViewById(R.id.heading);
            time=itemView.findViewById(R.id.time);
            imageView=itemView.findViewById(R.id.imageView);

        }
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView;
        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.singleImage);
            cardView=itemView.findViewById(R.id.ImageCardView);

        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView mainHeading,heading,time;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            mainHeading=itemView.findViewById(R.id.mainHeading);
            heading=itemView.findViewById(R.id.heading);
            time=itemView.findViewById(R.id.time);


        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (al.get(position).type)
        {
            case 0:
                return NewsModel.IMAGETYPE;
             case 1:
                 return NewsModel.TEXTTYPE;
            case 2:
                return NewsModel.TEXTONLY;
            default:
                return -1;

        }

    }
}
