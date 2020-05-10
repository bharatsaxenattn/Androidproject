package  com.example.album.view.ui.signup

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.album.R
import com.example.album.utils.CustomProgressBar
import com.example.album.utils.isNetworkAvailable
import com.example.album.utils.showToast
import com.example.album.view.ui.login.Login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Signup : Fragment(), View.OnClickListener {


lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var mAuth:FirebaseAuth
    lateinit var reference:DatabaseReference
    lateinit var storage:FirebaseStorage
    lateinit var mstorageReference: StorageReference
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtPassword:EditText
    lateinit var submit:Button
    lateinit var profileImage:ImageView
    lateinit var currentUser:FirebaseUser
    val PERMISSION_CODE=101
    val IMAGE_CODE=101
    var TAG="SignUp"
    var uri: Uri? =null
    var imageUrl:String=""
    var user_id=""
     var profileUri: Uri? =null
    private var progressBar: CustomProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()



    }



    open fun showProgress(context: Context) {
        progressBar = CustomProgressBar()
        progressBar!!.show(context)
    }

    open fun hideProgress() {
        progressBar?.getDialog()?.dismiss()
    }
    private fun updateUI() {


        activity?.let {
            var name1=edtName.text.toString()
            var email=edtEmail.text.toString()
            var pass=edtPassword.text.toString()
            var signupViewModel: SignupViewModel = ViewModelProvider(this)[SignupViewModel::class.java]


            if(profileUri!=null)
            {
                showProgress(activity!!)
                signupViewModel.signup(name1,email,pass,imageUrl,it).observe(it, Observer {
                    Log.v("status",it.toString())
                    if (it)
                    {
                        var id =FirebaseAuth.getInstance().currentUser!!.uid
                        signupViewModel.uploadImage(id.toString(), profileUri!!,activity!!).observe(activity!!,
                            Observer {
                                hideProgress()
                                activity!!.supportFragmentManager.beginTransaction().
                                replace(R.id.signup_main, Login.newInstance()).commit()
                            })

                    }
                })
            }
            else
            {
                activity!!.showToast("Please select profile image first")
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        /* initialization view componets*/
        val  view=inflater.inflate(R.layout.fragment_signup, container, false)
        edtName=view.findViewById(R.id.edt_name)
        edtEmail=view.findViewById(R.id.edt_email)
        edtPassword=view.findViewById(R.id.edt_password)
        submit=view.findViewById(R.id.btn_sign_up)

        profileImage=view.findViewById(R.id.profileImage)
        profileImage.setOnClickListener(this)
        submit.setOnClickListener(this)
        // Inflate the layout for this fragment
        return view
    }

    companion object {


        @JvmStatic
        fun newInstance(): Signup
        {
            val instance= Signup()
            return instance
            }
    }

    override fun onClick(v: View?) {
        if(v?.id== R.id.btn_sign_up)
        {
            if(checkValidation())
            {
                if(activity!!.isNetworkAvailable())
                {
                    updateUI()
                }
                else
                {
                    activity!!.showToast("Please Connect to wifi or mobile network")
                }


            }
        }
        if(v?.id==R.id.profileImage)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(activity?.applicationContext!!,Manifest.permission.READ_EXTERNAL_STORAGE)
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
            profileImage.setImageURI(data?.data)




        }
    }

    private fun checkValidation():Boolean {
        if (edtName.text.length<3)
        {
            edtName.setError("please provide valide name")
            return false
        }

        else if (!checkEmail(edtEmail.text.toString()))
        {
            edtEmail.setError("please provide correct email")
            return false
        }
        else if (edtPassword.text.length<5)
        {
            edtPassword.setError("Please provide password of lengt greater than 5")
            return false
        }
        return true

    }

    private fun checkEmail(a: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(a).matches()

    }


}
