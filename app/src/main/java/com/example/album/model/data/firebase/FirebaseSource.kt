package com.example.album.model.data.firebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.album.model.data.pojo.AlbumItems2
import com.example.album.view.activity.GalleryActivity
import com.example.album.model.data.pojo.ImageData
import com.example.album.model.data.pojo.CategoryData
import com.example.album.model.data.pojo.ProfileData
import com.example.album.utils.showToast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.lang.Exception
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
                        var data = ImageData(
                            url,
                            title,
                            time,
                            key
                        )
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
                            ImageData(
                                uri1.toString(),
                                child,
                                text1.toString(),
                                text.toString()
                            )
                        uploadData(data, context)

                    }
                }
            } else {
                uplaodTask.exception?.let {
                    progressDialog.dismiss()
                    context!!.showToast("ImageUploaded failed")
                   // Toast.makeText(context, "ImageUploaded failed", Toast.LENGTH_SHORT).show()

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
    fun deleteImage(data: ImageData, applicationContext: Context):MutableLiveData<Boolean> {
    var lvdata=MutableLiveData<Boolean>()
        var a = FirebaseSource().getFirebaseUser(applicationContext)
        var ref = firebaseStorageInstance.getReference("UserImages")
            .child(a).child(data.title.toString()).child(data.key.toString())
        ref.removeValue()
    lvdata.value=true
        Log.v("imaege", "Deleted")
    return lvdata
    }

    /* cheking the registered email id in auth*/
    fun checkRegisteredEmailValidation(email: String, password: String, context: Context): MutableLiveData<Boolean> {
        //firebaseAuth= FirebaseAuth.getInstance()
        var lvdata=MutableLiveData<Boolean>()
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
                            lvdata.value=true
                            val value = p0.getValue(ProfileData::class.java)
                            var name = value!!.name!!
                            Log.v("name==", name)
                            Log.v("value==", value.toString())
                            editor.putString("name", name)
                            editor.putBoolean("sign", true)
                            editor.putString("user_id", fUser.uid)
                            editor.putString("email", email)
                            editor.putString("password", password)
                            editor.apply()
                            editor.commit()
                            editor.commit()
                            progressDialog.dismiss()

                        }

                        override fun onCancelled(p0: DatabaseError) {
                            progressDialog.dismiss()
                            Toast.makeText(context, "Error occur!!", Toast.LENGTH_LONG).show()
                        }
                    })


                    /* adding the detail of the user in the shared preferences*/

                    Log.v("currentUser", fUser.uid + "")

                } else {
                    progressDialog.dismiss()
                   // Toast.makeText(context, "Invalid details", Toast.LENGTH_LONG).show()
                }
            })

            return lvdata
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
                            var data =
                                ImageData(
                                    url,
                                    title,
                                    time,
                                    key1
                                )
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
                try {
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
                            var data =
                                AlbumItems2(
                                    title,
                                    url
                                )
                            arrayList.add(data)
                            //   arrayList.add(a)
                        }

                    }
                    lvdata.value = arrayList
                }
                catch (e:KotlinNullPointerException)
                {

                }
                catch(e1:Exception)
                {

                }

            }

            override fun onCancelled(p0: DatabaseError) {
                // Toast.makeText(activity,"Error occur!!", Toast.LENGTH_LONG).show()
            }
        })
            return lvdata
    }


    /* getting the url of the profile pic*/
    fun getProfileUrl(user_id: String): MutableLiveData<String> {
        var liveData=MutableLiveData<String>()
       // var profile_url: String = ""
        var ref = FirebaseDatabase.getInstance()

        var r = ref.getReference("UsersProfile").child(user_id)
        r.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.getValue() != null) {
                    var a = p0.getValue()
                 //   profile_url = a.toString()
                    liveData.value=a.toString()
                    Log.v("profile_image12=", a.toString() + " is ")
                }

            }
        })

        return liveData
    }



     fun uploadCategoryImage(uri:Uri,context: Context,titletxt:String):MutableLiveData<Boolean> {
         var a=MutableLiveData<Boolean>()
        var uri=uri
        if (uri != null) {
            var progressDialog = ProgressDialog(context)
            // Code for showing progressDialog while uploading
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            val baos = ByteArrayOutputStream()
            var s = FirebaseSource().getFirebaseUser(context!!.applicationContext)
            val storafgeRef = FirebaseStorage.getInstance()
                .reference.child("albumCategory/$s/$titletxt")

            val uplaod = storafgeRef.putFile(uri)
            uplaod.addOnCompleteListener { uplaodTask ->
                if (uplaodTask.isSuccessful) {
                    storafgeRef.downloadUrl.addOnCompleteListener { urlTask ->
                        urlTask.result?.let {
                            uri = it

                            var imageUrl = uri.toString()
                            // imageView_category.setImageBitmap(imageBitmap)
                            progressDialog.dismiss()
                            context.showToast("ImageUploaded Sucessfully")
                            Log.v("path==", uri.toString())
                            uploadCategoryImageUrl(imageUrl,titletxt,s)
                            a.value=true


                        }
                    }
                } else {
                    uplaodTask.exception?.let {
                        progressDialog.dismiss()
                        a.value=false
                        context.showToast("ImageUploaded failed")

                    }
                }
            }

        }
         return  a
    }

     fun uploadCategoryImageUrl(
        imageUrl: String,
        titletxt: String,
        userId: String
    ):MutableLiveData<Boolean> {

        var lvdata=MutableLiveData<Boolean>()
        var data = CategoryData(titletxt, imageUrl)
        var firebaseDatabase = FirebaseDatabase.getInstance()
      //  var user = FirebaseSource().getFirebaseUser(activity!!.applicationContext)
        var reference: DatabaseReference =
            firebaseDatabase.getReference("AlbumCategory").child(userId).child(titletxt)
        reference.setValue(data).addOnSuccessListener {
            lvdata.value=true
        }

        return lvdata
    }

    fun signup(name:String,email: String,password:String ,imageUrl:String,activity:Activity): MutableLiveData<Boolean> {
        var liveData=MutableLiveData<Boolean>()
        var progressDialog= ProgressDialog(activity)
        // Code for showing progressDialog while uploading
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    val user = firebaseAuth.currentUser
                    var id= user?.uid
                    var  user_id= id!!
                    var data= ProfileData(name,email,password,id)
                    var reference=firebaseStorageInstance.getReference("Users").child(id!!)
                    reference.setValue(data).addOnSuccessListener {

                        liveData.value=true
                        progressDialog.dismiss()
                         uploadprofileUrl(user_id,imageUrl)
                      //  activity!!.showToast("Signup Sucessfully")
                        // Toast.makeText(activity,"Signup Sucessfully",Toast.LENGTH_LONG).show()
                    }

                   /* activity!!.supportFragmentManager.beginTransaction().
                    replace(R.id.signup_main, Login.newInstance()).commit()*/
                    //updateUI(user)
                } else {

                    if(task.exception is FirebaseAuthUserCollisionException)
                    {
                       // Log.w(TAG, "The email address is already in use by another account.", task.exception)
                        activity!!.showToast("The email address is already in use by another account.")

                    }
                    else
                    {
                        activity!!.showToast("Signup failed.")
                    }


                }

                // ...
            }

        return liveData
    }


     fun uploadprofileUrl(userId: String, imageUrl: String): MutableLiveData<Boolean> {
        var liveData=MutableLiveData<Boolean>()
        var reference=firebaseStorageInstance.getReference("UsersProfile").child(userId)
        reference.setValue(imageUrl).addOnSuccessListener {
        liveData.value=true
        }
        return liveData

    }


    fun uploadProfileImage(id:String,uri: Uri,activity: Activity): MutableLiveData<String> {
       var liveData=MutableLiveData<String>()
       var  mstorageReference:StorageReference=FirebaseStorage.getInstance().getReference().child(id)

        var progressDialog= ProgressDialog(activity)
        // Code for showing progressDialog while uploading
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        val uplaod=mstorageReference.putFile(uri)
        uplaod.addOnCompleteListener { uplaodTask ->
            if (uplaodTask.isSuccessful) {
                mstorageReference.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                       var path = it
                        liveData.value=path.toString()
                      //  imageUrl=uri.toString()
                        // imageView_category.setImageBitmap(imageBitmap)
                        progressDialog.dismiss()
                       // activity.showToast("Image Uploaded sucessfully")
                       // Toast.makeText(activity,"ImageUploaded sucessfully", Toast.LENGTH_SHORT).show()
                        Log.v("path==", path.toString())
                        // uploadImageUrl()


                    }
                }
            } else {
                uplaodTask.exception?.let {
                    progressDialog.dismiss()
                    activity!!.showToast("ImageUploaded failed")

                }
            }
        }
        return liveData
    }







}

