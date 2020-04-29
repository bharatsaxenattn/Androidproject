package com.example.album.fragments

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.album.GalleryActivity
import com.example.album.POJO.CategoryData
import com.example.album.R
import com.example.album.firebase.FirebaseSource
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class AddCategoryDialog : Fragment() {
    val PERMISSION_CODE = 101
    val IMAGE_CODE = 102
    lateinit var uri: Uri
    lateinit var titletxt: String
    lateinit var imageUrl: String


    companion object {

        @JvmStatic
        fun newInstance(): AddCategoryDialog {
            var instance = AddCategoryDialog()
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
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
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
            uploadImage()
        }
    }

    private fun uploadImage() {

        if (uri != null) {
            var progressDialog = ProgressDialog(activity)
            // Code for showing progressDialog while uploading
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            val baos = ByteArrayOutputStream()
            var s = FirebaseSource().getFirebaseUser(activity!!.applicationContext)
            val storafgeRef = FirebaseStorage.getInstance()
                .reference.child("albumCategory/$s/$titletxt")
            //    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)

            val image = baos.toByteArray()
            val uplaod = storafgeRef.putFile(uri)
            uplaod.addOnCompleteListener { uplaodTask ->
                if (uplaodTask.isSuccessful) {
                    storafgeRef.downloadUrl.addOnCompleteListener { urlTask ->
                        urlTask.result?.let {
                            uri = it

                            imageUrl = uri.toString()
                            // imageView_category.setImageBitmap(imageBitmap)
                            progressDialog.dismiss()
                            Toast.makeText(
                                activity,
                                "ImageUploaded sucessfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.v("path==", uri.toString())
                            uploadImageUrl()


                        }
                    }
                } else {
                    uplaodTask.exception?.let {
                        progressDialog.dismiss()
                        Toast.makeText(activity, "ImageUploaded failed", Toast.LENGTH_SHORT).show()

                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        var intent = Intent(activity, GalleryActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            /* if (uri != null) {
            var progressDialog=ProgressDialog(this)
            // Code for showing progressDialog while uploading
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            var ref:StorageReference=reference.child("images/"+"hello")

            // adding listeners on upload
            // or failure of image
           ref.putFile(uri).addOnSuccessListener { taskSnapshot ->

               Log.v("Image Progress ","Sucess")
               progressDialog.dismiss()
           }.addOnFailureListener(OnFailureListener {
               Log.v("Image Progress ","Failure")
               progressDialog.dismiss()
           }).addOnProgressListener {
               taskSnapshot: UploadTask.TaskSnapshot ->
               var byteTranfered=(100.0
                       * taskSnapshot.getBytesTransferred()
                       / taskSnapshot.getTotalByteCount())
               progressDialog.setMessage("Uploaded "+byteTranfered+" %")
           }
          var path1=  storage.getReferenceFromUrl("gs://album-d9827.appspot.com/images/"+titletxt+".jpg")
            var path=ref.path
            Log.v("path==",path.toString())
            Log.v("path==",path1.toString())


        }

*/
        }
    }

    private fun uploadImageUrl() {

        var data = CategoryData(titletxt, imageUrl)
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var user = FirebaseSource().getFirebaseUser(activity!!.applicationContext)
        var reference: DatabaseReference =
            firebaseDatabase.getReference("AlbumCategory").child(user).child(titletxt)
        reference.setValue(data).addOnSuccessListener {
            Toast.makeText(activity, "Database Sucessfully", Toast.LENGTH_LONG).show()
        }
        var intent = Intent(activity, GalleryActivity::class.java)
        startActivity(intent)

        //updateUI(user)
    }
}