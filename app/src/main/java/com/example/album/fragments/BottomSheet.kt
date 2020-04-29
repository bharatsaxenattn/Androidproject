package com.example.album.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.album.ImageData
import com.example.album.R
import com.example.album.data.ImageRepository
import com.example.album.firebase.FirebaseSource
import com.example.album.ui.Image.ImageFragment
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
        var firebaseSource: FirebaseSource = FirebaseSource()
        var repo= ImageRepository(firebaseSource)
        repo.deleteImage(data1, activity!!.applicationContext)
        var a= ImageFragment.newInstance()
        var b=Bundle()
        b.putString("title",data1.title)
        a.arguments=b
        dismiss()
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.main_2,a).commitAllowingStateLoss()


    }

    interface BottomSheetListener {
        fun onOptionClick(text: String)
    }

}

