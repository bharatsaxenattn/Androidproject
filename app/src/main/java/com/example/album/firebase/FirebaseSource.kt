package com.example.album.firebase

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.album.AlbumItems2
import com.example.album.GalleryActivity
import com.example.album.ImageData
import com.example.album.POJO.ProfileData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalTime

class FirebaseSource {
    /* instantiating the user variable for the firebase user id*/
    lateinit var fuser: String

    /* creating the instance of the firebase authentication*/
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    /* creating the instance of the firebase database*/
    private val firebaseStorageInstance: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    /* this function was used to get the user id of the user*/
    fun getFirebaseUser(context: Context): String {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        var fuser = sharedPreferences.getString("user_id", "")!!
        return fuser
    }

    /* function used to retrive the data of the image*/
    fun retrieveImageData(
        path: String,
        child: String,
        child1: String
    ): MutableLiveData<List<ImageData>> {
        var lvdata: MutableLiveData<List<ImageData>> = MutableLiveData<List<ImageData>>()

        var ref = firebaseStorageInstance.getReference(path).child(child).child(child1)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                var arrayList: ArrayList<ImageData>
                arrayList = ArrayList()
                if (p0.getValue() != null) {
                    var hashMap: HashMap<String, HashMap<String, String>?>? = null
                    hashMap = p0.getValue() as HashMap<String, HashMap<String, String>?>
                    for (key in hashMap.keys) {
                        var a = hashMap[key]!!
                        Log.v("value1==", a.toString())
                        Log.v("key123==", key)
                        var title = a["title"]
                        var time = a["time"]
                        var url = a["imageUrl"]
                        var data = ImageData(url, title, time, key)
                        arrayList.add(data)
                    }

                }
                lvdata.value = arrayList
            }

