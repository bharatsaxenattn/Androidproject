package com.example.album.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.example.album.R
import com.example.album.data.ImageRepository
import com.example.album.firebase.FirebaseSource

class Profile : Fragment() {

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
        var profile_image:ImageView=view.findViewById(R.id.profile_image)

        var sharedPreferences:SharedPreferences=activity!!.getSharedPreferences("userInfo",Context.MODE_PRIVATE)
        var nm=sharedPreferences.getString("name","")
        var em=sharedPreferences.getString("email","")
        var user_id=sharedPreferences.getString("user_id","")
        var a= FirebaseSource()
      //  var ar=Imagerepository(a).getProfileImageUrl(user_id!!)
      //  Log.v("arratylist is",ar.toString()+"is")

        /* getting image url form the firebase server*/
        var img_url= ImageRepository(a).getProfileImageUrl(user_id!!)
        var sign_out:Button=view.findViewById(R.id.sign_out)
        /* loading the image into the imageview*/
        Glide.with(this)
            .load(img_url)
            .into(profile_image)
        Log.v("profile",em+"email"+img_url)
        name.setText(nm)
        email.setText(em)

        /* signing out from the current user*/
        sign_out.setOnClickListener(View.OnClickListener {
            var shared= context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
            var editor:SharedPreferences.Editor=shared.edit()
            editor.putBoolean("sign",false)
            editor.apply()
            editor.commit()
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_2,Login.newInstance()).addToBackStack(null).commit()
        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance():Profile{
            return Profile()

            }
    }
}
