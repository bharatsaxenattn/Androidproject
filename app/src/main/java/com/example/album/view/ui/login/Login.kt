package com.example.album.view.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.album.R
import com.example.album.utils.isNetworkAvailable
import com.example.album.utils.showToast
import com.example.album.view.activity.GalleryActivity
import com.example.album.view.ui.signup.Signup
import com.example.album.view.ui.signup.SignupViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class Login : Fragment(), View.OnClickListener {
    lateinit var btnSignIn: Button
    lateinit var manager: FragmentManager
    lateinit var signUptxt: TextView
    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var btngoogle:Button
    lateinit var firebaseDatabase: FirebaseDatabase
    val RC_SIGN_IN = 110
    lateinit var googleSignInClient: GoogleSignInClient
    val TAG = "Login"
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        var a = sharedPreferences.getBoolean("sign", false)
        auth = FirebaseAuth.getInstance()
        gsoInitialize()
        Log.v("sharedPre", a.toString())
        if (a) {
            val intent: Intent = Intent(
                context,
                GalleryActivity::class.java
            )
            startActivity(intent)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manager = activity!!.supportFragmentManager
        firebaseDatabase = FirebaseDatabase.getInstance()

        val view: View = inflater.inflate(R.layout.fragment_login2, container, false)
        btnSignIn = view.findViewById(R.id.btn_sign_in)
        signUptxt = view.findViewById(R.id.signupTxt)
        edt_email = view.findViewById(R.id.edt_email)
        btngoogle=view.findViewById(R.id.g_signin)
        edt_password = view.findViewById(R.id.edt_password)
        btnSignIn.setOnClickListener(this)
        signUptxt.setOnClickListener(this)
        btngoogle.setOnClickListener(this)
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(): Login {
            var instance = Login()
            return instance
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_sign_in) {

            if(activity!!.isNetworkAvailable())
            {
                var loginViewModel: LoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
                loginViewModel.login(
                    edt_email.text.toString(),
                    edt_password.text.toString(),
                    this.context!!
                ).observe(activity!!
                    , Observer {
                        if (it) {
                            activity!!.showToast("Signin")
                            //Toast.makeText(context, "SignIn", Toast.LENGTH_LONG).show()
                            val intent = Intent(context, GalleryActivity::class.java)
                            startActivity(intent)
                        } else {
                            activity!!.showToast("Login failed")
                        }
                    })

            }
            else
            {
                activity!!.showToast("Please connect to wifi or mobile network")
            }

        }
        if (v?.id == R.id.signupTxt) {
            manager.beginTransaction().replace(
                R.id.main_screen,
                Signup.newInstance()
            ).addToBackStack(null).commit()
        }
        if (v?.id == R.id.g_signin) {
            gsignIn()
        }

    }


    /* google signin initialization*/
    fun gsoInitialize() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        googleSignInClient = GoogleSignIn.getClient(activity!!, gso)
    }

    private fun gsignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /* handling onactivity result of google signin*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!,account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }


    }

    private fun firebaseAuthWithGoogle(
        idToken: String,
        account: GoogleSignInAccount
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    val name=account.displayName.toString()
                    val email=account.email.toString()
                    val profileUrl=account.photoUrl.toString()
                    Log.v(TAG,name+"name "+email+" email "+profileUrl+" url")
                    /* if task is sucessful then upload image url to the database*/
                    var loginViewModel: LoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

                            loginViewModel.updateProfileUrl(id.toString(), account.photoUrl!!.toString()).observe(activity!!,
                                Observer {
                                    activity!!.showToast("Signin")
                                    var shared = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                                    var editor: SharedPreferences.Editor = shared.edit()
                                    editor.putBoolean("sign", true)
                                    editor.putString("user_id", user!!.uid)
                                    editor.putString("email", email)
                                    editor.putString("name", name).apply()
                                    editor.putString("password", "googlesignin")
                                    editor.apply()
                                    editor.commit()
                                    //Toast.makeText(context, "SignIn", Toast.LENGTH_LONG).show()
                                    val intent = Intent(context, GalleryActivity::class.java)
                                    startActivity(intent)
                                })


                } else {

                    Log.w(TAG, "signInWithCredential:failure", task.exception)

                }

                // ...
            }
    }
}