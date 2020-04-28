package com.example.album.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.album.ImageData
import com.example.album.R
import com.example.album.data.Imagerepository
import com.example.album.firebase.FirebaseSource
import com.example.album.ui.Image.ImageFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(
    var data1: ImageData
) : BottomSheetDialogFragment() {

    private var mBottomSheetListener: BottomSheetListener? = null

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
        var repo= Imagerepository(firebaseSource)
        repo.deleteImage(data1, activity!!.applicationContext)
        var a= ImageFragment.newInstance()
        var b=Bundle()
        b.putString("title",data1.title)
        a.arguments=b
        dismiss()
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.main_2,a).addToBackStack(null).commitAllowingStateLoss()


    }

    interface BottomSheetListener {
        fun onOptionClick(text: String)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mBottomSheetListener = context as BottomSheetListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString())
        }


    }*/
}

