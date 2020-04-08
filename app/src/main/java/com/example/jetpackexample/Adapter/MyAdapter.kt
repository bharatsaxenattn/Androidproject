package com.example.jetpackexample.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackexample.DataItems
import com.example.jetpackexample.R
import com.example.jetpackexample.databinding.ItemLayoutBinding

class MyAdapter(val arrayList:List<DataItems>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate=LayoutInflater.from(parent.context)
       val binding:ItemLayoutBinding= DataBindingUtil.inflate(inflate,
           R.layout.item_layout,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user=arrayList.get(position)
        holder.binding.executePendingBindings()

    }
    class ViewHolder(var binding:ItemLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    }
}