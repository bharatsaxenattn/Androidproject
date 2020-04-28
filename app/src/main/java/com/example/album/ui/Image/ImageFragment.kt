package com.example.album.ui.Image

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.album.Adapters.ImageAdapter
import com.example.album.Adapters.MyAdapter
import com.example.album.AlbumItems2
import com.example.album.GalleryActivity
import com.example.album.ImageData

import com.example.album.R
import com.example.album.firebase.FirebaseSource
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "title"
private const val ARG_PARAM2 = "param2"
lateinit var manager: FragmentManager
lateinit var recyclerView: RecyclerView
lateinit var shimmerFrameLayout: ShimmerFrameLayout



var IMAGE_CODE=101
var PERMISSION_CODE=110

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var title: String? = "title"
    private var param2: String? = null
    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_image, container, false)
        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout)
        shimmerFrameLayout.startShimmer()
        var fab: FloatingActionButton = view.findViewById(R.id.addFab)
        recyclerView=view.findViewById(R.id.image_recycler)
        fab.setOnClickListener(this)

        arguments!!.getString("title")
        Log.v("title12", title)
        manager = activity!!.supportFragmentManager

        var viewModel:ImageViewModel= ViewModelProviders.of(activity!!).get(ImageViewModel::class.java)
        var a=FirebaseSource().getFirebaseUser(this.context!!)
        viewModel.getImageData("UserImages",a,title.toString()).observe(activity!!, Observer<List<ImageData>> {
            shimmerFrameLayout.visibility = View.GONE
            shimmerFrameLayout.stopShimmer()
            recyclerView.layoutManager= GridLayoutManager(activity,4) as RecyclerView.LayoutManager?
            var adapter:ImageAdapter= ImageAdapter(it,activity!!.supportFragmentManager)
            recyclerView.adapter=adapter

        })

        return view
    }



  /*  private fun getImageData() {
         var arrayList: ArrayList<ImageData>
        arrayList=ArrayList()
        var database= FirebaseDatabase.getInstance()
        var ref=database.getReference("UserImages").child("bharat").child(title.toString())
        ref.addValueEventListener(object: ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot) {

                if(p0.getValue()!=null) {
                    var hashMap: HashMap<String, HashMap<String, String>?>? = null
                    hashMap = p0.getValue() as HashMap<String, HashMap<String, String>?>
                    for (key in hashMap.keys) {
                        var a = hashMap[key]!!
                        Log.v("value1==", a.toString())
                        var title = a["title"]
                        var time = a["time"]
                        var url = a["imageUrl"]
                        var data = ImageData(url, title, time)
                        arrayList.add(data)
                        //   arrayList.add(a)
                    }
                }

                recyclerView.layoutManager= GridLayoutManager(activity,4) as RecyclerView.LayoutManager?
                var adapter:ImageAdapter= ImageAdapter(arrayList)
                recyclerView.adapter=adapter




            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,"Error occur!!", Toast.LENGTH_LONG).show()
            }
        })


    }*/

    companion object {
        @JvmStatic
        fun newInstance(): ImageFragment {
            return ImageFragment()
        }
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.addFab) {
            openAlbum()
        }
    }

    private fun openAlbum() {

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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE) {
            uri = data?.data!!
            //   val bitmap= data?.extras?.get("data") as Bitmap
            var viewModel:ImageViewModel= ViewModelProviders.of(activity!!).get(ImageViewModel::class.java)
            val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
            var a=sharedPreferences.getString("user_id","")
         //   Log.v("sharedPre",a.toString())
            viewModel.uploadImage("Pictures/$a",title.toString(),activity!!,uri)
            //uploadImage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadImage() {

        if (uri != null) {
            var progressDialog = ProgressDialog(activity)
            // Code for showing progressDialog while uploading
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            val baos = ByteArrayOutputStream()
          //  var a= Random(0,1000).nextInt()
            val text1 = LocalTime.now()
            val text = System.currentTimeMillis()
            Log.v("time",text1.toString())
            val storafgeRef = FirebaseStorage.getInstance()
                .reference.child("Pictures/abc/$title/$text")
            //    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)

            val image = baos.toByteArray()
            val uplaod = storafgeRef.putFile(uri)
            uplaod.addOnCompleteListener { uplaodTask ->
                if (uplaodTask.isSuccessful) {
                    storafgeRef.downloadUrl.addOnCompleteListener { urlTask ->
                        urlTask.result?.let {

                            uri = it
                            // imageView_category.setImageBitmap(imageBitmap)
                            progressDialog.dismiss()
                            Toast.makeText(
                                activity,
                                "ImageUploaded sucessfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            val text = System.currentTimeMillis()
                            var data=ImageData(uri.toString(),title,text1.toString(),text.toString())
                            uploadData(data)

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
        }
    }

    private fun uploadData(data: ImageData) {

        var firebaseDatabase= FirebaseDatabase.getInstance()
        var a=FirebaseSource().fuser
        val text = System.currentTimeMillis()
        var reference: DatabaseReference =firebaseDatabase.getReference("UserImages").child(a).child(
            data.title.toString()).child(text.toString())
        reference.setValue(data).addOnSuccessListener {
            Toast.makeText(activity,"Database Sucessfully",Toast.LENGTH_LONG).show() }

    }
}


