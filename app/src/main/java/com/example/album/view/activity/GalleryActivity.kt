package com.example.album.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.album.Constant
import com.example.album.R
import com.example.album.view.ui.home.HomeFragment
import com.example.album.view.ui.login.Login
import com.example.album.view.ui.profile.Profile
import com.example.album.view.ui.profile.ProfileViewModel
import com.example.album.view.ui.timeline.TimelineFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.BuildConfig
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.app_bar_gallery2.*


class GalleryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {
    val TAG= GalleryActivity::class.java.name
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var storage: FirebaseStorage
    lateinit var reference: StorageReference
    lateinit var imageUrl: String
    lateinit var drawerLayout: DrawerLayout
    lateinit var sharedPreferences: SharedPreferences
    var user_id = ""
    var img_url: String = ""
    lateinit var  navView: NavigationView
    lateinit var  navController:NavController
    lateinit var  bottomNavigationView:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery3)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        bottomNavigationView=findViewById(R.id.bottom_navigation)
        setSupportActionBar(toolbar)

        /* for initializing that which flavour is selected*/
        if(Constant.type==Constant.TYPE.SIDE)
        {
            /* if flavour is side navigation bar*/
            initializeSideNavigation()
            Log.v(TAG," sideNaviggation")
        }
        else if(Constant.type==Constant.TYPE.BOTTOM)
        {
            /*if flavour is bottom navigation bar*/
            supportActionBar!!.hide()
            Log.v(TAG," BottomNavigation")

            bottomNavigationView.setOnNavigationItemSelectedListener(this)
        }


    }

    private fun initializeSideNavigation() {

        drawerLayout = findViewById(R.id.drawer_layout)
         navView= findViewById(R.id.nav_view)
         navController = findNavController(R.id.nav_host_fragment)
        navigationViewSetup()

    }

    override fun onResume() {
        super.onResume()
        if(Constant.type==Constant.TYPE.SIDE) {
            bottomNavigationView.visibility=View.GONE

        }
    }

    private fun navigationViewSetup() {
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.timeline,
                R.id.nav_slideshow
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

        var profileViewModel: ProfileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        val id= FirebaseAuth.getInstance().currentUser!!.uid!!
        Log.v("id===",id.toString())
        profileViewModel.profileImageUrl(id).observe(this, Observer {
            Glide.with(this)
                .load(it)
                .into(imgvw)
            Log.v("profile image",it)
        })
        Glide.with(this)
            .load(img_url)
            .into(imgvw)
        // imgvw.setImageResource()
        tvname.setText(name)
        tv_email.setText(email)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        Log.v("Count==",count.toString())

        /* closing the app is user press back and there is no backstack available*/
        if (count == 0)
        {
            super.onBackPressed()
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }
       else {
            supportFragmentManager.popBackStack()
        }
    }

    /* handling onclick of the navigation view*/
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        val tran = supportFragmentManager
        /* if user selected the home */
        if (id == R.id.nav_home) {
            toolbar.setTitle("Collections")

            tran.beginTransaction().replace(R.id.main_2, HomeFragment()).addToBackStack(null)
                .commit()

        }

        /* if user selected timeline option*/
        if (id == R.id.timeline) {
            toolbar.setTitle("Timeline")
            tran.beginTransaction().replace(R.id.main_2, TimelineFragment.newInstance()).addToBackStack(null)
                .commit()
        }
        /* if user selected timeline option*/
        if (id == R.id.nav_profile) {
            toolbar.setTitle("Profile")
            tran.beginTransaction().add(R.id.main_2, Profile.newInstance()).addToBackStack(null)
                .commit()
        }
        /* if user selected logout option*/
        if (id == R.id.nav_logout) {
            var shared = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = shared.edit()
            editor.putBoolean("sign", false)
            editor.apply()
            editor.commit()
            FirebaseAuth.getInstance().signOut();

            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, Login.newInstance()).commit()
            if(Constant.type==Constant.TYPE.BOTTOM)
            {
                bottomNavigationView.visibility=View.GONE
            }
            FirebaseAuth.getInstance().signOut()

        }
        if(Constant.type==Constant.TYPE.SIDE) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        return true
    }




}
