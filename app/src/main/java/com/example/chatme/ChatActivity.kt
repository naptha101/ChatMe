package com.example.chatme

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class ChatActivity : AppCompatActivity() {
lateinit var meslay:RecyclerView
lateinit var send:View
lateinit var mesbox:EditText
lateinit var list:ArrayList<message>

lateinit var mdbref:DatabaseReference
var reciverroom:String?=null
    var senderroom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        // Set up the custom ActionBar
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowHomeEnabled(false)
        actionBar?.setDisplayShowTitleEnabled(false)

        val customActionBarView = LayoutInflater.from(this).inflate(
            R.layout.action_bar_custom_layout,
            null
        )
        actionBar?.customView = customActionBarView
        actionBar?.setDisplayShowCustomEnabled(true)
var back=customActionBarView.findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java));
           
        }
        // Customize the circular image
        val circularImageView = customActionBarView.findViewById<ImageView>(R.id.action_bar_image)


        var name= this.intent.getStringExtra("name")
        var uid= this.intent.getStringExtra("uid")
        val nameText=customActionBarView.findViewById<TextView>(R.id.textView2)
        nameText.setText(name)
        if (uid != null) {
            var storage= FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.reference.child("profile_images/${uid}.jpg")

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri) // Replace with the URL or resource of the user's profile image
                    .placeholder(R.drawable.baseline_person_24) // Default image resource
                    .transform(CircleCrop()) // Apply a circular crop to the image
                    .into(circularImageView)
            }.addOnFailureListener { /* Handle download failure */ }
        }

meslay=findViewById(R.id.recy)
        send=findViewById(R.id.send)
        mesbox=findViewById(R.id.messagebox)
        meslay=findViewById(R.id.recy)
        list=ArrayList()
        val recuid=uid;
      var  mesAdap=messAdap(this,list)
        mdbref=FirebaseDatabase.getInstance().getReference()
val senuid=FirebaseAuth.getInstance().currentUser?.uid
        senderroom = recuid+senuid
        reciverroom=senuid+recuid

        mdbref.child("chats").child(senderroom!!).child("messages").addValueEventListener(
            object :ValueEventListener{

                @SuppressLint("SuspiciousIndentation")
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (postsnapshot in snapshot.children) {

                        var mesa = postsnapshot.getValue(message::class.java)
                        list.add(mesa!!)
                    }
                    meslay.scrollToPosition(mesAdap.itemCount-1);
                    mesAdap.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {

                }
            }
        )

        meslay.layoutManager=LinearLayoutManager(this)
        meslay.adapter=mesAdap
        send.setOnClickListener {
var mess=mesbox.text.toString()

if(mess.length>0) {
    var obje = message(mess, senuid.toString())
    mdbref.child("chats").child(senderroom!!).child("messages").push()
        .setValue(obje).addOnSuccessListener {
            var obje = message(mess, senuid.toString())
            mdbref.child("chats").child(reciverroom!!).child("messages").push()
                .setValue(obje)
            mesbox.setText("")
        }
}
    }
}


}