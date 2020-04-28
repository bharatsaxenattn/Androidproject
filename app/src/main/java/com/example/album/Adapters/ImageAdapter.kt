package com.example.album.Adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.album.ImageData
import com.example.album.R
import com.example.album.databinding.ItemLayoutImageBinding
import com.example.album.ui.SingleImage.SoloImage

class ImageAdapter(var arrayList: List<ImageData>,var manager:FragmentManager): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    class ViewHolder(var binding:ItemLayoutImageBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate= LayoutInflater.from(parent.context)
            val binding: ItemLayoutImageBinding = DataBindingUtil.inflate(inflate,
                R.layout.item_layout_image,parent,false)
            return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
       holder.binding.image=arrayList.get(position)
     //   Log.v("inside adapter=",holder.binding.image.toString())
        holder.binding.executePendingBindings()
        holder.binding.mainAlbumImage.setOnClickListener(View.OnClickListener {
         //   Log.v("Key in adapter:",holder.binding.image.key)
            var a=SoloImage.newInstance()
            var bundle:Bundle= Bundle()
            bundle.putSerializable("imageData",holder.binding.image)
            a.arguments=bundle
            manager.beginTransaction()
                .replace(R.id.main_2,a).addToBackStack(null).commitAllowingStateLoss()
        })

    }
}