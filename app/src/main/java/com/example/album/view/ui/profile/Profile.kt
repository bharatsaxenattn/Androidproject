package com.example.album.view.ui.profile

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.album.R
import com.example.album.view.ui.login.Login
import com.google.firebase.auth.FirebaseAuth

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
        var sign_out:Button=view.findViewById(R.id.sign_out)

        /* sharedpreference tasks*/
        var sharedPreferences:SharedPreferences=activity!!.getSharedPreferences("userInfo",Context.MODE_PRIVATE)
        var nm=sharedPreferences.getString("name","")
        var em=sharedPreferences.getString("email","")
        var user_id=sharedPreferences.getString("user_id","")


        /* getting image url form the firebase server*/

        var profileViewModel: ProfileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        profileViewModel.profileImageUrl(user_id!!).observe(activity!!, Observer {
            Glide.with(this)
                .load(it)
                .into(profile_image)
            Log.v("profile image",it)
        })

        name.setText(nm)
        email.setText(em)

        /* signing out from the current user*/
        sign_out.setOnClickListener(View.OnClickListener {
            siginOut()

        })

        return view
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
