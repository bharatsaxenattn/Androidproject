package com.example.album.Adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.album.AlbumItems2
import com.example.album.R
import com.example.album.databinding.ItemLayout2Binding
import com.example.album.databinding.ItemLayout4BindingImpl
import com.example.album.ui.Image.ImageFragment


class MyAdapter(val arrayList:List<AlbumItems2>,var grid:Int,var manager: FragmentManager): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener {
    lateinit var title:String
    class ViewHolder(var binding: ItemLayout2Binding):RecyclerView.ViewHolder(binding.root) {

    }
    class ViewHolder2(var binding: ItemLayout4BindingImpl):RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate= LayoutInflater.from(parent.context)
        if(viewType==AlbumItems2.IMAGETYPE2)
        {
            val binding:ItemLayout2Binding= DataBindingUtil.inflate(inflate,
                R.layout.item_layout2,parent,false)
            return ViewHolder(binding)
        }
        else
        {
            val binding:ItemLayout4BindingImpl= DataBindingUtil.inflate(inflate,
                R.layout.item_layout4,parent,false)
            return ViewHolder2(binding)
        }

    }

    override fun getItemCount(): Int {
      return arrayList.size;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(grid==2)
        {
            holder as ViewHolder
            holder.binding.album=arrayList.get(position)
            //  holder.binding=arrayList.get(position).imgUrl
            holder.binding.executePendingBindings()
            var t=arrayList.get(position).name
            holder.binding.mainAlbumImage.setOnClickListener{
                var bundle=Bundle()

                bundle.putString("title",t)
                Log.v("title adapter",t)
                var a=ImageFragment()
                a.arguments=bundle
                manager.beginTransaction()
                    .replace(R.id.main_2,a).addToBackStack(null).commit()
            }
        }
        else
        {
            holder as ViewHolder2
            holder.binding.album=arrayList.get(position)
            //  holder.binding=arrayList.get(position).imgUrl
            holder.binding.executePendingBindings()
        }


    }


    override fun getItemViewType(position: Int): Int {
        if(grid==2)
        {
            return AlbumItems2.IMAGETYPE2
        }
        else
            return AlbumItems2.IMAGETYPE4
    }

    override fun onClick(v: View?) {

        if(v!!.id==R.id.mainAlbumImage)
        {

        }

    }


}