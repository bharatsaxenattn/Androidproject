package com.example.album

import android.Manifest
import android.app.ActionBar
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.album.fragments.Login
import com.example.album.fragments.Profile
import com.example.album.ui.TimelineFragment
import com.example.album.ui.home.HomeFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.app_bar_gallery2.*

class GalleryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var fab: FloatingActionButton
    val PERMISSION_CODE = 101
    val IMAGE_CODE = 102
    lateinit var uri: Uri
    lateinit var storage: FirebaseStorage
    lateinit var reference: StorageReference
    lateinit var titletxt: String
    lateinit var imageUrl: String
    lateinit var drawerLayout: DrawerLayout
    lateinit var sharedPreferences: SharedPreferences
    var user_id = ""
    var img_url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery3)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.timeline, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
        var name = sharedPreferences.getString("name", "Name")
        var email = sharedPreferences.getString("email", "Email")
        user_id = sharedPreferences.getString("user_id", "")!!
        val navHeaderView: View = navView.inflateHeaderView(R.layout.nav_header_gallery2)
        var imgvw: ImageView = navHeaderView.findViewById(R.id.imageView) as ImageView

        var tvname: TextView = navHeaderView.findViewById(R.id.textView)
        var tv_email: TextView = navHeaderView.findViewById(R.id.nav_email)
        Glide.with(this)
            .load(img_url)
            .into(imgvw)
        // imgvw.setImageResource()
        tvname.setText(name)
        tv_email.setText(email)
    }


  /*  private fun createAlbumDialog() {
        var inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.create_popup, null)
        var popup = PopupWindow(
            view,
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        popup.showAtLocation(view, Gravity.CENTER, 0, 0)

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
            popup.dismiss()
        })
        //  popup.showAsDropDown(fab)
        //   popup.showAsDropDown()
    }*/

    private fun openAlbum(text: String?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext!!,
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
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE) {
            uri = data?.data!!
            var bitmap = data?.extras?.get("data") as Bitmap
            uploadImage(bitmap)
        }
    }

    private fun uploadImage(bitmap: Bitmap) {

        if (uri != null) {
            var progressDialog = ProgressDialog(this)
            // Code for showing progressDialog while uploading
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            val storage1 = FirebaseStorage.getInstance()
            val storafgeRef = storage1.getReference().child("albumCategory/$titletxt")
            Log.v("uri==", uri.toString())
            val uplaod = storafgeRef.putFile(uri)
            uplaod.addOnCompleteListener { uplaodTask ->
                if (uplaodTask.isSuccessful) {
                    storafgeRef.downloadUrl.addOnCompleteListener { urlTask ->
                        urlTask.result?.let {
                            uri = it

                            imageUrl = uri.toString() + ".jpg"
                            // imageView_category.setImageBitmap(imageBitmap)
                            progressDialog.dismiss()
                            Toast.makeText(this, "ImageUploaded sucessfully", Toast.LENGTH_SHORT)
                                .show()
                            Log.v("path==", uri.toString())
                            uploadImageUrl()


                        }
                    }
                } else {
                    uplaodTask.exception?.let {
                        progressDialog.dismiss()
                        Toast.makeText(this, "ImageUploaded failed", Toast.LENGTH_SHORT).show()

                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnProgressListener { taskSnapshot: UploadTask.TaskSnapshot ->
                var byteTranfered = (100.0
                        * taskSnapshot.getBytesTransferred()
                        / taskSnapshot.getTotalByteCount())
                progressDialog.setMessage("Uploaded " + byteTranfered + " %")

            }

        }
    }

    private fun uploadImageUrl() {

        var data = AlbumItems2(titletxt, imageUrl)
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference: DatabaseReference =
            firebaseDatabase.getReference("AlbumCategory").child(titletxt)
        reference.setValue(data).addOnSuccessListener {

            Toast.makeText(this, "Database Sucessfully", Toast.LENGTH_LONG).show()
        }

        //updateUI(user)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.gallery, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.nav_home) {
            toolbar.setTitle("Collections")
            var tran = supportFragmentManager
            tran.beginTransaction().replace(R.id.main_2, HomeFragment()).addToBackStack(null)
                .commit()

        }

        if (id == R.id.timeline) {
            toolbar.setTitle("Timeline")
            var tran = supportFragmentManager
            tran.beginTransaction().replace(R.id.main_2, TimelineFragment.newInstance())
                .addToBackStack(null).commit()
        }
        if (id == R.id.nav_profile) {
            toolbar.setTitle("Profile")
            var tran = supportFragmentManager
            tran.beginTransaction().replace(R.id.main_2, Profile.newInstance()).addToBackStack(null)
                .commit()
        }
        if (id == R.id.nav_logout) {
            var shared = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = shared.edit()
            editor.putBoolean("sign", false)
            editor.apply()
            editor.commit()
            supportFragmentManager.beginTransaction().replace(R.id.main_2, Login.newInstance())
                .addToBackStack(null).commit()

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true
    }


}
