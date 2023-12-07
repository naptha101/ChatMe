package com.example.chatme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Advancesetting : AppCompatActivity() {
    //var password: String? =intent.getStringExtra("password")
    private var moth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advancesetting)
       var email=moth.currentUser!!.email
        if(email!=null){
            findViewById<EditText>(R.id.Email).setText(email)
            //findViewById<EditText>(R.id.pass).setText(password)
        }
findViewById<Button>(R.id.button3).setOnClickListener { update() }


    }
    fun update(){
        var mail=email;
        var pass=findViewById<EditText>(R.id.pass).text.toString()
        var confir=findViewById<EditText>(R.id.passcon).text.toString()
        var em=findViewById<EditText>(R.id.Email).text.toString()

        if(pass!=null&&confir!=null&&pass.equals(confir)) {
            moth.currentUser!!.updateEmail(em)
                .addOnCompleteListener { updateEmailTask ->
                    if (updateEmailTask.isSuccessful) {
                        // Change password
                        moth.currentUser!!.updatePassword(confir)
                            .addOnCompleteListener { updatePasswordTask ->
                                if (updatePasswordTask.isSuccessful) {
                                   Toast.makeText(this,"Credential Updated",Toast.LENGTH_SHORT).show()
                                } else {
                                    // Handle errors updating password
                                    Toast.makeText(this,"internal error",Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
        }else{
            Toast.makeText(this,"Either of the field id empty or passwords are not same",Toast.LENGTH_SHORT).show()
        }

    }


}