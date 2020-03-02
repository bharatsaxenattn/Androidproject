package com.example.firstlayout.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstlayout.R;

import java.util.ArrayList;

public class PaginationAdapter extends RecyclerView.Adapter<PaginationAdapter.MyViewHolder> {
    ArrayList<String> al=new ArrayList<>();
    Context context;
    public PaginationAdapter(ArrayList<String> al,Context context) {
        this.al=al;
        this.context=context;
    }

    @NonNull
    @Override
    public PaginationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pagination_elements,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaginationAdapter.MyViewHolder holder, int position) {
        String name=al.get(position);
        holder.textView.setText(name);

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textPagination);
        }
    }
}
