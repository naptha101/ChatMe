package com.example.chatme

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class messAdap(val context:Context,var list:ArrayList<message>):RecyclerView.Adapter<RecyclerView.ViewHolder> (){
   val res=1;
    val sen=2;



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
            //   holder.sendmes.maxWidth=900
            }
        } else if (holder is Recviewhold) {
            val message = list[position]
            if (message != null) {
                holder.resmes.text = message.mes
//holder.resmes.maxWidth=900

            }
        }

    }

}
class Sentviewhold(itemView: View) : RecyclerView.ViewHolder(itemView){
    var sendmes= itemView.findViewById<TextView>(R.id.sendd)

}
class Recviewhold(itemView: View) : RecyclerView.ViewHolder(itemView){
    var resmes=itemView.findViewById<TextView>(R.id.recie)
}