package com.example.roomdatabase;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public MyAdapter(Context context, List<Employee> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    List<Employee> list;
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        Employee employee=list.get(position);
        int id=employee.getId();
        String name=employee.getName();
        String add=employee.getAddress();
        String num=employee.getNumber();

        String info=" Id : "+id+"\n name: "+name+"\n address : "+add+"\n number : "+num;
        holder.textView.setText(info);
        if(position%2==0)
        {
            holder.textView.setBackgroundResource(R.color.light_green);
            holder.textView.setTextColor(Color.WHITE);
        }
        else
            holder.textView.setBackgroundResource(R.color.lightorange);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.txt_card);
        }
    }
}
