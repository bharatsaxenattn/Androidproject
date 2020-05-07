package com.example.album.view.ui.addcategory

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.album.view.activity.GalleryActivity
import com.example.album.R
import com.example.album.view.ui.home.HomeFragment
import com.example.album.utils.showToast

class AddCategoryDialog : Fragment() {
    val PERMISSION_CODE = 101
    val IMAGE_CODE = 102
    lateinit var uri: Uri
    lateinit var titletxt: String
    lateinit var imageUrl: String


    companion object {

        @JvmStatic
        fun newInstance(): AddCategoryDialog {
            var instance =
                AddCategoryDialog()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.create_popup, container, false)
        var title: EditText = view.findViewById(R.id.edt_title)
        var done: Button = view.findViewById(R.id.btn_done)
        var cancel: Button = view.findViewById(R.id.btn_cancel)

        done.setOnClickListener(View.OnClickListener {
            if (title.text.length < 3)
                title.setError("Please provide proper album name")
            else {
                titletxt = title.text.toString()
                openAlbum(title.text.toString())
            }
        })
        cancel.setOnClickListener(View.OnClickListener {
            var intent = Intent(activity, GalleryActivity::class.java)
            startActivity(intent)
            // activity!!.supportFragmentManager.beginTransaction().add(R.id.add_main,HomeFragment()).commit()
        })

        return view
    }

    private fun openAlbum(text: String?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this.context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                imagePick()
            }
        } else {
            //system OS is < Marshmallow
            imagePick()
        }

    }

    private fun imagePick() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    imagePick()
                } else {
                    //permission from popup denied
                    activity!!.showToast("Permission denied")

                   // Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE) {
            uri = data?.data!!
            //   val bitmap= data?.extras?.get("data") as Bitmap

            var addCategoryViewModel: AddCategoryViewModel = ViewModelProvider(this)[AddCategoryViewModel::class.java]

            addCategoryViewModel.addCategory(uri,context!!,titletxt).observe(activity!!, Observer {
                if(it)
                {
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_2,HomeFragment()).commit()
                }
            })
           /* var repository=ImageRepository(FirebaseSource())

            *//*uploading the category data*//*
            repository.uploadCategoryImage(uri,context!!,titletxt)
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_2,HomeFragment()).commit()*/

            //uploadImage()
        }
    }


}