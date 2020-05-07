package com.example.album.model.data.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.album.Constant
import com.example.album.model.data.pojo.ImageData
import com.example.album.R
import com.example.album.databinding.ItemLayoutImageBinding
import com.example.album.view.ui.singleImage.SoloImage

class ImageAdapter(var arrayList: List<ImageData>, var manager:FragmentManager): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    val TAG=Class::class.java.name
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
        holder.binding.executePendingBindings()
        holder.binding.mainAlbumImage.setOnClickListener(View.OnClickListener {
          /*creating instance of single image fragment */
            var a=SoloImage.newInstance()
            /*passsing object through bundle*/
            var bundle= Bundle()
            bundle.putSerializable("imageData",holder.binding.image)
            a.arguments=bundle

            /*replacing the single fragment*/
           /* manager.beginTransaction()
                .replace(R.id.main_2,a).addToBackStack(null).commitAllowingStateLoss()*/
            if(Constant.type==Constant.TYPE.BOTTOM)
            {
                Log.v(TAG,"inside bottom")
                manager.beginTransaction()
                    .replace(R.id.bottom_container,a).addToBackStack(null).commitAllowingStateLoss()
            }
            else
            {
                manager.beginTransaction()
                    .replace(R.id.main_2,a).addToBackStack(null).commitAllowingStateLoss()
            }
        })

    }
}