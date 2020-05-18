package com.bharat.retrofitpractice.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bharat.retrofitpractice.R
import com.bharat.retrofitpractice.activity.WeatherActivity
import com.bharat.retrofitpractice.databinding.StateItemsBinding

class MyAdapter(var list:ArrayList<String>,var context: Context): RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    class ViewHolder (var binding:StateItemsBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflate=LayoutInflater.from(parent.context)
        var bind:StateItemsBinding=DataBindingUtil.inflate(inflate, R.layout.state_items,parent,false)
        return ViewHolder(bind)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var state=list.get(position)
        holder.binding.state=state
        holder.binding.stateLayout.setOnClickListener{

                var intent: Intent =Intent(context, WeatherActivity::class.java)
                intent.putExtra("state",state)
                context.startActivity(intent)

        }
    }


}