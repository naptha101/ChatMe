package com.example.chatme

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

lateinit var email:EditText
lateinit var pass:EditText

lateinit var sign:Button

class Loginpage : AppCompatActivity() {
    var auth=FirebaseAuth.getInstance()

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage)
        email=findViewById(R.id.editText2);
        pass=findViewById(R.id.passET)

        sign=findViewById(R.id.signup)
        sign.setOnClickListener { startActivity(Intent(this,RegisterPage::class.java))

        }
        var login=findViewById<Button>(R.id.buton)
        login.setOnClickListener {
login2(email.text.toString(),pass.text.toString())

        }
       // supportActionBar?.hide()
    }
    fun login2(email:String,pass:String){
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        var check:RadioButton=findViewById(R.id.radio)

auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
    task->
    if(task.isSuccessful){
        if(check.isChecked==true){
            sharedPreferences.edit().putString("rememberMe","yes" ).apply()
        }else{
            sharedPreferences.edit().putString("rememberMe", "no").apply()
        }

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }else{
        Toast.makeText(this,"Registration failed",Toast.LENGTH_SHORT).show();
    }
}
    }
}