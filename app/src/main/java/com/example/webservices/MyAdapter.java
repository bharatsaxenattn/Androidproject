package com.example.webservices;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<ListItems> arrayList=new ArrayList<>();
    Context context;

    public MyAdapter(ArrayList<ListItems> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        ListItems listItems=arrayList.get(position);
        String name=listItems.getName();
        String message=listItems.getMessage();
        String url=listItems.getProfileImage();
        Log.v("image",url+"");
        holder.message.setText(message+" ");
        holder.name.setText(name+" ");
             Glide.with(context)
                .load(url)
                .centerCrop()
                .into(holder.imageView);
             if(position%2==0)
             {
                 holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
             }
                else
             {
                 holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
             }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,message;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.list_image);
            name=itemView.findViewById(R.id.name);
            message=itemView.findViewById(R.id.message);
            linearLayout=itemView.findViewById(R.id.listLayout);
        }
    }
}
