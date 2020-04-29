package com.example.album.activity

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.album.R
import com.example.album.fragments.Login
import com.example.album.fragments.Profile
import com.example.album.ui.timeline.TimelineFragment
import com.example.album.ui.home.HomeFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
        Glide.with(this)
            .load(img_url)
            .into(imgvw)
        // imgvw.setImageResource()
        tvname.setText(name)
        tv_email.setText(email)
    }





  /*  override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.gallery, menu)
        return true
    }
*/
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
