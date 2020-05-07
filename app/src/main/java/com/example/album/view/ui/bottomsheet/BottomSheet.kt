package com.example.album.view.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.album.model.data.pojo.ImageData
import com.example.album.R
import com.example.album.view.ui.Image.ImageFragment
import com.example.album.view.ui.singleImage.SingleImageViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(
    var data1: ImageData
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.delete_image_dialog, container, false)
        var del:Button=v.findViewById(R.id.del)
        var cancel:Button=v.findViewById(R.id.cancel)
        del.setOnClickListener(View.OnClickListener {
            deleteImage()
        })
        cancel.setOnClickListener(View.OnClickListener { dismiss() })

        setStyle(BottomSheetDialogFragment.STYLE_NO_TITLE, R.style.CustomBottomSheetDialogTheme);


        return v
    }

    private fun deleteImage() {
        var singleImageViewModel: SingleImageViewModel = ViewModelProvider(this)[SingleImageViewModel::class.java]

        singleImageViewModel.deleteImage(data1,activity!!).observe(activity!!, Observer {
            if(it)
            {
                var a= ImageFragment.newInstance()
                var b=Bundle()
                b.putString("title",data1.title)
                a.arguments=b
                dismiss()
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_2,a).commitAllowingStateLoss()
            }
        })
      /*  var firebaseSource: FirebaseSource = FirebaseSource()
        var repo= ImageRepository(firebaseSource)
        repo.deleteImage(data1, activity!!.applicationContext)*/



    }

    interface BottomSheetListener {
        fun onOptionClick(text: String)
    }

}