            override fun onCancelled(p0: DatabaseError) {
                //Toast.makeText(activity,"Error occur!!", Toast.LENGTH_LONG).show()
            }
        })

        return lvdata

    }


    fun uploadImage(path: String, child: String, context: Context, uri: Uri) {

        var uri1 = uri
        val progressDialog = ProgressDialog(context)
        // Code for showing progressDialog while uploading
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        val text1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val text = System.currentTimeMillis()
        Log.v("time", text1.toString())
        var a = FirebaseSource().getFirebaseUser(context)
        val storafgeRef = FirebaseStorage.getInstance()
            .reference.child("Pictures/$a/$child/$text")

        val uplaod = storafgeRef.putFile(uri1)
        uplaod.addOnCompleteListener { uplaodTask ->
            if (uplaodTask.isSuccessful) {
                storafgeRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {

                        uri1 = it
                        // imageView_category.setImageBitmap(imageBitmap)
                        progressDialog.dismiss()
                        Toast.makeText(
                            context,
                            "ImageUploaded sucessfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val text = System.currentTimeMillis()
                        var data =
                            ImageData(uri1.toString(), child, text1.toString(), text.toString())
                        uploadData(data, context)

                    }
                }
            } else {
                uplaodTask.exception?.let {
                    progressDialog.dismiss()
                    Toast.makeText(context, "ImageUploaded failed", Toast.LENGTH_SHORT).show()

                    // Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    var intent = Intent(context, GalleryActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }


    }

    /* uploading the image data to the firebase database*/
    private fun uploadData(data: ImageData, context: Context) {
        var a = FirebaseSource().getFirebaseUser(context)
        var firebaseDatabase = FirebaseDatabase.getInstance()
        val text = System.currentTimeMillis()
        var reference: DatabaseReference =
            firebaseDatabase.getReference("UserImages").child(a).child(
                data.title.toString()
            ).child(text.toString())
        reference.setValue(data).addOnSuccessListener {
            //Toast.makeText(activity,"Database Sucessfully",Toast.LENGTH_LONG).show()
        }

    }

/* deleting the image data from the firebase storage*/
    fun deleteImage(data: ImageData, applicationContext: Context) {
        var a = FirebaseSource().getFirebaseUser(applicationContext)
        var ref = firebaseStorageInstance.getReference("UserImages")
            .child(a).child(data.title.toString()).child(data.key.toString())
        ref.removeValue()
        Log.v("imaege", "Deleted")
    }

    /* cheking the registered email id in auth*/
    fun checkRegisteredEmailValidation(email: String, password: String, context: Context) {
        //firebaseAuth= FirebaseAuth.getInstance()
        val progressDialog = ProgressDialog(context)
        // Code for showing progressDialog while uploading
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {

                    var shared = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                    var editor: SharedPreferences.Editor = shared.edit()
                    var fUser = firebaseAuth.currentUser!!
                    var reference = firebaseStorageInstance.getReference("Users").child(fUser.uid)
                    reference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            val value = p0.getValue(ProfileData::class.java)
                            var name = value!!.name!!
                            Log.v("name==", name)
                            Log.v("value==", value.toString())
                            editor.putString("name", name).apply()
                            progressDialog.dismiss()

                        }

                        override fun onCancelled(p0: DatabaseError) {
                            progressDialog.dismiss()
                            Toast.makeText(context, "Error occur!!", Toast.LENGTH_LONG).show()
                        }
                    })
                    Toast.makeText(context, "SignIn", Toast.LENGTH_LONG).show()

                    /* adding the detail of the user in the shared preferences*/
                    editor.putBoolean("sign", true)
                    editor.putString("user_id", fUser.uid)
                    editor.putString("email", email)

                    editor.putString("password", password)
                    editor.apply()
                    editor.commit()
                    Log.v("currentUser", fUser.uid + "")
                    val intent: Intent = Intent(context, GalleryActivity::class.java)
                    context.startActivity(intent)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(context, "Invalid details", Toast.LENGTH_LONG).show()
                }
            })


    }


    /* getting the image url for the time line*/
    fun getTimelineData(user: String): MutableLiveData<List<ImageData>> {

        var lvdata: MutableLiveData<List<ImageData>> = MutableLiveData<List<ImageData>>()

        var storage = FirebaseDatabase.getInstance()
        //    var a=FirebaseSource().getFirebaseUser(context)
        //getting all the image data for the current user
        var ref = storage.getReference("UserImages").child(user)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                var arrayList: ArrayList<ImageData>
                arrayList = ArrayList()
                if (p0.getValue() != null) {

                    //Log.v("value123",p0.getValue().toString())
                    var hashMap: HashMap<String, HashMap<String, HashMap<String, String>>> =
                        p0.getValue()
                                as HashMap<String, HashMap<String, HashMap<String, String>>>

                    for (key in hashMap.keys) {
                        Log.v("items", hashMap[key].toString())
                        var a = hashMap[key]

                        for (key1 in a!!.keys) {
                            var data1 = a[key1]
                            Log.v("data123", data1.toString())
                            var title = data1!!["title"]
                            var time = data1["time"]
                            var url = data1["imageUrl"]
                            var data = ImageData(url, title, time, key1)
                            arrayList.add(data)
                        }

                    }

                }
                arrayList.sortByDescending({ selector(it) })
                lvdata.value = arrayList

            }

            override fun onCancelled(p0: DatabaseError) {
                //Toast.makeText(activity,"Error occur!!", Toast.LENGTH_LONG).show()
            }
        })

        return lvdata
    }

    /* method use to sort the image based on the timestamp*/
    private fun selector(it: ImageData): String {

        return it.time!!

    }


        /* method for getting the data of the category*/
    fun getCategoryImageData(user: String): MutableLiveData<List<AlbumItems2>> {

        var lvdata: MutableLiveData<List<AlbumItems2>> = MutableLiveData<List<AlbumItems2>>()
        var ref = firebaseStorageInstance.getReference("AlbumCategory").child(user)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                var arrayList: ArrayList<AlbumItems2>
                arrayList = ArrayList()
                if (p0.getValue() != null) {

                    var hashMap: HashMap<String, HashMap<String, String>?>?
                    hashMap = p0.getValue() as HashMap<String, HashMap<String, String>?>
                    for (key in hashMap.keys) {
                        var a = hashMap[key]!!
                        Log.v("value1==", a.toString())
                        Log.v("key==", key.toString())
                        var title = a["title"]
                        var url = a["url"]
                        var data = AlbumItems2(title, url)
                        arrayList.add(data)
                        //   arrayList.add(a)
                    }

                }
                lvdata.value = arrayList

            }

            override fun onCancelled(p0: DatabaseError) {
                // Toast.makeText(activity,"Error occur!!", Toast.LENGTH_LONG).show()
            }
        })
        return lvdata
    }


    /* getting the url of the profile pic*/
    fun getProfileUrl(user_id: String): String {
        var profile_url: String = ""
        var ref = FirebaseDatabase.getInstance()

        var r = ref.getReference("UsersProfile").child(user_id)
        r.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.getValue() != null) {
                    var a = p0.getValue()
                    profile_url = a.toString()
                    Log.v("profile_image12=", profile_url + " is ")
                }

            }
        })

        return profile_url
    }

}