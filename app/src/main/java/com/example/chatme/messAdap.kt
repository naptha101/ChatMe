package com.example.chatme

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.example.chatme.ChatActivity
class messAdap(val context:Context,var list:ArrayList<message>):RecyclerView.Adapter<RecyclerView.ViewHolder> (){
   val res=1;
    val sen=2;

val storage=FirebaseStorage.getInstance()
companion object{
    var senduri: Uri?=null;
    var recuri: Uri?=null;}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if(viewType==1){
           val view:View= LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
           return Recviewhold(view);
       }else{
           val view:View= LayoutInflater.from(context).inflate(R.layout.send,parent,false)
           return Sentviewhold(view);
       }
    }


    override fun getItemCount(): Int {
    return list.size

    }

    @SuppressLint("SuspiciousIndentation")
    override fun getItemViewType(position: Int): Int {
      val currentmessageid=list[position]!!.senderId
        if(FirebaseAuth.getInstance().currentUser!!.uid.equals(currentmessageid)){
            return  sen
        }else{
            return res
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is Sentviewhold) {
            val message = list[position]
            if (message != null) {

                holder.sendmes.text = message.mes

               val storageRef: StorageReference = storage.reference.child("profile_images/${message.senderId}.jpg")
/*if(senduri==null){
  storageRef.downloadUrl.addOnSuccessListener { uri ->
        senduri=uri
    }.addOnFailureListener {
        Toast.makeText(context, "Error occured 22", Toast.LENGTH_SHORT).show()
    }}*/
      if(senduri!=null){
          Glide.with(context)
              .load(senduri) // Replace with the URL or resource of the user's profile image
              .placeholder(R.drawable.baseline_person_24) // Default image resource
              .transform(CircleCrop()) // Apply a circular crop to the image
              .into(holder.senimg)
      }

            //   holder.sendmes.maxWidth=900
            }
        } else if (holder is Recviewhold) {
            val message = list[position]
            if (message != null) {
                holder.resmes.text = message.mes
//holder.resmes.maxWidth=900
                val storageRef: StorageReference = storage.reference.child("profile_images/${message.senderId}.jpg")
/*if(recuri==null){
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                  recuri=uri;
                }.addOnFailureListener {  Toast.makeText(context,"Error occured",Toast.LENGTH_SHORT).show()}}*/
if(recuri!=null){
    Glide.with(context)
        .load(recuri) // Replace with the URL or resource of the user's profile image
        .placeholder(R.drawable.baseline_person_24) // Default image resource
        .transform(CircleCrop()) // Apply a circular crop to the image
        .into(holder.recimg)
}

            }
        }

    }

}
class Sentviewhold(itemView: View) : RecyclerView.ViewHolder(itemView){
    var sendmes= itemView.findViewById<TextView>(R.id.sendd)
var senimg=itemView.findViewById<ImageView>(R.id.action_bar_image)
}
class Recviewhold(itemView: View) : RecyclerView.ViewHolder(itemView){
    var resmes=itemView.findViewById<TextView>(R.id.recie)
    var recimg=itemView.findViewById<ImageView>(R.id.action_bar_image2)
}