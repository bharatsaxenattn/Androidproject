package com.example.album.view.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.album.R
import com.example.album.utils.showToast
import com.example.album.view.ui.login.Login
import com.example.album.view.ui.signup.SignupViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

class Profile : Fragment() {
    val PERMISSION_CODE=105
    val IMAGE_CODE=106
    lateinit var profile_image:ImageView
    var profileUri: Uri? =null
    val id=FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_profile, container, false)
        var name:TextView=view.findViewById(R.id.profile_name)
        var email:TextView=view.findViewById(R.id.profile_email)
        profile_image=view.findViewById(R.id.profile_image)
        var sign_out:Button=view.findViewById(R.id.sign_out)

        /* sharedpreference tasks*/
        var sharedPreferences:SharedPreferences=activity!!.getSharedPreferences("userInfo",Context.MODE_PRIVATE)
        var nm=sharedPreferences.getString("name","")
        var em=sharedPreferences.getString("email","")
        var user_id=sharedPreferences.getString("user_id","")

        Log.v("uId",id)
        /* getting image url form the firebase server*/

        var profileViewModel: ProfileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        profileViewModel.profileImageUrl(id).observe(activity!!, Observer {
            Glide.with(this)
                .load(it)
                .error(R.drawable.image)
                .into(profile_image)

            Log.v("profile image",it)
        })

        name.setText(nm)
        email.setText(em)

        /* signing out from the current user*/
        sign_out.setOnClickListener(View.OnClickListener {
            siginOut()

        })

        profile_image.setOnClickListener({
            selectImage()
        })

        return view
    }

    private fun selectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(
                    activity?.applicationContext!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
            else{
                //permission already granted
                imagePick()
            }
        }
        else{
            //system OS is < Marshmallow
            imagePick()
        }
    }


    private fun imagePick() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    imagePick()
                }
                else{
                    //permission from popup denied
                    activity!!.showToast("Permission denied")
                    //Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE){
            var uri: Uri = data?.data!!
            profileUri=uri
            profile_image.setImageURI(data?.data)

            var signupViewModel: SignupViewModel = ViewModelProvider(this)[SignupViewModel::class.java]

            if(profileUri!=null)
            {
                signupViewModel.uploadImage(id, profileUri!!,activity!!).observe(activity!!,
                    Observer {
                        signupViewModel.updateProfileUrl(id,it).observe(activity!!, Observer {
                            activity!!.showToast("Profile Image Uploaded")
                        })

                    })
            }




        }
    }


    private fun siginOut() {
        var shared= context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        var editor:SharedPreferences.Editor=shared.edit()
        editor.putBoolean("sign",false)
        editor.apply()
        editor.commit()
        FirebaseAuth.getInstance().signOut();
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_2,
            Login.newInstance()).addToBackStack(null).commit()
    }


    companion object {

        @JvmStatic
        fun newInstance(): Profile {
            return Profile()

            }
    }
}
