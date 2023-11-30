package com.example.chatme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.net.URI

class RegisterPage : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var pass: EditText
    lateinit var login: Button
    lateinit var sign: Button
lateinit var datab:DatabaseReference
    var auth= FirebaseAuth.getInstance()
    var PICK_IMAGE_REQUEST=1;
    var uri:String?="kk"
    lateinit var real:Uri
    var imgupld=FirebaseStorage.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        var name:EditText=findViewById(R.id.name)
        email=findViewById(R.id.editText2);
        pass=findViewById(R.id.password)
        login=findViewById(R.id.login)
        sign=findViewById(R.id.register)
        login.setOnClickListener { startActivity(Intent(this,login::class.java))
        finish()}
sign.setOnClickListener {
    signin(name.text.toString(),email.text.toString(),pass.text.toString());
}
    // supportActionBar?.hide()
        var img:ImageView=findViewById(R.id.imageView)
        img.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
var ulod:TextView=findViewById(R.id.textView)
        ulod.setOnClickListener {
           /* val databaseReference = FirebaseDatabase.getInstance().reference.child("images")
            if(uri!=null){
            databaseReference.setValue(uri.toString())}else{
                Toast.makeText(this,"Choose an image to upload",Toast.LENGTH_SHORT).show()
            }*/
        }


    }
    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data

            // Display the selected image in the ImageView
            var img:ImageView=findViewById(R.id.imageView)
            uri=selectedImageUri.toString()
       real=selectedImageUri!!
            img.setImageURI(selectedImageUri)
        }
    }

    fun signin(Name:String,email:String,pass:String){
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                task->
            if(task.isSuccessful){
                var uid:String=auth.currentUser?.uid!!.toString()
                uploadtodatabase(Name,email,uid);
                if(real!=null){
                uploadProfilePhoto(uid)}
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"Registration failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    fun uploadtodatabase(name:String,email:String,uid:String){
datab=FirebaseDatabase.getInstance().getReference();


                    datab.child("user").child(uid).setValue(user(name,email,uid,uri!!,true))


    }
    private fun uploadProfilePhoto(userId: String) {
        real?.let { uri ->
            val storageRef: StorageReference = imgupld.reference.child("profile_images/$userId.jpg")
            storageRef.putFile(real)
                .addOnSuccessListener { /* Handle upload success */ }
                .addOnFailureListener { /* Handle upload failure */ }
        }
    }
}