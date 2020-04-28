package com.example.album.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.album.GalleryActivity
import com.example.album.POJO.ProfileData

import com.example.album.R
import com.example.album.data.Imagerepository
import com.example.album.firebase.FirebaseSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment(), View.OnClickListener {
    lateinit var btnSignIn: Button
    lateinit var manager: FragmentManager
    lateinit var signUptxt: TextView
    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var fUser: FirebaseUser
    lateinit var mchildListener: ChildEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        var a=sharedPreferences.getBoolean("sign",false)
        Log.v("sharedPre",a.toString())
        if(a)
        {
            val intent:Intent=Intent(context,GalleryActivity::class.java)
           startActivity(intent)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manager= activity!!.supportFragmentManager
        firebaseDatabase= FirebaseDatabase.getInstance()

        val view:View=inflater.inflate(R.layout.fragment_login2, container, false)
        btnSignIn=view.findViewById(R.id.btn_sign_in)
        signUptxt=view.findViewById(R.id.signupTxt)
        edt_email=view.findViewById(R.id.edt_email)
        edt_password=view.findViewById(R.id.edt_password)
        btnSignIn.setOnClickListener(this)
        signUptxt.setOnClickListener(this)
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance():Login
        {
            var instance=Login()
            return instance
        }
    }

    override fun onClick(v: View?) {
        if(v?.id ==R.id.btn_sign_in)
        {
            var firebaseSource: FirebaseSource = FirebaseSource()
            var repo= Imagerepository(firebaseSource)
            repo.checkAuth(edt_email.text.toString(),edt_password.text.toString(),this.context!!)
           //   checkRegisteredEmailValidation()

          /*  val intent: Intent = Intent(activity, GalleryActivity::class.java)
            startActivity(intent)
*/
        }
        if(v?.id ==R.id.signupTxt)
        {
            manager.beginTransaction().replace(R.id.main_screen,Signup.newInstance()).addToBackStack(null).commit()
        }
    }


     fun checkRegisteredEmailValidation() {
        mAuth= FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(edt_email.text.toString(),edt_password.text.toString())
            .addOnCompleteListener(OnCompleteListener { task ->
                if(task.isSuccessful)
                {
                    fUser= mAuth.currentUser!!
                    reference=firebaseDatabase.getReference("Users").child(fUser.uid)
                    reference.addValueEventListener(object: ValueEventListener
                    {
                        override fun onDataChange(p0: DataSnapshot) {
                            val value=p0.getValue(ProfileData::class.java)
                            Log.v("value==",value.toString())

                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(activity,"Error occur!!", Toast.LENGTH_LONG).show()
                        }
                    })
                    Toast.makeText(activity,"SignIn", Toast.LENGTH_LONG).show()

                    val intent:Intent=Intent(activity,GalleryActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(activity,"Invalid details", Toast.LENGTH_LONG).show()
                }
            })



    }

}
