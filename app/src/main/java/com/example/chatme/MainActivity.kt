package com.example.chatme

import android.app.SearchManager.OnCancelListener
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.widget.Button
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private lateinit var recy:RecyclerView
    private lateinit var List: ArrayList<user>
    private lateinit var Adapter:userAdapter
    private lateinit var dbref:DatabaseReference
private lateinit var mAuth:FirebaseAuth

///dsdvevervev
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
           online()
        List=ArrayList()
mAuth= FirebaseAuth.getInstance()
        Adapter=userAdapter(this,List)
        recy=findViewById(R.id.user_recycle)
        recy.layoutManager=LinearLayoutManager(this)
                recy.adapter=Adapter
dbref=FirebaseDatabase.getInstance().getReference()
        dbref.child("user").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                List.clear()
                for(postSnapshot in snapshot.children){
                    val current=postSnapshot.getValue(user::class.java)
                    List.add(current!!);
                }
                Adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,"database error",Toast.LENGTH_SHORT).show()
            }


        })


findViewById<Button>(R.id.search).setOnClickListener { onser()
findViewById<TextView>(R.id.text).visibility=View.VISIBLE
};

    findViewById<ImageView>(R.id.imageView6).setOnClickListener {
        recyupdt();
    }

    }
    override fun onDestroy() {

        // Perform your cleanup or custom operation here
        // This method is called when the activity is being destroyed (e.g., when the app is closed)
        var datab= FirebaseDatabase.getInstance().getReference();
        var auth=FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
        var uid=auth.currentUser!!.uid
        var user=datab.child("user").child(uid)

        user.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    val value = dataSnapshot.getValue(com.example.chatme.user::class.java)

                    if (value != null) {
                        datab.child("user").child(uid).setValue(user(value.name.toString(),value.Email.toString(),uid,value.image.toString(),false))
                    }
                }
            }    override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that may occur while reading the data
                println("Error: ${databaseError.toException()}")
            }
        })



        mAuth.signOut()}
        super.onDestroy()
    }

fun onser(){
    var List2=ArrayList<user>();
    var i=0;
  //  var moth=FirebaseAuth.getInstance().currentUser!!.uid
    var name=findViewById<EditText>(R.id.editTextText).text.toString()

    while(i<List.size) {
        var us = List.get(i).name;
        if (us==name.toString()) {

            List2.add(List.get(i))
            Adapter= userAdapter(this, List2);
            Adapter.notifyDataSetChanged()
            recy.layoutManager = LinearLayoutManager(this)
            recy.adapter = Adapter
            Toast.makeText(this,"found user",Toast.LENGTH_SHORT).show()
break
        }
        i++;
    }


}

    override fun onCreateOptionsMenu(menu:Menu?):Boolean{
menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.logout){
            val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("rememberMe", "no").apply()
            var datab= FirebaseDatabase.getInstance().getReference();
            var auth=FirebaseAuth.getInstance()
            var uid=auth.currentUser!!.uid
            var user=datab.child("user").child(uid)

            user.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        val value = dataSnapshot.getValue(com.example.chatme.user::class.java)

                        if (value != null) {
                            datab.child("user").child(uid).setValue(user(value.name.toString(),value.Email.toString(),uid,value.image.toString(),false))
                        }
                    }
                }    override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that may occur while reading the data
                    println("Error: ${databaseError.toException()}")
                }
            })



            mAuth.signOut()

           startActivity(Intent(this,Loginpage::class.java))
            finish();
            return true;
        }
        if(item.itemId==R.id.settings){
            startActivity(Intent(this,setting::class.java))
            return true
        }
        return true;
    }

    fun recyupdt(){
        findViewById<TextView>(R.id.text).visibility=View.GONE
        Adapter=userAdapter(this,List)
        recy=findViewById(R.id.user_recycle)
        recy.layoutManager=LinearLayoutManager(this)
        recy.adapter=Adapter
        dbref=FirebaseDatabase.getInstance().getReference()
        dbref.child("user").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                List.clear()
                for(postSnapshot in snapshot.children){
                    val current=postSnapshot.getValue(user::class.java)
                    List.add(current!!);
                }
                Adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
    fun online(){
        var datab= FirebaseDatabase.getInstance().getReference();
        var auth=FirebaseAuth.getInstance()
        var uid=auth.currentUser!!.uid
        var user=datab.child("user").child(uid)

        user.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    val value = dataSnapshot.getValue(com.example.chatme.user::class.java)

                    if (value != null) {
                        datab.child("user").child(uid).setValue(user(value.name.toString(),value.Email.toString(),uid,value.image.toString(),true))
                    }
                }
            }    override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that may occur while reading the data
                println("Error: ${databaseError.toException()}")
            }
        })

    }
    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (e1.y!=null&&e2.y!=null&&e1.y < e2.y) {
               recyupdt()
                return true
            }
            return false
        }
    }
}