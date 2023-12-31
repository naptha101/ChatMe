package com.example.chatme

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView.FindListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class setting : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var udata: DatabaseReference
    lateinit var imdata: FirebaseDatabase
    lateinit var muser: FirebaseUser
    var PICK_IMAGE_REQUEST = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        udata = FirebaseDatabase.getInstance().getReference()
        prevnem()
        udata = FirebaseDatabase.getInstance().getReference()
        auth = FirebaseAuth.getInstance()
        var gap = auth.currentUser!!.uid
        var but: Button = findViewById(R.id.button2)

        but.setOnClickListener {
            updt()
            var id = auth.currentUser!!.uid
            uploadProfilePhoto(id.toString(), uri)
        }
        var storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val storageRef: StorageReference =
                storage.reference.child("profile_images/${auth.currentUser?.uid}.jpg")

            var img: ImageView = findViewById(R.id.imageView3)
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                // Use an image loading library like Glide or Picasso to load and display the image
                // In this example, we'll use Glide
                // Picasso.get().load(uri).into(holder.img)
                Glide.with(this)
                    .load(uri) // Replace with the URL or resource of the user's profile image
                    .placeholder(R.drawable.baseline_person_24) // Default image resource
                    .transform(CircleCrop()) // Apply a circular crop to the image
                    .into(img)
            }.addOnFailureListener {
                Toast.makeText(this, "Error Occured", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<View>(R.id.view).setOnClickListener { imupdt() }
        findViewById<Button>(R.id.adv).setOnClickListener {
            var intent = Intent(this, Advancesetting::class.java)

            startActivity(intent)
            /*  findViewById<ConstraintLayout>(R.id.cc).visibility=View.VISIBLE
    findViewById<ConstraintLayout>(R.id.cc).alpha=0.94f;*/

        }
        findViewById<Button>(R.id.ok).setOnClickListener {
            var yoth = FirebaseAuth.getInstance().currentUser!!.email
            //var mail= findViewById<EditText>(R.id.name2).setText(yoth.toString())
            var browser = FirebaseAuth.getInstance().currentUser
            var pass = findViewById<EditText>(R.id.editff).text
            val credential = EmailAuthProvider.getCredential(yoth.toString(), pass.toString())

            /*     browser?.reauthenticate(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var intent=Intent(this,Advancesetting::class.java)

                    startActivity(intent)

                    findViewById<ConstraintLayout>(R.id.cc).visibility = View.GONE
                    finish()
                } else {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
                }*/
        }
    }




    var uri:Uri?=null
    fun imupdt(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data

            // Display the selected image in the ImageView
            var img:ImageView=findViewById(R.id.imageView3)
            //uri=selectedImageUri.toString()
           // real=selectedImageUri!!
            uri=selectedImageUri
            img.setImageURI(selectedImageUri)
        }
    }
    private fun uploadProfilePhoto(userId: String,real: Uri?) {
        real?.let { uri ->
            var storage= FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.reference.child("profile_images/$userId.jpg")
            storageRef.putFile(real)
                .addOnSuccessListener { /* Handle upload success */ }
                .addOnFailureListener { /* Handle upload failure */ }
        }
    }
    fun updt(){

        udata=FirebaseDatabase.getInstance().getReference()
        auth=FirebaseAuth.getInstance()
        val uid=auth.currentUser!!.uid.toString()
var childref=udata.child("user").child(uid)
        childref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if the dataSnapshot has a value
                if (dataSnapshot.exists()) {
                    // Retrieve the value from the dataSnapshot
                    val value = dataSnapshot.getValue(user::class.java)

                    // Now you can use the 'value' in your application
                    if (value != null) {
                        // Do something with the value
                        var name:EditText=findViewById(R.id.name2)
                      childref.setValue(user(name.text.toString(),value.Email.toString(),uid,value.image.toString(),true)).addOnSuccessListener {
                         // Toast.makeText(this,"data Updated",Toast.LENGTH_SHORT).show()

                      }
                    }
                } else {
                    // Data does not exist at the specified location
                    //Toast.makeText(this,"Data is upgraded",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that may occur while reading the data
                println("Error: ${databaseError.toException()}")
            }
        })
    }
    private fun showDialogWithEditText() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.setting_dialog, null)


        builder.setView(dialogView)
        builder.setTitle("Dialog Title")
        builder.setMessage("Enter something:")

        // Set a positive button and its click listener
        builder.setPositiveButton("OK") { dialog, _ ->

        }




        // Set a negative button and its click listener
        builder.setNegativeButton("Cancel") { dialog, _ ->
            // Do something when Cancel button is clicked
         //   dialog.dismiss()
        }

        // Display the dialog
        builder.show()
    }
    fun prevnem(){
        auth=FirebaseAuth.getInstance()
        val uid=auth.currentUser!!.uid.toString()
        var childref=udata.child("user").child(uid)
        childref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if the dataSnapshot has a value
                if (dataSnapshot.exists()) {
                    // Retrieve the value from the dataSnapshot
                    val value = dataSnapshot.getValue(user::class.java)

                    // Now you can use the 'value' in your application
                    if (value != null) {
                        // Do something with the value
                        var name:EditText=findViewById(R.id.name2)
                        name.setText(value.name)
                    }
                } else {
                    // Data does not exist at the specified location
                    //Toast.makeText(this,"Data is upgraded",Toast.LENGTH_SHORT).show()
                }
            }    override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that may occur while reading the data
                println("Error: ${databaseError.toException()}")
            }
        })

    }

}