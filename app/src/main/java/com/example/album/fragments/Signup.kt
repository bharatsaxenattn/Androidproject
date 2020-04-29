package  com.example.album.fragments

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
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
import com.example.album.activity.GalleryActivity
import com.example.album.POJO.ProfileData
import com.example.album.R

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= FirebaseAuth.getInstance()



    }

    private fun updateUI() {
        var email1=edtEmail.text.toString()
        var pass=edtPassword.text.toString()

        activity?.let {
            mAuth.createUserWithEmailAndPassword(email1, pass)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")


                        val user = mAuth.currentUser
                        firebaseDatabase= FirebaseDatabase.getInstance()
                        var name1=edtName.text.toString()
                        var email=edtEmail.text.toString()
                        var pass=edtPassword.text.toString()
                        var id= user?.uid
                        user_id= id!!
                        var data= ProfileData(name1,email,pass,id)
                        reference=firebaseDatabase.getReference("Users").child(id!!)
                        reference.setValue(data).addOnSuccessListener {
                            uploadprofileUrl()
                            Toast.makeText(activity,"Signup Sucessfully",Toast.LENGTH_LONG).show() }

                        activity!!.supportFragmentManager.beginTransaction().
                            replace(R.id.signup_main, Login.newInstance()).commit()
                        //updateUI(user)
                    } else {

                        if(task.exception is FirebaseAuthUserCollisionException)
                        {
                            Log.w(TAG, "The email address is already in use by another account.", task.exception)
                            Toast.makeText(activity, "The email address is already in use by another account.",
                                Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(activity, "Signup failed.",
                                Toast.LENGTH_SHORT).show()

                        }


                        // If sign in fails, display a message to the user.


                    }

                    // ...
                }
        }
    }

    private fun uploadprofileUrl() {
        reference=firebaseDatabase.getReference("UsersProfile").child(user_id)
        reference.setValue(imageUrl).addOnSuccessListener {

    }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
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

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance():Signup
        {
            val instance=Signup()
            return instance
            }
    }

    override fun onClick(v: View?) {
        if(v?.id== R.id.btn_sign_up)
        {
            if(checkValidation())
            {
                updateUI()

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
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE){
           var uri: Uri = data?.data!!


            storage= FirebaseStorage.getInstance()
            mstorageReference=storage.getReference().child(id.toString())

            var progressDialog= ProgressDialog(activity)
            // Code for showing progressDialog while uploading
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            val uplaod=mstorageReference.putFile(uri)
            uplaod.addOnCompleteListener { uplaodTask ->
                if (uplaodTask.isSuccessful) {
                    mstorageReference.downloadUrl.addOnCompleteListener { urlTask ->
                        urlTask.result?.let {
                            uri = it

                            imageUrl=uri.toString()
                            // imageView_category.setImageBitmap(imageBitmap)
                            progressDialog.dismiss()
                            Toast.makeText(activity,"ImageUploaded sucessfully", Toast.LENGTH_SHORT).show()
                            Log.v("path==", uri.toString())
                           // uploadImageUrl()


                        }
                    }
                } else {
                    uplaodTask.exception?.let {
                        progressDialog.dismiss()
                        Toast.makeText(activity,"ImageUploaded failed", Toast.LENGTH_SHORT).show()

                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        var intent=Intent(activity, GalleryActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

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
