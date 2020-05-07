package com.example.album.view.ui.singleImage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.album.view.ui.bottomsheet.BottomSheet
import com.example.album.model.data.pojo.ImageData

import com.example.album.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

/**
 * A simple [Fragment] subclass.
 * Use the [SoloImage.newInstance] factory method to
 * create an instance of this fragment.
 */
class SoloImage : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
   lateinit var data: ImageData
    lateinit var imageView:ImageView
    lateinit var deleteBtn:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var b=arguments
        data= b!!.getSerializable("imageData") as ImageData
        Log.v("imageData",data.key)
        val view:View=inflater.inflate(R.layout.solo_image, container, false)
        imageView=view.findViewById(R.id.solo_image)
        deleteBtn=view.findViewById(R.id.delete_btn)


        loadImage()
        deleteBtn.setOnClickListener(this)

        // Inflate the layout for this fragment
        return view
    }

    private fun loadImage() {
        Log.v("umgurl",data.imageUrl)
        Glide.with(activity!!.applicationContext)
            .load(data.imageUrl)
            .into(imageView)
    }

    companion object {

        @JvmStatic
        fun newInstance():SoloImage {
            return SoloImage()
        }

    }

    override fun onClick(v: View?) {
        if(v!!.id==R.id.delete_btn)
        {
            val bottomSheet =
                BottomSheet(data)
            bottomSheet.show(activity!!.supportFragmentManager, "BottomSheet")
            //deleteImage()
        }
    }


}
