package com.example.chatme

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

import org.w3c.dom.Text

class userAdapter(var Context:Context,var List:ArrayList<user>):RecyclerView.Adapter<userAdapter.userViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view:View=LayoutInflater.from(Context).inflate(R.layout.user_layout,parent,false)
   return userViewHolder(view);
    }


    override fun getItemCount(): Int {


        return List.size
    }


    override fun onBindViewHolder(holder: userViewHolder, position: Int) {

        val ret=List[position]
        holder.name.setText(ret.name)

        if (ret.uid != null) {
            var storage=FirebaseStorage.getInstance()


            val storageRef: StorageReference = storage.reference.child("profile_images/${ret.uid}.jpg")
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(Context)
                    .load(uri) // Replace with the URL or resource of the user's profile image
                    .placeholder(R.drawable.baseline_person_24) // Default image resource
                    .transform(CircleCrop()) // Apply a circular crop to the image
                    .into(holder.img)
            }.addOnFailureListener { /* Handle download failure */ }
        }
        if(ret.onl==false){
holder.oln.setImageResource(R.drawable.circular_backgroundl3)
        }else{
    holder.oln.setImageResource(R.drawable.circular_background2)
        }
        holder.itemView.setOnClickListener {
            var intent=Intent(Context,ChatActivity::class.java)

            intent.putExtra("name",ret.name)
         intent.putExtra("uid",ret.uid)
            Context.startActivity(intent)
        }
    }

    class userViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
var name=itemView.findViewById<TextView>(R.id.name)
        var img=itemView.findViewById<ImageView>(R.id.imageView2)
        var oln=itemView.findViewById<ImageView>(R.id.imageView3)
    }


}